package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
@NamedQueries({
        @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
        @NamedQuery(name = "Product.findByDate", query = "SELECT p FROM Product p WHERE p.date = :date")
})
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Integer idProduct;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Float price;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private LocalDate date;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private List<Compilation> compilations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public Product(){}

    public Product(String name, String description, Float price, LocalDate date, byte[] image){
        this.name = name;
        this.description = description;
        this.price = price;
        this.date = date;
        this.image = image;
    }

    public Integer getIdProduct() {
        return this.idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<Question> getQuestions() { return this.questions; }

    public void addQuestion(Question question){
        getQuestions().add(question);
        question.setProduct(this);
    }

    public List<Compilation> getCompilations() { return this.compilations; }

    public void addCompilation(Compilation compilation){
        getCompilations().add(compilation);
        //compilation.setProduct(this);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        getReviews().add(review);
        review.setProduct(this);
    }
}
