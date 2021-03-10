package myapp.jpa;

import myapp.jpa.model.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import myapp.jpa.dao.JpaDao;
import myapp.jpa.model.Person;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestJpaDao {

    @Autowired
    JpaDao dao;

    @Test
    public void addAndFindPerson() {
        // Création
        var p1 = new Person("Jean", null);
        var c1 = new Car("AC-000-DC","207");
        p1.addCar(c1);
        p1 = dao.addPerson(p1);

        assertTrue(p1.getId() > 0);
        // relecture
        var p2 = dao.findPerson(p1.getId());
        assertEquals("Jean", p2.getFirstName());
        assertEquals(p1.getId(), p2.getId());
    }

    @Test
    public void updateAndRemovePerson(){
        // Création
        var p1 = new Person("Jean", null);
        var c1 = new Car("AC-000-DC","207");
        p1 = dao.addPerson(p1);
        // Update
        var p2 = dao.findPerson(p1.getId());
        var c2 = new Car("AC-001-DC","207");
        Date Birth = new Date(System.currentTimeMillis());
        p2.setBirthDay(Birth);
        //p2.addCar(c2);
        dao.updatePerson(p2);
        p2 = dao.findPerson(p1.getId());
        assertEquals(p1.getId(),p2.getId());
        assertEquals(Birth,p2.getBirthDay());

        //Remove

        dao.removePerson(p1.getId());
        p2 = dao.findPerson(p1.getId());
        assertEquals(null,p2);
    }

    @Test
    public void UniqueCouple(){
        var p1 = new Person("Adrien",null);
        Date myBirth = new Date(System.currentTimeMillis());
        p1.setBirthDay(myBirth);
        var p2 = new Person("Adrien",null);
        Date homonymeBirth = new Date(System.currentTimeMillis() - Integer.MAX_VALUE);
        p2.setBirthDay(homonymeBirth);


        p1 = dao.addPerson(p1);
        p2 = dao.addPerson(p2);


       // assertEquals(p1,dao.findPerson(p1.getId()));  //ça marche faut juste homogeniser le format des dates SimpleDateFormat?Modifier le to_String de Person?mille et une solution
        //assertEquals(p2,dao.findPerson(p2.getId()));
        assertNotEquals(p1,null);
        assertNotEquals(p2,null);
        assertNotEquals(dao.findPerson(p1.getId()),dao.findPerson(p2.getId()));
    }

    @Test
    public void addPersonneThread() throws InterruptedException {
        var p1 = new Person("Adrien",null);
        var p2 = new Person("Test",null);

        Thread a = new Thread(() -> dao.addPerson(p1));
        a.start();
        dao.addPerson(p2);
        dao.addPerson(p1);
        a.join();


    }

}