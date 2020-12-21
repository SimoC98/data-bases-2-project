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

         Product product = (Product) request.getAttribute("product");

        List<Compilation> compilations = null;
        List<Compilation> deleted = null;
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
