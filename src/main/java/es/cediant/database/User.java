package es.cediant.database;
// Generated Dec 26, 2012 11:28:55 AM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name="user", catalog="webdb", uniqueConstraints = @UniqueConstraint(columnNames="username"))
public class User  implements java.io.Serializable {

    private Integer id;
    private String username;
    private String password;
    private String photo;

    public User() {
    }
	
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public User(String username, String password, String photo) {
       this.username = username;
       this.password = password;
       this.photo = photo;
    }
   
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="username", unique=true, nullable=false, length=45)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name="password", nullable=false, length=45)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="photo", length=45)
    public String getPhoto() {
        return this.photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }

}


