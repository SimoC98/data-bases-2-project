package it.polimi.db2.servlets;

import it.polimi.db2.entities.User;
import it.polimi.db2.exception.CompilationAlreadyExistingException;
import it.polimi.db2.exception.InvalidCredentialsException;
import it.polimi.db2.exception.ProductAlreadyExistingException;
import it.polimi.db2.services.CompilationService;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.QuestionService;
import it.polimi.db2.services.UserService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

@WebServlet(name = "HomeServlet")
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB(name = "it.polimi.db2.services/QuestionService")
    private QuestionService questionService;
    @EJB(name = "it.polimi.db2.services/CompilationService")
    private CompilationService compilationService;
    @EJB(name = "it.polimi.db2.services/UserService")
    private UserService userService;
    @EJB(name = "it.polimi.db2.services/ProductService")
    private ProductService productService;

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
            productService.createProduct("prodotto_prova22222","descrizione prodotto prova22222", (float) 30,today,null);
        } catch (ProductAlreadyExistingException e) {
            e.printStackTrace();
        }




        //test createQuestion
        //questionService.createQuestion("quanti anni hai?",1);



        //test createCompilation
        /*Timestamp log = new Timestamp(System.currentTimeMillis());
        try {
            compilationService.createCompilation(3,2,log);
        } catch (CompilationAlreadyExistingException e) {
            e.printStackTrace();
        }


        //compilationService.createAnswer(1,"simone",4);
        /*compilationService.createAnswer(3,"bene",3);
        compilationService.prova(1);
        compilationService.prova(3);



        compilationService.prova_del(3);
        compilationService.prova(1);
        compilationService.prova(3);*/

       /* User u = null;
        try {
            u = userService.checkCredentials("user","pass");
        } catch (InvalidCredentialsException e) {
            e.printStackTrace();
        }

        System.out.println(u.getCompilations().size());*/

    }
}
