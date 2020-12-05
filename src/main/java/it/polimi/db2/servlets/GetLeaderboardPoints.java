package it.polimi.db2.servlets;

import it.polimi.db2.entities.Compilation;
import it.polimi.db2.entities.User;
import it.polimi.db2.services.CompilationService;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetLeaderboardPoints")
public class GetLeaderboardPoints extends HttpServlet {
    @EJB(name = "it.polimi.db2.services/CompilationService")
    private CompilationService compilationService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer productId = null;

        try {
            productId = Integer.parseInt(request.getParameter("product_id"));
        } catch (NullPointerException | NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Incorrect or missing param values");
            return;
        }

        List<Compilation> compilations = null;
        try {
            compilationService.getOrderedCompilations(productId);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Not possible to retrieve leaderboard informations");
            return;
        }

        List<String> users = null;
        List<Integer> points = null;
        if(!compilations.isEmpty()) {
            compilations.stream().forEach(x -> {
                users.add(x.getUser().getUsername());
                points.add(x.getPoints());
            });
        }

        //todo: add path
        String path = null;
        request.setAttribute("users",users);
        request.setAttribute("points",points);

        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request,response);
    }
}
