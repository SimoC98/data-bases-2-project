package it.polimi.db2.servlets;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Question;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.QuestionService;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetProductQuestionnaire")
public class GetProductQuestionnaire extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB(name = "it.polimi.db2.services/ProductService")
    private ProductService productService;
    @EJB(name = "it.polimi.db2.services/QuestionService")
    private QuestionService questionService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        //todo: add path
        String path = null;
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        request.setAttribute("questions", questions);
        dispatcher.forward(request,response);
    }
}
