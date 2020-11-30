package it.polimi.db2.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CompilationKey implements Serializable {
    @Column
    private Integer user_id;
    @Column
    private Integer product_id;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompilationKey that = (CompilationKey) o;
        return Objects.equals(user_id, that.user_id) &&
                Objects.equals(product_id, that.product_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, product_id);
    }
}
