package it.polimi.db2.services;


import it.polimi.db2.entities.*;
import it.polimi.db2.exception.BadWordException;
import it.polimi.db2.exception.CompilationAlreadyExistingException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Stateless
public class CompilationService {

    @PersistenceContext(unitName = "data_bases_2_project")
    private EntityManager em;

    public CompilationService() {
    }

    public Compilation createCompilation(Integer idUser, Integer idProduct, Timestamp log,int deleted) throws CompilationAlreadyExistingException {
        if (em.createNamedQuery("Compilation.findByUser&ProductId").setParameter("idUser",idUser).setParameter("idProduct",idProduct).getResultList().size()>0) {
            throw new CompilationAlreadyExistingException("A compilation is already created for this user!");
        }
        User user = em.find(User.class, idUser);
        Product product = em.find(Product.class, idProduct);
        Compilation compilation = new Compilation(user, product, log);
        compilation.setDeleted(deleted);
        user.addCompilation(compilation);
        product.addCompilation(compilation);
        em.persist(compilation);
        return compilation;
    }

    //TODO WITH TRIGGERS
    /*
    public Compilation addPointsToCompilation(){

    }*/

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
        List<Compilation> compilations = em.createNamedQuery("Compilation.getOrderedCompilation",Compilation.class).setParameter("idProduct",productId).setHint("javax.persistence.cache.storeMode ", "REFRESH").getResultList();
        return compilations;
    }

    //methods to test many to many rel between compilation and question
    public void createAnswer(List<Integer> questionsId,List<String> answerText,int compilation) throws BadWordException {
        for(String s : answerText) {
            checkBadWords(s);
        }

        Compilation c = em.find(Compilation.class,compilation);
        for(int i=0;i<questionsId.size();i++) {
            Question q = em.find(Question.class,questionsId.get(i));
            String a = answerText.get(i);
            if(q.getType().equals(QuestionType.DYNAMIC) && a.length()==0) {
                //throw exception
            }
            if(a.length()>0) c.addAnswerQuestion(q,a);
        }
    }

    public void checkBadWords(String s) throws BadWordException{
        List<BadWord> b = em.createNamedQuery("BadWord.checkBadWords",BadWord.class).getResultList();
        List<String> w = new ArrayList<>();
        b.stream().forEach(x -> w.add(x.getBadWord()));
        String arr[] = s.split(" ");
        for(int i=0;i< arr.length;i++) {
            if (w.contains(arr[i])) {
                throw new BadWordException();
            }
        }
    }
}
