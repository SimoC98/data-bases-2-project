package it.polimi.db2.servlets;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Question;
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
import java.util.LinkedList;
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
        /*
        Integer productId = null;
        boolean badRequest = false;
        try {
            productId = Integer.parseInt(request.getParameter("product_id"));
        } catch (NumberFormatException | NullPointerException e ) {
            badRequest = true;
        }

        Product p = null;
        try {
            p = productService.findProductById(productId);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Not possible to find this product");
        }

        if(badRequest || p==null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
        }

        List<Question> questions = p.getQuestions();
        List<Question> fixed_questions = null;
        try  {
            fixed_questions = questionService.findFixedQuestion();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Not possible to find fixed questions for this product");
        }

        questions.addAll(fixed_questions);

        */

        //todo: add path
        List<Question> questions = new LinkedList<>();
        Question question = new Question();
        Question question1 = new Question();
        question.setQuestionText("CIAO");
        question1.setQuestionText("AGGIUNTO");
        questions.add(question);
        questions.add(question1);
        String path = "/WEB-INF/questionnaire_dynamic.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("questions", questions);
        templateEngine.process(path, ctx, response.getWriter());
    }
}
