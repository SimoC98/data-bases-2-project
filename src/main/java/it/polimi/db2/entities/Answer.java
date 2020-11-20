package it.polimi.db2.entities;

import javax.persistence.*;

@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @Column(name = "id_answer")
    private Integer idAnswer;

    @Column(name = "question")
    private Integer question;

    @Column(name = "user")
    private Integer user;

    @Column(name = "questionnaire")
    private Integer questionnaire;

    @Column(name = "answer_text")
    private String answerText;

    public Integer getIdAnswer() {
        return this.idAnswer;
    }

    public void setIdAnswer(Integer idAnswer) {
        this.idAnswer = idAnswer;
    }

    public Integer getQuestion() {
        return this.question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public Integer getUser() {
        return this.user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getQuestionnaire() {
        return this.questionnaire;
    }

    public void setQuestionnaire(Integer questionnaire) {
        this.questionnaire = questionnaire;
    }

    public String getAnswerText() {
        return this.answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
