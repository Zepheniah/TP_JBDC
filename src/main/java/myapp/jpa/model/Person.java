package myapp.jpa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity(name = "Person")

@Table(name = "TPerson",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "first_name", "birth_day"
                })
        })

@NamedQueries({
        @NamedQuery(name = "findAllPersonWithName",
                query = "SELECT p FROM Person p WHERE p.firstName LIKE :custName"),
        @NamedQuery(name = "FindAllPersonWithCar",
                query = "SELECT c.owner FROM Car c WHERE c.model LIKE :model")
})


public class Person implements Serializable {
    @OneToMany(//
            cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE },
            fetch = FetchType.LAZY, mappedBy = "owner")
    @OrderBy("immatriculation ASC")
    private Set<Car> cars;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = { CascadeType.MERGE, CascadeType.PERSIST }
    )
    @JoinTable(
            name = "Person_Movie",
            joinColumns = { @JoinColumn(name = "id_person") },
            inverseJoinColumns = { @JoinColumn(name = "id_movie") }
    )
    Set<Movie> movies;

    private static final long serialVersionUID = 1L;

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic(optional = false)
    @Column(name = "first_name", length = 200,
            nullable = false, unique = false)
    private String firstName;

    @Basic()
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birth_day")
    private Date birthDay;

    @Version()
    private long version = 0;

    @Transient
    public static long updateCounter = 0;

    public Person() {
        super();
    }

    public Person(String firstName, Date birthDay) {
        super();
        this.firstName = firstName;
        this.birthDay = birthDay;
    }
    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie movie) {
        if (movies == null) {
            movies = new HashSet<>();
        }
        movies.add(movie);
    }
    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public void addCar(Car c) {
        if (cars == null) {
            cars = new HashSet<>();
        }
        cars.add(c);
        c.setOwner(this);
    }

    @PreUpdate
    public void beforeUpdate() {
        System.err.println("PreUpdate of " + this);
    }

    @PostUpdate
    public void afterUpdate() {
        System.err.println("PostUpdate of " + this);
        updateCounter++;
    }

    @Override
    public String toString() {
        return "Person(id=" + getId() + "," + firstName + "," + birthDay + ","
                + ",v" + getVersion() + ")";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

}