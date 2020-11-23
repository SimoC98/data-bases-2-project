package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "question", schema = "heroku_7795bfa940122a4")
@NamedQueries({
        @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
        @NamedQuery(name = "Question.findByProduct", query = "Select q FROM Question q WHERE q.product.idProduct = :pId") ,
        @NamedQuery(name = "Question.findFixed", query = "SELECT q FROM Question q WHERE q.type = 2") //todo check
})

public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_question")
    private Integer idQuestion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "type")
    private QuestionType type;

    public Question(){}

    public Question(String questionText, QuestionType type){
        this.questionText = questionText;
        this.type = type;
    }

    public Integer getIdQuestion() {
        return this.idQuestion;
    }

    public void setIdQuestion(Integer idQuestion) {
        this.idQuestion = idQuestion;
    }

    public List<Answer> getAnswers(){
        return this.answers;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getType() {
        return this.type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public void addAnswer(Answer answer){
        getAnswers().add(answer);
        answer.setQuestion(this);
        //TODO check bad-words
    }

    public void deleteAnswer(Answer answer){
        getAnswers().remove(answer);
    }
}
