package it.polimi.db2.services;


import it.polimi.db2.entities.*;
import it.polimi.db2.exception.CompilationAlreadyExistingException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;


@Stateless
public class CompilationService {

    @PersistenceContext(unitName = "data_bases_2_project")
    private EntityManager em;

    public CompilationService() {
    }

    public Compilation createCompilation(Integer idUser, Integer idProduct, Timestamp log) throws CompilationAlreadyExistingException {
        if (em.createNamedQuery("Compilation.findByUser&ProductId").setParameter("idUser",idUser).setParameter("idProduct",idProduct).getResultList().size()==0) {
            throw new CompilationAlreadyExistingException("A compilation is already created for this user!");
        }
        User user = em.find(User.class, idUser);
        Product product = em.find(Product.class, idProduct);
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

    public List<Compilation> getOrderedCompilations(int productId) {
        List<Compilation> compilations = em.createNamedQuery("Compilation.getOrderedCompilation",Compilation.class).setParameter("idProduct",productId).getResultList();
        return compilations;
    }

    //methods to test many to many rel between compilation and question
    public void createAnswer(List<Integer> questionsId,List<String> answerText,int compilation) {
        Compilation c = em.find(Compilation.class,compilation);
        for(int i=0;i<questionsId.size();i++) {
            Question q = em.find(Question.class,questionsId.get(i));
            c.addAnswerQuestion(q,answerText.get(i));
        }
    }

}
