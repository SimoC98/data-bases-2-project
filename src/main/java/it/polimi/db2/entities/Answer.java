package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "answer")
@NamedQueries({
        @NamedQuery(name = "Answer.findByUserId", query = "SELECT a FROM Answer a WHERE a.compilation.user = :idUser"),
        @NamedQuery(name = "Answer.findByProductId", query = "SELECT a FROM Answer a WHERE a.compilation.product = :idProduct"),
        @NamedQuery(name = "Answer.findByQuestionId", query = "SELECT a FROM Answer a WHERE a.question.idQuestion = :idQuestion")
})
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_answer")
    private Integer idAnswer;

    @ManyToOne
    @JoinColumn(name = "question")
    private Question question;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="user"),
        @JoinColumn(name= "id_product")
    })
    private Compilation compilation;

    @Column(name = "answer_text")
    private String answerText;

    public Answer(Integer idAnswer, Question question,String answerText,Compilation compilation) {
        this.idAnswer = idAnswer;
        this.question = question;
        this.answerText = answerText;
        this.compilation = compilation;
    }

    public Answer() {

    }

    public Integer getIdAnswer() {
        return this.idAnswer;
    }

    public void setIdAnswer(Integer idAnswer) {
        this.idAnswer = idAnswer;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setCompilation(Compilation compilation){this.compilation = compilation;}

    public Compilation getCompilation(){return this.compilation;}

    public String getAnswerText() {
        return this.answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
