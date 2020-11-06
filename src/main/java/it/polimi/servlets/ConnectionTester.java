package it.polimi.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;

@WebServlet(name = "ConnectionTester")
public class ConnectionTester extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String DB_URL = "jdbc:mysql://localhost:3306/dabatases2?serverTimezone=UTC";
        final String USER = "root"; //put user
        final String PASS = ""; //put password
        String result = "Connection worked";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            result = "Connection failed!";
            e.printStackTrace();
        }
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println(result);
        out.close();
    }
}
