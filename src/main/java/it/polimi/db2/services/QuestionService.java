package it.polimi.db2.services;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Question;
import it.polimi.db2.entities.QuestionType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class QuestionService {
    @PersistenceContext(unitName = "data_bases_2_project")
    private EntityManager em;

    public QuestionService() {
    }

    public Question createFixedQuestion(String text) {
        Question q = new Question(text, QuestionType.FIXED);
        em.persist(q);
        return q;
    }

    public void createQuestion(String text, int productId) {
        Product product = em.find(Product.class, productId);
        Question q = new Question(text, QuestionType.DYNAMIC);
        product.addQuestion(q);
        em.persist(product);
    }

    public List<Question> findQuestionByProduct(int productId) {
        List<Question> questions;
        Product p = em.find(Product.class, productId);
        questions = p.getQuestions();
        return questions;
    }

    public void deleteQuestionsByProductId(Integer idProduct) {

        Product p = em.find(Product.class, idProduct);
        List<Question> dynamicQuestions = em.createNamedQuery("Question.findByProduct", Question.class).setParameter("pId", idProduct).getResultList();
        for (Question q : dynamicQuestions) {
            if (q.getType().equals(QuestionType.DYNAMIC)) em.remove(q);
        }
        p.removeQuestions();

    }


}
