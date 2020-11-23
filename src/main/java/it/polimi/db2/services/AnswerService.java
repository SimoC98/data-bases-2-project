package it.polimi.db2.services;

import it.polimi.db2.entities.Answer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AnswerService {

    @PersistenceContext(unitName = "data_bases_2_project")
    private EntityManager entityManager;

    public AnswerService() {
    }

    public Answer getAnswerById(int answerId) {
        return (Answer) entityManager.find(Answer.class, answerId);
    }

}
