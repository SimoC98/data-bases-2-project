package it.polimi.db2.services;
import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Review;
import it.polimi.db2.entities.User;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ReviewService {
    @PersistenceContext(unitName = "data_bases_2_project")
    private EntityManager em;

    public ReviewService() {}
    public Review createReview(User user, Product product, String text){
        Review review = new Review(text);
        user.addReview(review);
        product.addReview(review);
        em.persist(review);
        return review;
    }
}
