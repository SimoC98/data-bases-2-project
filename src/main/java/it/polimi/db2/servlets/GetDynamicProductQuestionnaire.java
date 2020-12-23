package it.polimi.db2.servlets;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Question;
import it.polimi.db2.entities.User;
import it.polimi.db2.exception.ProductAlreadyExistingException;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.QuestionService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@WebServlet(name = "GetDynamicProductQuestionnaire")
public class GetDynamicProductQuestionnaire extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB(name = "it.polimi.db2.services/ProductService")
    private ProductService productService;
    @EJB(name = "it.polimi.db2.services/QuestionService")
    private QuestionService questionService;
    private TemplateEngine templateEngine;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String path = null;

        User u = (User) request.getSession().getAttribute("user");
        if(u.isBlocked()) {
            ctx.setVariable("msg","You are blocked");
            path = "/WEB-INF/messagePage.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        }

        ZoneId zoneId = ZoneId.of("Europe/Rome");
        LocalDate today = LocalDate.now(zoneId);

        Product p = null;
        try {
            p = productService.findProductByDate(today);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Product not found");
            return;
        }

        if (p == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "There is not product today");
            return;
        }

        List<Question> questions = questionService.findQuestionByProduct(p.getIdProduct());

        path = "/WEB-INF/questionnaire_dynamic.html";
        ctx.setVariable("questions", questions);
        templateEngine.process(path, ctx, response.getWriter());
    }


}
