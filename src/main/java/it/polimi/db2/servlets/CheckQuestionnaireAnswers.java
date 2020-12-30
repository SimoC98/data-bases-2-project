package it.polimi.db2.servlets;

import it.polimi.db2.entities.BadWord;
import it.polimi.db2.entities.Compilation;
import it.polimi.db2.entities.User;
import it.polimi.db2.exception.BadWordException;
import it.polimi.db2.exception.EmptyAnswerException;
import it.polimi.db2.exception.InvalidProductQuestionAssociationException;
import it.polimi.db2.services.CompilationService;
import it.polimi.db2.services.UserService;
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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "CheckQuestionnaireAnswers")
public class CheckQuestionnaireAnswers extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB(name = "it.polimi.db2.services/CompilationService")
    private CompilationService compilationService;
    @EJB(name = "it.polimi.db2.services/UserService")
    private UserService userService;

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
        Compilation compilation = (Compilation) request.getAttribute("compilation");
        User u = (User) request.getSession().getAttribute("user");

        if(compilation==null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Error! There is not a compilation for this user and product");
            return;
        }

        ServletContext servletContext = request.getServletContext();
        final WebContext ctx = new WebContext(request,response,servletContext,request.getLocale());
        String path = null;

        List<Integer> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()) {
            String par = params.nextElement();
            if(!par.equals("action")) {
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
            }
        }
        try{
            compilationService.createAnswer(questions,answers,compilation.getIdCompilation());
        } catch (BadWordException e) {
            e.printStackTrace();
            userService.blockUser(u);
            ctx.setVariable("msg","You have been blocked");
            path = "/WEB-INF/messagePage.html";
            templateEngine.process(path,ctx,response.getWriter());
            return;
        } catch (EmptyAnswerException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"You have not compiled marketing questions");
            return;
        } catch (InvalidProductQuestionAssociationException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Question not associated with product of today");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Not possible create answers");
            return;
        }
        ctx.setVariable("msg","Congratulation! You have compiled a questionnaire");
        path = "/WEB-INF/messagePage.html";
        templateEngine.process(path,ctx,response.getWriter());
    }


}
