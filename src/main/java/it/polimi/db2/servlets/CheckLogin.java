package it.polimi.db2.servlets;

import it.polimi.db2.entities.User;
import it.polimi.db2.exception.InvalidCredentialsException;
import it.polimi.db2.services.UserService;
import org.apache.commons.text.StringEscapeUtils;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckLogin")
public class CheckLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB(name = "it.polimi.db2.services/UserService")
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = null;
        String password = null;

        username = StringEscapeUtils.escapeJava(request.getParameter("username"));
        password = StringEscapeUtils.escapeJava(request.getParameter("password"));

        if(username==null || password==null || username.length()==0 || password.length()==0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Incorrect or missing param values");
            return;
        }

        User user = null;
        try {
            user = userService.checkCredentials(username,password);
        } catch (InvalidCredentialsException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
            return;
        }

        String path = null;
        if(user==null) {
            request.setAttribute("error_msg","Wrong credentials... please try again or register");
            path = "index.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            dispatcher.forward(request,response);
        } else {
            request.getSession().setAttribute("user",user);
            System.out.println("user " + user.getUsername() + " is logged in");
            //redirect to next page
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
