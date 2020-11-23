package it.polimi.db2.entities;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
@NamedQueries({@NamedQuery(name = "User.findUserByUsername", query = "select u from User u where u.username = :username"),
        @NamedQuery(name = "User.findUserByEmail", query="select u from User u where u.email = :email"),
        @NamedQuery(name = "User.findUserByCredentials", query = "select u from User u where u.username=:username and u.password=:password")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "blocked")
    private boolean blocked;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "user", cascade = CascadeType.ALL)
    private List<Compilation> compilations;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        //this.compilations = new ArrayList<Compilation>();
    }



    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getBlocked() {
        return this.blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void addCompilation(Compilation compilation) {
        this.compilations.add(compilation);
        compilation.setUser(this);
    }
}