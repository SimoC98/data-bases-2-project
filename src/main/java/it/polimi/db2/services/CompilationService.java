package it.polimi.db2.services;


import it.polimi.db2.entities.Compilation;
import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;


@Stateless
public class CompilationService {

    @PersistenceContext(unitName = "data_bases_2_project")
    private EntityManager em;

    public CompilationService(){}

    public Compilation createCompilation(String username, Integer productId, Timestamp log, Boolean deleted, Integer points){
        if (deleted){
            return null;
        }
        User user = ((User) em.createNamedQuery("User.findUserByUsername").setParameter("username", username).getSingleResult());
        Compilation compilation = new Compilation();
        return compilation;
    }
}
