package it.polimi.db2.servlets;

import it.polimi.db2.entities.Compilation;
import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.User;
import it.polimi.db2.exception.CompilationAlreadyExistingException;
import it.polimi.db2.services.CompilationService;
import it.polimi.db2.services.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

@WebServlet(name = "SubmitQuestionnaire")
public class SubmitQuestionnaire extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB(name = "it.polimi.db2.services/CompilationService")
    private CompilationService compilationService;
    @EJB(name = "it.polimi.db2.services/ProductService")
    private ProductService productService;
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    /*
        this servlet is called from the questionnaire_dynamic.html; a compilation is created and
        if the questionnaire is submitted request is forwarded to CheckQuestionnaireAnswers
        otherwise if is deleted request is forwarded to DeleteQuestionnaire.
        Buttons of front had should have name="action" and value="submit" or "delete"
         */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ZoneId zoneId = ZoneId.of("Europe/Rome");
        LocalDate today = LocalDate.now(zoneId);
        Product product;
        Timestamp log;
        User u = (User) request.getSession().getAttribute("user");

        product = productService.findProductByDate(today);

        if(product==null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"There is no product today");
            return;
        }

        log = new Timestamp(System.currentTimeMillis());

        Compilation newCompilation = null;
        try {
            newCompilation = compilationService.createCompilation(u.getId(),product.getIdProduct(),log);
        } catch (CompilationAlreadyExistingException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
            return;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Not possible to create a compilation");
            return;
        }
        String action = request.getParameter("action");
        String path = null;
        request.setAttribute("compilation",newCompilation);
        if(action.equalsIgnoreCase("submit")) {
            path = "CheckQuestionnaireAnswers";
        } else if(action.equalsIgnoreCase("delete")) {
            path = "DeleteQuestionnaire";
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Incorrect param values");
            return;
        }

        ServletContext servletContext = request.getServletContext();
        final WebContext ctx = new WebContext(request,response,servletContext,request.getLocale());
        templateEngine.process(path,ctx,response.getWriter());
    }


}
