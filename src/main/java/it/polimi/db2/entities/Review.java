package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "review")
public class Review implements Serializable {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReview;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "review_text")
    private String reviewText;

    public User getUser() {
        return user;
    }

    public Review(){}

    public Review(String text){
        this.reviewText = text;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getReviewText() {
        return this.reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Integer getReview() {
        return this.idReview;
    }

    public void setReview(Integer idReview) {
        this.idReview = idReview;
    }
}
