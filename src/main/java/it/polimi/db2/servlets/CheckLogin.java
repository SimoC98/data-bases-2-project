package it.polimi.db2.servlets;

import it.polimi.db2.entities.User;
import it.polimi.db2.exception.InvalidCredentialsException;
import it.polimi.db2.services.UserService;
import org.apache.commons.text.StringEscapeUtils;

import javax.ejb.EJB;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = null;
        String password = null;

        username = StringEscapeUtils.escapeJava(req.getParameter("username"));
        password = StringEscapeUtils.escapeJava(req.getParameter("password"));

        if(username==null || password==null || username.length()==0 || password.length()==0) {
            //send error 400
        }

        User user = null;
        try {
            user = userService.checkCredentials(username,password);
        } catch (InvalidCredentialsException e) {
            e.printStackTrace();
            //send error 400
        }

        if(user==null) {
            //send error_message invalid credentials to login page
        } else {
            req.getSession().setAttribute("user",user);
            //redirect to next page
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
