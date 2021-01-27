package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name = "product")
@NamedQueries({
        @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
        @NamedQuery(name = "Product.findByDate", query = "SELECT p FROM Product p WHERE p.date = :date"),
        @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.idProduct = :idProduct"),
        @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name =:name"),
        @NamedQuery(name = "Product.findAllByPreviousDate", query = "SELECT p FROM Product p WHERE p.date <= :date")
})
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
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
    @Basic(fetch = FetchType.EAGER)
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
        questions = new ArrayList<>();
        compilations = new ArrayList<>();
        reviews = new ArrayList<>();
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

    public String getImageData() {
        return Base64.getMimeEncoder().encodeToString(image);
    }

    public List<Question> getQuestions() { return this.questions; }

    public void addQuestion(Question question){
        getQuestions().add(question);
        question.setProduct(this);
    }
    public void removeQuestions(){
        getQuestions().clear();
    }

    public List<Compilation> getCompilations() { return this.compilations; }

    public void addCompilation(Compilation compilation){
        getCompilations().add(compilation);
    }

    public void removeCompilation(Compilation compilation){
        getCompilations().remove(compilation);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        getReviews().add(review);
        review.setProduct(this);
    }
}
