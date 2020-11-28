package it.polimi.db2.servlets;

import it.polimi.db2.exception.InvalidRegistrationException;
import it.polimi.db2.services.UserService;
import org.apache.commons.text.StringEscapeUtils;

import javax.ejb.EJB;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = StringEscapeUtils.escapeJava(req.getParameter("user"));
        String email = StringEscapeUtils.escapeJava(req.getParameter("email"));
        String password = StringEscapeUtils.escapeJava(req.getParameter("pass"));
        String password_conf = StringEscapeUtils.escapeJava(req.getParameter("pass_conf"));

        if(username==null || email==null || password==null || password_conf==null || username.isEmpty() || email.isEmpty() || password.isEmpty() || password_conf.isEmpty() ||!password.equals(password_conf)) {
            //send error 400
        }

        String error = null;
        try {
            userService.registerUser(username,email,password);
        } catch (InvalidRegistrationException e1) {
            e1.printStackTrace();
            error = e1.getMessage();
        } catch (Exception e2) {
            e2.printStackTrace();
            //send error 500
        }

        if(error!=null) {
            //send back to login page with error message
        }
        else {
            //redirect to next page
        }


    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
