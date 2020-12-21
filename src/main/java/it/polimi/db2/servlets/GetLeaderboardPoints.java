package it.polimi.db2.servlets;

import it.polimi.db2.entities.Compilation;
import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.User;
import it.polimi.db2.services.CompilationService;
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
import java.util.ArrayList;
import java.util.List;

/*
this servlet return 2 lists:
- users is a list of String containing usernames
- points is a list of Integer containing points
both lists are ordered according to points
 */
@WebServlet(name = "GetLeaderboardPoints")
public class GetLeaderboardPoints extends HttpServlet {
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

        Product product = productService.findProductByDate(today);

        if(product==null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"There is no product today");
            return;
        }

        List<Compilation> compilations = null;
        try {
            compilations = compilationService.getOrderedCompilations(product.getIdProduct());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Not possible to retrieve leaderboard informations");
            return;
        }

        List<String> users = new ArrayList<>();
        List<Integer> points = new ArrayList<>();
        if(!compilations.isEmpty()) {
            compilations.stream().forEach(x -> {
                users.add(x.getUser().getUsername());
                points.add(x.getPoints());
            });
        }

        String path = "/WEB-INF/leaderboard.html";
        ServletContext servletContext = request.getServletContext();
        final WebContext ctx = new WebContext(request,response,servletContext,request.getLocale());
        ctx.setVariable("users",users);
        ctx.setVariable("points",points);
        templateEngine.process(path,ctx,response.getWriter());
    }
}
