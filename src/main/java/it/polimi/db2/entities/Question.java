package it.polimi.db2.entities;

import javax.persistence.*;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @Column(name = "id_question")
    private Integer idQuestion;

    @Column(name = "questionnaire")
    private Integer questionnaire;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "type")
    private String type;

    public Integer getIdQuestion() {
        return this.idQuestion;
    }

    public void setIdQuestion(Integer idQuestion) {
        this.idQuestion = idQuestion;
    }

    public Integer getQuestionnaire() {
        return this.questionnaire;
    }

    public void setQuestionnaire(Integer questionnaire) {
        this.questionnaire = questionnaire;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
