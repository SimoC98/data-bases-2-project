package it.polimi.db2.entities;

import javax.persistence.*;

@Entity
@Table(name = "questionnaire")
public class Questionnaire {
    @Id
    @Column(name = "id_questionnaire")
    private Integer idQuestionnaire;

    @Column(name = "product")
    private Integer product;

    public Integer getIdQuestionnaire() {
        return this.idQuestionnaire;
    }

    public void setIdQuestionnaire(Integer idQuestionnaire) {
        this.idQuestionnaire = idQuestionnaire;
    }

    public Integer getProduct() {
        return this.product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }
}
