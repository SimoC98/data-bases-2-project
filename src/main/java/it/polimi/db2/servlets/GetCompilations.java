package it.polimi.db2.servlets;


import it.polimi.db2.entities.Compilation;
import it.polimi.db2.entities.Product;
import it.polimi.db2.services.CompilationService;
import it.polimi.db2.services.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/*
this servlet return 2 lists:
- users is a list of String containing usernames
- points is a list of Integer containing points
both lists are ordered according to points
 */
@WebServlet(name = "GetCompilations")
public class GetCompilations extends HttpServlet {
    @EJB(name = "it.polimi.db2.services/CompilationService")
    private CompilationService compilationService;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ZoneId zoneId = ZoneId.of("Europe/Rome");
        LocalDate today = LocalDate.now(zoneId);

        int productId = 0;
        try{
            productId = Integer.parseInt(request.getParameter("productId"));
        }catch( NumberFormatException e){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Not possible to retrieve the correct id");
            return;
        }

        Product product = null;
        List<Compilation> compilations = null;
        List<Compilation> deleted = null;
        try{
            product = productService.findProductById(productId);
            if(product==null) throw new Exception();
        }catch(Exception e){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Not possible to retrieve the product");
            return;
        }

        if(product.getDate().isAfter(today)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "NOT PERMITTED!");
            return;
        }

        try {
            compilations = compilationService.getCompilationList(product.getIdProduct());
            deleted = compilationService.getDeletedCompilation(product.getIdProduct());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Not possible to retrieve Compilation informations");
            return;
        }

        String path = "/WEB-INF/inspectCompilation.html";
        ServletContext servletContext = request.getServletContext();
        final WebContext ctx = new WebContext(request,response,servletContext,request.getLocale());
        ctx.setVariable("product",product);
        ctx.setVariable("compilations",compilations);
        ctx.setVariable ("deleted", deleted);
        templateEngine.process(path,ctx,response.getWriter());
    }

}
