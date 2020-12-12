package it.polimi.db2.servlets;

import it.polimi.db2.entities.Product;
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

@WebServlet(name = "CreateProductQuestions")
public class CreateProductQuestions extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB(name = "it.polimi.db2.services/ProductService")
    private ProductService productService;
    @EJB(name = "it.polimi.db2.services/QuestionService")
    private QuestionService questionService;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer productId = null;
        Product p = null;
        String[] questions = null;
        boolean badRequest = false;
        try {
            //productId = Integer.parseInt(request.getParameter("product_id"));
            p = productService.findProductById(22);
            questions = request.getParameterValues("question");
        } catch (NumberFormatException | NullPointerException e) {
            badRequest = true;
        }

        System.out.println(questions);
        System.out.println(p);

        if(badRequest || p==null || questions==null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Incorrect or missing param values");
            return;
        }

        try {
            for(int i=0; i<questions.length;i++) {
                questionService.createQuestion(questions[i],p.getIdProduct());
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Not possible to add questions for this product");
            return;
        }

        //todo:add path
        String path = null;
        response.sendRedirect(path);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
