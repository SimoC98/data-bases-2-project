package it.polimi.db2.servlets;

import it.polimi.db2.entities.User;
import it.polimi.db2.exception.InvalidCredentialsException;
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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckLogin")
@MultipartConfig
public class CheckLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = null;
        String password = null;

        System.out.println("ciao sono qui");


        username = StringEscapeUtils.escapeJava(request.getParameter("username"));
        password = StringEscapeUtils.escapeJava(request.getParameter("password"));

        if(username==null || password==null || username.length()==0 || password.length()==0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Incorrect or missing param values");
            return;
        }

        User user = null;
        try {
            user = userService.checkCredentials(username,password);
            System.out.println(user);
        } catch (InvalidCredentialsException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
            return;
        }

        //jstl operations for forward commented
        String path = null;
        if(user==null) {
            path = "index.html";
            ServletContext servletContext = request.getServletContext();
            final WebContext ctx = new WebContext(request,response,servletContext,request.getLocale());
            ctx.setVariable("error_msg","Wrong credentials... please try again or register");
            templateEngine.process(path,ctx,response.getWriter());
            //request.setAttribute("error_msg","Wrong credentials... please try again or register");
            //RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            //dispatcher.forward(request,response);
        } else {
            request.getSession().setAttribute("user",user);
            path = "getProductReviews";
            response.sendRedirect(path);
            //RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            //dispatcher.forward(request,response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
