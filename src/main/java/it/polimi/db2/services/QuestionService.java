package it.polimi.db2.services;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Question;
import it.polimi.db2.entities.QuestionType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
public class QuestionService {
    private EntityManager em;

    public QuestionService(){}

    public void createFixedQuestion(String text){
        Question q = new Question(text, QuestionType.FIXED);
        em.persist(q);
    }

    public void createQuestion(String text, int productId){
        Product product = em.find(Product.class, productId);
        Question q = new Question(text, QuestionType.DYNAMIC);
        product.addQuestion(q);
        em.persist(product);
    }

    public List<Question> findQuestionByProduct(int productId){
        List<Question> questions = em.createNamedQuery("Question.findByProduct", Question.class).
                setParameter("pId", productId).getResultList();
        return questions;
    }

    public List<Question> findFixedQuestion(){
        List<Question> questions = em.createNamedQuery("Question.findFixed", Question.class).getResultList();
        return questions;
    }
}
