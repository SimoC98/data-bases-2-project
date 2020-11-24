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
    public Review createReview(int user_id, int product_id, String text){
        User user = em.find(User.class,user_id);
        Product product = em.find(Product.class,product_id);
        Review review = new Review(text);
        user.addReview(review);
        product.addReview(review);
        em.persist(review);
        return review;
    }
}
