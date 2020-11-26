package it.polimi.db2.services;

import it.polimi.db2.entities.Answer;
import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Question;
import it.polimi.db2.entities.QuestionType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
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

    public Question createQuestion(String text, int productId) {
        Product product = em.find(Product.class, productId);
        Question q = new Question(text, QuestionType.DYNAMIC);
        product.addQuestion(q);
        em.persist(product);
        return q;
    }

    public List<Question> findQuestionByProduct(int productId) {
        List<Question> questions = em.createNamedQuery("Question.findByProduct", Question.class).
                setParameter("pId", productId).getResultList();
        return questions;
    }

    public List<Question> findFixedQuestion() {
        List<Question> questions = em.createNamedQuery("Question.findFixed", Question.class).getResultList();
        return questions;
    }

    public void deleteQuestionsByProductId(Integer idProduct) {

        Product p = em.find(Product.class, idProduct);
        List<Question> dynamicQuestions = em.createNamedQuery("Question.findByProduct", Question.class).setParameter("pId", idProduct).getResultList();
        List<Question> fixedQuestions = em.createNamedQuery("Question.findFixed", Question.class).getResultList();
        for (Question q : dynamicQuestions) {
            em.remove(q);
        }

        for (Question q : fixedQuestions) {
            List<Answer> answers = new ArrayList<Answer>(q.getAnswers());
            answers.stream().filter(answer -> answer.getCompilation().getProduct().getIdProduct().equals(idProduct)).forEach(answer -> q.getAnswers().remove(answer));
        }
        p.removeQuestions();

    }


}
