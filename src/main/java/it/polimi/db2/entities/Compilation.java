package it.polimi.db2.entities;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "compilation")
public class Compilation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(name = "points")
    private Integer points;


    @Column(name = "deleted")
    private Byte deleted;

    @Column(name = "log")
    private java.sql.Timestamp log;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compilation", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compilation", cascade = CascadeType.ALL)
    private List<User> users;

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

    public void addUser(User u) {
        getUsers().add(u);
    }

    public void removeUser(User u) {
        getUsers().remove(u);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Byte getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    public java.sql.Timestamp getLog() {
        return this.log;
    }

    public void setLog(java.sql.Timestamp log) {
        this.log = log;
    }
}
