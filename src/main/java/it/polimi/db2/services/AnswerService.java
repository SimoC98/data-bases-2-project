package it.polimi.db2.services;

import it.polimi.db2.entities.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.List;

@Stateless
public class AnswerService {

    @PersistenceContext(unitName = "data_bases_2_project")
    private EntityManager em;

    public AnswerService() {
    }

    public Answer getAnswerById(int answerId) {
        return (Answer) em.find(Answer.class, answerId);
    }

    public Answer insertAnswer(Integer idAnswer, Integer idQuestion, Integer idUser, Integer idProduct, String answerText, Timestamp log,Boolean deleted){
        User u = em.find(User.class, idUser);
        Question q = em.find(Question.class, idQuestion);
        Product p = em.find(Product.class, idProduct);
        Compilation c = (Compilation) em.createNamedQuery("Compilation.findByUser&ProductId").setParameter("idUser",idUser).setParameter("idProduct",idProduct).getSingleResult();
        Answer a = new Answer(idAnswer,q,answerText,c);
        em.persist(a);
        return a;
    }


}
