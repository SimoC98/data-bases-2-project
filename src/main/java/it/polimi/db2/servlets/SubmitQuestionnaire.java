package it.polimi.db2.servlets;

import it.polimi.db2.entities.Compilation;
import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.User;
import it.polimi.db2.exception.CompilationAlreadyExistingException;
import it.polimi.db2.services.AnswerService;
import it.polimi.db2.services.CompilationService;
import it.polimi.db2.services.ProductService;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "SubmitQuestionnaire")
public class SubmitQuestionnaire extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB(name = "it.polimi.db2.services/CompilationService")
    private CompilationService compilationService;
    @EJB(name = "it.polimi.db2.services/ProductService")
    private ProductService productService;

    /*
    this servlet is called from the questionnaire_dynamic.html; a compilation is created and
    if the questionnaire is submitted request is forwarded to CheckQuestionnaireAnswers
    otherwise if is deleted request is forwarded to DeleteQuestionnaire.
    Buttons of front had should have name="action" and value="submit" or "delete"
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer productId = null;
        Product product = null;
        Timestamp log = null;
        User u = (User) request.getSession().getAttribute("user");
        boolean badRequest = false;
        try {
            productId = Integer.parseInt(request.getParameter("product_id"));
            product = productService.findProductById(productId);
        } catch (NumberFormatException | NullPointerException e)   {
            badRequest = true;
        }

        if(badRequest || product==null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Incorrect or missing param values");
        }

        log = new Timestamp(System.currentTimeMillis());

        Compilation newCompilation = null;
        try {
            newCompilation = compilationService.createCompilation(u.getId(),productId,log);
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


        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request,response);
    }


}
