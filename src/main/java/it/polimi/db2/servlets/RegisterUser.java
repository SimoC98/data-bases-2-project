package it.polimi.db2.servlets;

import it.polimi.db2.exception.InvalidRegistrationException;
import it.polimi.db2.services.UserService;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterUser")
public class RegisterUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/UserService")
    private UserService userService;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = StringEscapeUtils.escapeJava(request.getParameter("user"));
        String email = StringEscapeUtils.escapeJava(request.getParameter("email"));
        String password = StringEscapeUtils.escapeJava(request.getParameter("pass"));
        String passwordConf = StringEscapeUtils.escapeJava(request.getParameter("pass_conf"));


        if(username==null || email==null || password==null || passwordConf==null || username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConf.isEmpty() ||!password.equals(passwordConf) || password.length()<4) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Incorrect or missing param values");
            return;
        }

        String error = null;
        try {
            userService.registerUser(username,email,password);
        } catch (InvalidRegistrationException e1) {
            e1.printStackTrace();
            error = e1.getMessage();
        } catch (Exception e2) {
            e2.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Not possible to register user");
            return;
        }

        String path = "index.html";
        if(error!=null) {
            ServletContext servletContext = request.getServletContext();
            final WebContext ctx = new WebContext(request,response,servletContext,request.getLocale());
            ctx.setVariable("error_msg",error);
            templateEngine.process(path,ctx,response.getWriter());
        }
        else {
            response.sendRedirect(path);
        }


    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
