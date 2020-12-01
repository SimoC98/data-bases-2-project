package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "compilation")
@NamedQueries({
        @NamedQuery(name = "Compilation.findByProductId", query = "SELECT c FROM Compilation c WHERE c.product.idProduct = :idProduct"),
        @NamedQuery(name = "Compilation.findByUserId", query = "SELECT c FROM Compilation c WHERE c.user.id = :idUser"),
        @NamedQuery(name = "Compilation.findByUser&ProductId", query = "SELECT c FROM Compilation c WHERE c.user = :idUser AND c.product = :idProduct")
})
public class Compilation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compilation")
    private Integer idCompilation;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "log")
    private java.sql.Timestamp log;

    @Column(name = "points")
    private Integer points;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compilation", cascade = CascadeType.ALL)
    private List<Answer> answers;

    public Compilation(){}

    public Compilation(User user, Product product, Timestamp log){
        this.user = user;
        this.product = product;
        this.log = log;
        this.points = 0;
    }

    public void addAnswerPoints(int points){
        this.points += points;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product product() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer a) {
        getAnswers().add(a);
        a.setCompilation(this);
    }

    public void removeAnswer(Answer a) {
        getAnswers().remove(a);
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public java.sql.Timestamp getLog() {
        return this.log;
    }

    public void setLog(java.sql.Timestamp log) {
        this.log = log;
    }
}
