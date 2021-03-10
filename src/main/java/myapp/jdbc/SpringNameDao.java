package myapp.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class SpringNameDao {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jt;

    // Nouvelle version
    private Connection newConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @PostConstruct
    public void initSchema() throws SQLException {
        var query = "create table if not exists NAME (" //
                + " id integer not null, " //
                + " name varchar(50) not null, " //
                + " primary key (id) )";
        jt.update(query);

    }

    public void addName(int id, String name) throws SQLException {
        var query = "insert into NAME values (?,?)";
        jt.update(query,id,name);
    }

    public void deleteName(int id) throws SQLException {
        var query = "Delete From NAME where (id = ?)";
        jt.update(query,id);
    }

    public String findName(int id) throws SQLException {
        var query = "Select * From NAME where (id = ?)";
        jt.update(query,id);
        return null;
    }

    public Collection<String> findNames() throws SQLException {
        var query = "Select * From NAME order by name";
        var result = new LinkedList<String>();
        try (var conn = newConnection()) {
            var st = conn.createStatement();
            var rs = st.executeQuery(query);
            while (rs.next()) {
                result.add(rs.getString("name"));
            }
        }
        return result;
    }
    public void UpdateNames(int id,String newName) throws SQLException {
        var query = "UPDATE NAME SET name = ? where (id =?)";
        try (var conn = newConnection()) {
            var st = conn.prepareStatement(query);
            st.setInt(2, id);
            st.setString(1, newName);
            st.execute();
        }
    }


    public void longWork() {
        try (var c = newConnection()) {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        } catch (SQLException e1) {
        }
    }
}
