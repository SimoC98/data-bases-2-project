package it.polimi.db2.servlets;

import it.polimi.db2.entities.Product;
import it.polimi.db2.exception.ProductAlreadyExistingException;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.utils.ImageUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

@WebServlet(name = "CreateProduct")
@MultipartConfig
public class CreateProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB(name = "it.polimi.db2.services/ProductService")
    private ProductService productService;
    private TemplateEngine templateEngine;


    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ZoneId zonedId = ZoneId.of( "Europe/Rome" );
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
            date = LocalDate.parse(request.getParameter("date"));
            if(date.isBefore(today)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid date selected");
            }
            imagePart = request.getPart("image");
            //image = IOUtils.toByteArray(imagePart.getInputStream());
            image = ImageUtils.readImage(imagePart.getInputStream());
        } catch(NumberFormatException | NullPointerException e) {
             badRequest = true;
            //e.printStackTrace();
        }

        if(badRequest || name==null || description==null || image==null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
            return;
        }

        Product newProduct = null;
        String error = null;
        try {
            newProduct = productService.createProduct(name,description,price,date,image);
        } catch (ProductAlreadyExistingException e) {
            e.printStackTrace();
            error = e.getMessage();
        }

        /*
        if error!= null send back to the INSERT page a error message that will be displayed
        otherwise, send back to the INSERT page the product just created, to let the admin insert some questions
         */
        String path = "/WEB-INF/createProduct.html";
        ServletContext servletContext = request.getServletContext();
        final WebContext ctx = new WebContext(request,response,servletContext,request.getLocale());
        if(error!=null) {
            ctx.setVariable("error_msg",error);
        } else {
            ctx.setVariable("product",newProduct);
        }
        templateEngine.process(path,ctx,response.getWriter());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
