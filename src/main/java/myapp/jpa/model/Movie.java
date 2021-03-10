package myapp.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Movie implements Serializable {

    // for serialization
    private static final long serialVersionUID = 1L;

    // primary key
    @Id()
    @GeneratedValue()
    private Long id;

    @Column(length = 150, nullable = false)
    private String name;

    public Movie() {
        super();
    }

    public Movie(String name) {
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        return "Movie(" + id + "," + name + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Movie)
            return (((Movie) obj).getId() == id);
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}