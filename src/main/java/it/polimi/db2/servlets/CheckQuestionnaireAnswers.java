package it.polimi.db2.servlets;

import it.polimi.db2.entities.Answer;
import it.polimi.db2.entities.Compilation;
import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.User;
import it.polimi.db2.exception.CompilationAlreadyExistingException;
import it.polimi.db2.services.AnswerService;
import it.polimi.db2.services.CompilationService;
import it.polimi.db2.services.ProductService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Enumeration;

@WebServlet(name = "CheckQuestionnaireAnswers")
public class CheckQuestionnaireAnswers extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB(name = "it.polimi.db2.services/AnswerService")
    private AnswerService answerService;
    @EJB(name = "it.polimi.db2.services/CompilationService")
    private CompilationService compilationService;
    @EJB(name = "it.polimi.db2.services/ProductService")
    private ProductService productService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer product_id = null;
        Product product = null;
        Integer user_id = null;
        Timestamp log = null;
        boolean bad_request = false;
        try {
            product_id = Integer.parseInt(request.getParameter("product_id"));
            product = productService.findProductById(product_id);
        } catch (NumberFormatException | NullPointerException e)   {
            bad_request = true;
        }

        if(bad_request || product==null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Incorrect or missing param values");
        }

        //TODO: check if u is not null in every servlet
        User u = (User) request.getSession().getAttribute("user");
        user_id = u.getId();

        log = new Timestamp(System.currentTimeMillis());

        Compilation new_compilation = null;
        try {
            new_compilation = compilationService.createCompilation(user_id,product_id,log);
        } catch (CompilationAlreadyExistingException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
        }

        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()) {
            String par = params.nextElement();
            if(!par.equals("product_id")) {
                Integer question_id = null;
                try {
                    question_id = Integer.parseInt(par);
                } catch(NumberFormatException | NullPointerException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
                }
                String answer_text = request.getParameter(par);
                //TODO: create answer
                //TODO: check bad answers here???

            }
        }




    }


}
