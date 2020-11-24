package it.polimi.db2.services;

import it.polimi.db2.entities.User;
import it.polimi.db2.exception.InvalidCredentialsException;
import it.polimi.db2.exception.InvalidRegistrationException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "data_bases_2_project")
    private EntityManager em;

    public void registerUser(String username, String email, String password) throws InvalidRegistrationException {
        String error = null;
        try {
            List<User> users = null;
            users = em.createNamedQuery("User.findUserByUsername",User.class).setParameter("username",username).getResultList();
            if(users.size()>0) error = "Error! Username already taken";

            users = em.createNamedQuery("User.findUserByEmail",User.class).setParameter("email",email).getResultList();
            if(users.size()>0) error = "Error! Email already taken";
        } catch(PersistenceException e) {
            error = "Error! Cannot complete registration process";
        }

        if(error!=null) {
            throw new InvalidRegistrationException(error);
        }

        User user = new User(username,email,password);
        em.persist(user);
    }

    public User checkCredentials(String username, String password) throws InvalidCredentialsException {
        String error = null;
        List<User> users = null;
        try {
            users =  em.createNamedQuery("User.findUserByCredentials",User.class).setParameter("username",username).setParameter("password",password).getResultList();
            if(users.size()>1) error = "Error! More than 1 users with same username";
        } catch (PersistenceException e) {
            error = "Error! Cannot complete login process";
        }

        if(error!=null) {
            throw new InvalidCredentialsException(error);
        }

        if(users.isEmpty()) return null;
        else return users.get(0);
    }

    public void blockUser(User user) {
        user.setBlocked(true);
        em.merge(user);
    }
}
