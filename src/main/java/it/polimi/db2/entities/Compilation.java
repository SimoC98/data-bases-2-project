package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "compilation")
@NamedQueries({
        @NamedQuery(name = "Compilation.findByProductId", query = "SELECT c FROM Compilation c WHERE c.product = :product"),
        @NamedQuery(name = "Compilation.findByUserId", query = "SELECT c FROM Compilation c WHERE c.user = :user")
})
public class Compilation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "log")
    private java.sql.Timestamp log;

    @Column(name = "points")
    private Integer points;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compilation", cascade = CascadeType.ALL)
    private List<Answer> answers;

    public Compilation(){}

    public Compilation(Timestamp log, Boolean deleted, Integer points){
        this.log = log;
        this.deleted = deleted;
        this.points = points;
    }

    public Compilation(User user, Product product, Timestamp log, Boolean deleted, Integer points){
        this.log = log;
        this.deleted = deleted;
        this.points = points;
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
