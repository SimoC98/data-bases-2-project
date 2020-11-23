package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "review")
public class Review implements Serializable {
    @Id
    @Column(name = "id_review")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReview;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User idUser;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product idProduct;

    @Column(name = "review_text")
    private String reviewText;

    public Review(String text){
        this.reviewText = text;
    }

    public User getUser() {
        return idUser;
    }

    public void setUser(User idUser) {
        this.idUser = idUser;
    }

    public Product getProduct() {
        return idProduct;
    }

    public void setProduct(Product idProduct) {
        this.idProduct = idProduct;
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
