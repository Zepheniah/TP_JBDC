package myapp.jpa.dao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.*;

import org.springframework.stereotype.Service;

import myapp.jpa.model.Person;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class JpaDao {

    @PersistenceContext
    EntityManager em;

    public List<Person> findAllPersons() {
        String query = "SELECT p FROM Person p";
        TypedQuery<Person> q = em.createQuery(query, Person.class);
        return q.getResultList();
    }


    public Person addPerson(Person p) {
        em.persist(p);

        return (p);
    }

    /*
     * Charger une personne
     */
    public Person findPerson(long id) {
        Person to_return = em.find(Person.class,id);
        if(to_return != null) to_return.getCars().size();
        return to_return;
    }
    /*
    * Met à jour une personne
     */
    public void updatePerson(Person p){
        em.merge(p);
    }
    /*
    * Supprime une personne
     */
    public void removePerson(long id){
        Person toDelete = em.find(Person.class,id);
        em.remove(toDelete);
    }

    public List<Person> findPersonsByCarModel(String model) {
        List<Person> listPer = new ArrayList<>();
        listPer = em.createNamedQuery("FindAllPersonWithCar").setParameter("model",model).getResultList();
        return listPer;
    }

    /*
     * Fermeture d'un EM (avec rollback éventuellement)
     */
    private void closeEntityManager(EntityManager em) {
        if (em == null || !em.isOpen())
            return;

        var t = em.getTransaction();
        if (t.isActive()) {
            try {
                t.rollback();
            } catch (PersistenceException e) {
                e.printStackTrace(System.err);
            }
        }
        em.close();
    }



}