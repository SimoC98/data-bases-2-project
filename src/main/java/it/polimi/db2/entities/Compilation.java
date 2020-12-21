package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

@Entity
@Table(name = "compilation")
@NamedQueries({
        @NamedQuery(name = "Compilation.findByProductId", query = "SELECT c FROM Compilation c WHERE c.product.idProduct = :idProduct"),
        @NamedQuery(name = "Compilation.findByUserId", query = "SELECT c FROM Compilation c WHERE c.user.id = :idUser"),
        @NamedQuery(name = "Compilation.findByUser&ProductId", query = "SELECT c FROM Compilation c WHERE c.user.id = :idUser AND c.product.idProduct = :idProduct"),
        @NamedQuery(name = "Compilation.getOrderedCompilation", query = "SELECT c FROM Compilation c WHERE c.product.idProduct = :idProduct order by c.points desc"),
        @NamedQuery(name = "Compilation.getCompilationList", query = "SELECT c FROM Compilation c WHERE c.product.idProduct = :idProduct AND c.deleted = 0"),
        @NamedQuery(name = "Compilation.getDeletedCompilation", query = "SELECT c FROM Compilation c WHERE c.product.idProduct = :idProduct AND c.deleted = 1")
})
public class Compilation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    private Integer idCompilation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "deleted")
    private int deleted;

    @Column(name = "log")
    private java.sql.Timestamp log;

    @Column(name = "points")
    private Integer points;

    //map many to many rel with question with a map
    @ElementCollection(fetch= FetchType.EAGER)
    @CollectionTable(name = "answer", joinColumns = @JoinColumn(name = "compilation_id"))
    @MapKeyJoinColumn(name = "question_id")
    @Column(name = "answer_text")
    private Map<Question,String> answersQuestions;

    public Compilation(){}

    public Compilation(User user, Product product, Timestamp log){
        this.user = user;
        this.product = product;
        this.log = log;
        this.points = 0;
    }

    public void deleteAllAnswers(){
        this.getAnswersQuestions().clear();
    }

    public void addAnswerPoints(int points){
        this.points += points;
    }

    public int getDeleted() {
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

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public int isDeleted() {
        return this.deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public java.sql.Timestamp getLog() {
        return this.log;
    }

    public void setLog(java.sql.Timestamp log) {
        this.log = log;
    }

    public void addAnswerQuestion(Question q, String answer_text) {
        answersQuestions.put(q,answer_text);
    }

    public void deleteAnswerQuestion(Question q) {
        answersQuestions.remove(q);
    }

    public Map<Question, String> getAnswersQuestions() {
        return answersQuestions;
    }

    public Integer getIdCompilation() {
        return idCompilation;
    }

    public void setAnswersQuestions(Map<Question, String> answersQuestions) {
        this.answersQuestions = answersQuestions;
    }
}
