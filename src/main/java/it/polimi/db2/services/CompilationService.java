package it.polimi.db2.services;


import it.polimi.db2.entities.Compilation;
import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.User;
import it.polimi.db2.exception.CompilationAlreadyExistingException;
import it.polimi.db2.exception.ProductAlreadyExistingException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;


@Stateless
public class CompilationService {

    @PersistenceContext(unitName = "data_bases_2_project")
    private EntityManager em;

    public CompilationService() {
    }

    public Compilation createCompilation(String username, Integer idProduct, Timestamp log, Boolean deleted, Integer points) throws CompilationAlreadyExistingException {

        if (em.find(Compilation.class, username) != null) {
            throw new CompilationAlreadyExistingException("A compilation is already created for this user!");
        }
        User user = ((User) em.createNamedQuery("User.findUserByUsername").setParameter("username", username).getSingleResult());
        Product product = (Product) em.createNamedQuery("Product.findById").setParameter("idProduct", idProduct).getSingleResult();
        Compilation compilation = new Compilation(user, product, log, deleted, points);
        em.persist(compilation);
        return compilation;

    }


}
