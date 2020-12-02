package it.polimi.db2.servlets;

import it.polimi.db2.exception.InvalidRegistrationException;
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

@WebServlet(name = "RegisterUser")
public class RegisterUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB(name = "it.polimi.db2.services/UserService")
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = StringEscapeUtils.escapeJava(request.getParameter("user"));
        String email = StringEscapeUtils.escapeJava(request.getParameter("email"));
        String password = StringEscapeUtils.escapeJava(request.getParameter("pass"));
        String passwordConf = StringEscapeUtils.escapeJava(request.getParameter("pass_conf"));

        System.out.println(request.getParameter("action"));

        if(username==null || email==null || password==null || passwordConf==null || username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConf.isEmpty() ||!password.equals(passwordConf)) {
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

        String path = "index.jsp";
        if(error!=null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            request.setAttribute("error_msg",error);
            dispatcher.forward(request,response);
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
