package it.polimi.db2.services;


import it.polimi.db2.entities.Answer;
import it.polimi.db2.entities.Compilation;
import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.User;
import it.polimi.db2.exception.CompilationAlreadyExistingException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.List;


@Stateless
public class CompilationService {

    @PersistenceContext(unitName = "data_bases_2_project")
    private EntityManager em;

    public CompilationService() {
    }

    public Compilation createCompilation(Integer idUser, Integer idProduct, Timestamp log) throws CompilationAlreadyExistingException {
        if (em.createNamedQuery("Compilation.findByUser&ProductId").setParameter("idUser",idUser).setParameter("idProduct",idProduct).getSingleResult() != null) {
            throw new CompilationAlreadyExistingException("A compilation is already created for this user!");
        }
        User user = (User) em.find(User.class, idUser);
        Product product = (Product) em.find(Product.class, idProduct);
        Compilation compilation = new Compilation(user, product, log);
        user.addCompilation(compilation);
        product.addCompilation(compilation);
        em.persist(compilation);
        return compilation;
    }

    //TODO WITH TRIGGERS
    public Compilation addPointsToCompilation(List<Answer> answers){
        int totalPoints = 0;
        Compilation c = null;
        for (Answer a :answers) {
            //totalPoints += a.getQuestion().getType();
        }
        c.setPoints(totalPoints);
        em.merge(c);
        return c;
    }

    public void deleteCompilationByProductId(Integer idProduct){
        List<Compilation> compilationsToDelete = em.createNamedQuery("Compilation.findByProductId",Compilation.class).setParameter("idProduct",idProduct).getResultList();
        User user = null;
        Product product = null;
        if (! compilationsToDelete.isEmpty() ){
            for (Compilation c:compilationsToDelete) {
                user = c.getUser();
                user.removeCompilation(c);
                product = c.product();
                product.removeCompilation(c);
                em.remove(c);
            }
        }
    }

}
