package it.polimi.db2.servlets;

import it.polimi.db2.entities.Compilation;
import it.polimi.db2.entities.User;
import it.polimi.db2.services.CompilationService;
import org.thymeleaf.TemplateEngine;
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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "CheckQuestionnaireAnswers")
public class CheckQuestionnaireAnswers extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB(name = "it.polimi.db2.services/CompilationService")
    private CompilationService compilationService;

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

    //TODO: alternative options?
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Compilation compilation = (Compilation) request.getAttribute("compilation");
        User u = (User) request.getSession().getAttribute("user");


        if(compilation==null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Incorrect or missing param values");
        }

        List<Integer> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()) {
            String par = params.nextElement();
            if(!par.equals("product_id") && !par.equals("action")) {
                Integer questionId = null;
                try {
                    questionId = Integer.parseInt(par);
                } catch(NumberFormatException | NullPointerException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
                    return;
                }
                String answerText = request.getParameter(par);
                questions.add(questionId);
                answers.add(answerText);
                //TODO: the transaction has to be rolled back if an answer contains bad words --> all answers added oj the same transaction? Bad word checked in the same transaction?
                try{
                    compilationService.createAnswer(questions,answers,compilation.getIdCompilation());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }




    }


}
