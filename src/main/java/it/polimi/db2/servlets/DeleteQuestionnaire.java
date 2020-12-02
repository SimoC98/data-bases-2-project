package it.polimi.db2.servlets;

import it.polimi.db2.entities.Compilation;
import it.polimi.db2.services.CompilationService;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteQuestionnaire")
public class DeleteQuestionnaire extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB(name = "it.polimi.db2.services/CompilationService")
    private CompilationService compilationService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Compilation compilation = (Compilation) request.getAttribute("compilation");

        if(compilation==null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Incorrect or missing param values");
            return;
        }

        try {
            //compilationService.deleteQuestionnaire
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Not possible to delete the questionnaire");
            return;
        }

        //TODO: add path
        String path = null;
        response.sendRedirect(path);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
