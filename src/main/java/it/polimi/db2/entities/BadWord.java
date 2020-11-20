package it.polimi.db2.entities;

import javax.persistence.*;

@Entity
@Table(name = "bad_word")
public class BadWord {
    @Id
    @Column(name = "id_bad_word")
    private Integer idBadWord;

    @Column(name = "bad_word")
    private String badWord;

    public Integer getIdBadWord() {
        return this.idBadWord;
    }

    public void setIdBadWord(Integer idBadWord) {
        this.idBadWord = idBadWord;
    }

    public String getBadWord() {
        return this.badWord;
    }

    public void setBadWord(String badWord) {
        this.badWord = badWord;
    }
}
