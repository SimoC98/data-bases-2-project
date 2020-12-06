package it.polimi.db2.servlets;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Review;
import it.polimi.db2.services.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@WebServlet(name = "GetProductAndReviews")
public class GetProductAndReviews extends HttpServlet {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ZoneId zoneId = ZoneId.of("Europe/Rome");
        LocalDate today = LocalDate.now(zoneId);

        Product p = null;
        try {
            p = productService.findProductByDate(today);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Product not found");
        }

        List<Review> reviews = null;
        if(p!=null) {
            reviews = p.getReviews();
        }

        String path = "/WEB-INF/Home.jsp";
        ServletContext servletContext = request.getServletContext();
        final WebContext ctx = new WebContext(request,response,servletContext,request.getLocale());

        ctx.setVariable("product",p);
        ctx.setVariable("reviews",reviews);

        templateEngine.process(path,ctx,response.getWriter());
    }
}
