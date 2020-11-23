package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "answer")
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
