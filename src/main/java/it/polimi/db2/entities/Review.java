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
    private User idUser;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product idProduct;

    @Column(name = "review_text")
    private Integer reviewText;


    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Product getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Product idProduct) {
        this.idProduct = idProduct;
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
