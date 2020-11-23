package it.polimi.db2.entities;

import javax.persistence.*;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @Column(name = "id_review")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReview;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(name = "review_text")
    private Integer reviewText;

    public User getIdUser() {
        return user;
    }

    public void setIdUser(User user) {
        this.user = user;
    }

    public Product getIdProduct() {
        return product;
    }

    public void setIdProduct(Product product) {
        this.product = product;
    }

    public Integer getReviewText() {
        return this.reviewText;
    }

    public void setReviewText(Integer reviewText) {
        this.reviewText = reviewText;
    }

    public Integer getIdReview() {
        return this.idReview;
    }

    public void setIdReview(Integer idReview) {
        this.idReview = idReview;
    }
}
