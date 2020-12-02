package it.polimi.db2.servlets;

import it.polimi.db2.entities.Review;
import it.polimi.db2.entities.User;
import it.polimi.db2.services.ReviewService;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CreateReview")
public class CreateReview extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB(name = "it.polimi.db2.services/ReviewService")
    private ReviewService reviewService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Integer productId = null;
        String reviewTxt = null;
        boolean badRequest = false;

        try {
            productId = Integer.parseInt(request.getParameter("mission_id"));
            reviewTxt = request.getParameter("review_txt");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            badRequest = true;
        }

        if(badRequest || reviewTxt==null || reviewTxt.length()==0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Incorrect or missing param values");
        }

        Review review = null;
        try {
            review = reviewService.createReview(user.getId(),productId,reviewTxt);
        } catch(Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Impossible to create review");
        }

        String path = "/GetProductAndReviews";
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request,response);
    }
}
