package it.polimi.db2.services;


import it.polimi.db2.entities.*;
import it.polimi.db2.exception.BadWordException;
import it.polimi.db2.exception.CompilationAlreadyExistingException;
import it.polimi.db2.exception.EmptyAnswerException;
import it.polimi.db2.exception.InvalidProductQuestionAssociationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class CompilationService {

    @PersistenceContext(unitName = "data_bases_2_project")
    private EntityManager em;

    @EJB
    private QuestionService questionService;

    public CompilationService() {
    }

    public Compilation createCompilation(Integer idUser, Integer idProduct, Timestamp log, int deleted) throws CompilationAlreadyExistingException {
        if (em.createNamedQuery("Compilation.findByUser&ProductId").setParameter("idUser", idUser).setParameter("idProduct", idProduct).getResultList().size() > 0) {
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

    public void deleteCompilationByProductId(Integer idProduct) {
        List<Compilation> compilationsToDelete = em.createNamedQuery("Compilation.findByProductId", Compilation.class).setParameter("idProduct", idProduct).getResultList();
        questionService.deleteQuestionsByProductId(idProduct);
        User user;
        Product product;
        if (!compilationsToDelete.isEmpty()) {
            for (Compilation c : compilationsToDelete) {
                if (!c.getAnswersQuestions().isEmpty()) c.deleteAllAnswers();
                user = c.getUser();
                user.removeCompilation(c);
                product = c.product();
                product.removeCompilation(c);
                em.remove(c);
            }
        }
    }

    public List<Compilation> getOrderedCompilations(int productId) {
        return em.createNamedQuery("Compilation.getOrderedCompilation", Compilation.class).setParameter("idProduct", productId).setHint("javax.persistence.cache.storeMode ", "REFRESH").getResultList();
    }

    //methods to test many to many rel between compilation and question
    public void createAnswer(List<Integer> questionsId, List<String> answerText, int compilation) throws BadWordException, EmptyAnswerException, InvalidProductQuestionAssociationException {
        ZoneId zoneId = ZoneId.of("Europe/Rome");
        LocalDate today = LocalDate.now(zoneId);

        for (String s : answerText) {
            checkBadWords(s);
        }

        Compilation c = em.find(Compilation.class, compilation);
        for (int i = 0; i < questionsId.size(); i++) {
            Question q = em.find(Question.class, questionsId.get(i));
            if(!q.getProduct().getDate().equals(today)) {
                throw new InvalidProductQuestionAssociationException();
            }
            String a = answerText.get(i);
            if (q.getType().equals(QuestionType.DYNAMIC) && a.length() == 0) {
                throw new EmptyAnswerException("Empty answer for question:" + q.getQuestionText());
            }
            if (a.length() > 0) c.addAnswerQuestion(q, a);
        }
    }

    public void checkBadWords(String s) throws BadWordException {
        List<BadWord> b = em.createNamedQuery("BadWord.checkBadWords", BadWord.class).getResultList();
        List<String> w = new ArrayList<>();
        b.forEach(x -> w.add(x.getBadWord()));
        String[] arr = s.split(" ");
        for (String value : arr) {
            if (w.contains(value)) {
                throw new BadWordException();
            }
        }
    }

    public List<Compilation> getDeletedCompilation(int productId) {
        return em.createNamedQuery("Compilation.getDeletedCompilation", Compilation.class).setParameter("idProduct", productId).setHint("javax.persistence.cache.storeMode ", "REFRESH").getResultList();
    }

    public List<Compilation> getCompilationList(int productId) {
        return em.createNamedQuery("Compilation.getCompilationList", Compilation.class).setParameter("idProduct", productId).setHint("javax.persistence.cache.storeMode ", "REFRESH").getResultList();
    }
}
