package it.polimi.db2.entities;

import javax.persistence.*;

@Entity
@Table(name = "compilation")
public class Compilation {
    @Id
    @Column(name = "id_user")
    private Integer idUser;

    @Id
    @Column(name = "id_questionnaire")
    private Integer idQuestionnaire;

    @Column(name = "points")
    private Integer points;

    @Column(name = "deleted")
    private Byte deleted;

    @Column(name = "log")
    private java.sql.Timestamp log;

    public Integer getIdUser() {
        return this.idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdQuestionnaire() {
        return this.idQuestionnaire;
    }

    public void setIdQuestionnaire(Integer idQuestionnaire) {
        this.idQuestionnaire = idQuestionnaire;
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
