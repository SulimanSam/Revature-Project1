
import org.junit.jupiter.api.*;

import java.sql.*;


import static org.junit.jupiter.api.Assertions.*;

class TestHome {


    String URL = "jdbc:h2:./TempDate";
    String USER_NAME = "sam";
    String PASSWORD = "p4ssw0rd";

    @BeforeEach
    void setUp() {


        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql0 = "CREATE TABLE homes (\n" +
                    "\thome_id serial PRIMARY KEY\n" +
                    "\t, home_name VARCHAR(30) UNIQUE\n" +
                    "\t, home_space INTEGER NOT NULL\n" +
                    "\t, empty_space INTEGER NOT NULL  \n" +
                    " );";
            PreparedStatement ps0 = conn.prepareStatement(sql0);
            ps0.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql0 = "DROP TABLE homes";
            PreparedStatement ps0 = conn.prepareStatement(sql0);
            ps0.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    @Test
    public void h2Test() {
        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            /////////////////////////////////////////////////////////////////////
            String sql1 = "INSERT INTO homes VALUES(?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql1);
            ps.setInt(1, 1);
            ps.setString(2, "HomeTest1");
            ps.setInt(3, 8000);
            ps.setInt(4, 8000);

            ps.executeUpdate();
            /////////////////////////////////////////////////////////////////////
            String sql2 = "INSERT INTO homes VALUES(?,?,?,?)";

            ps = conn.prepareStatement(sql2);
            ps.setInt(1, 2);
            ps.setString(2, "HomeTest2");
            ps.setInt(3, 5000);
            ps.setInt(4, 5000);

            ps.executeUpdate();
            /////////////////////////////////////////////////////////////////////
            String sql3 = "INSERT INTO homes VALUES(?,?,?,?)";

            ps = conn.prepareStatement(sql3);
            ps.setInt(1, 3);
            ps.setString(2, "HomeTest3");
            ps.setInt(3, 9000);
            ps.setInt(4, 9000);

            ps.executeUpdate();
            /////////////////////////////////////////////////////////////////////
            String sql9 = "SELECT * FROM homes ORDER BY home_id";



        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}

