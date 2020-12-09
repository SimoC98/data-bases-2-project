package it.polimi.db2.servlets;

import it.polimi.db2.services.CompilationService;
import it.polimi.db2.services.QuestionService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HomeServlet")
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB(name = "it.polimi.db2.services/QuestionService")
    private QuestionService questionService;
    @EJB(name = "it.polimi.db2.services/CompilationService")
    private CompilationService compilationService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("Hello this is a test");
        out.close();*/



        //test createProduct
        /*ZoneId zonedId = ZoneId.of( "Europe/Rome" );
        LocalDate today = LocalDate.now( zonedId );
        try {
            productService.createProduct("prodotto_prova","descrizione prodotto prova", (float) 2.15,today,null);
        } catch (ProductAlreadyExistingException e) {
            e.printStackTrace();
        }
       */



        //test createQuestion
        //questionService.createQuestion("quanti anni hai?",1);



        //test createCompilation
        /*Timestamp log = new Timestamp(System.currentTimeMillis());
        try {
            compilationService.createCompilation(1,1,log);
        } catch (CompilationAlreadyExistingException e) {
            e.printStackTrace();
        }*/


        //compilationService.createAnswer(1,"simone",4);
        /*compilationService.createAnswer(3,"bene",3);
        compilationService.prova(1);
        compilationService.prova(3);

        compilationService.prova_del(3);
        compilationService.prova(1);
        compilationService.prova(3);*/
    }
}
