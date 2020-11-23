package it.polimi.db2.services;
import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Review;
import it.polimi.db2.entities.User;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;

@Stateless
public class ReviewService {
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
