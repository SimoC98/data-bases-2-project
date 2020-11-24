package it.polimi.db2.servlets;

import it.polimi.db2.entities.Product;
import it.polimi.db2.exception.ProductAlreadyExistingException;
import it.polimi.db2.services.ProductService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;


import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@WebServlet(name = "CreateProduct")
public class CreateProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB(name = "it.polimi.db2.services/ProductService")
    private ProductService productService;


    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        ZoneId zonedId = ZoneId.of( "America/Montreal" );
        LocalDate today = LocalDate.now( zonedId );

        String name = null;
        String description = null;
        float price = 0;
        LocalDate date = null;
        Part imagePart = null;
        byte[] image = null;
        boolean badRequest = false;

        try {
            name = StringEscapeUtils.escapeJava(request.getParameter("name"));
            description = StringEscapeUtils.escapeJava(request.getParameter("description"));
            price = Float.parseFloat(request.getParameter("price"));
            //date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
            date = LocalDate.parse(request.getParameter("date"));
            if(date.isBefore(today)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid date selected");
            }
            imagePart = request.getPart("image");
            image = IOUtils.toByteArray(imagePart.getInputStream());
        } catch(NumberFormatException | NullPointerException e) {
             badRequest = true;
            //e.printStackTrace();
        }

        if(badRequest || name==null || description==null || image==null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
        }

        Product new_product = null;
        String error = null;
        try {
            new_product = productService.createProduct(name,description,price,date,image);
        } catch (ProductAlreadyExistingException e) {
            e.printStackTrace();
            error = e.getMessage();
        }


        /*
        if error!= null send back to the INSERT page a error message that will be displayed
        otherwise, send back to the INSERT page the id of the product just created, to let the admin insert some questions
         */
        //todo: add path
        String path = null;
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        if(error!=null) {
            request.setAttribute("error_msg", error);
            dispatcher.forward(request,response);
        } else {
            request.setAttribute("product_id",new_product.getIdProduct());
            dispatcher.forward(request,response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
