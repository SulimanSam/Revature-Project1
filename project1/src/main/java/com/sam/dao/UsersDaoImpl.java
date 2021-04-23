package com.sam.dao;

import com.sam.model.User;
import static com.sam.ConnectionHelper.ConnectionInfo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDaoImpl implements UsersDAO{

    @Override
    public List<User> selectAllUsers() {

        List<User> users = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql = "SELECT u.*, ur.user_role AS userRole FROM users u\n" +
                    "                    INNER JOIN user_roles ur ON ur.role_id = u.user_role_id";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                users.add(new User(rs.getInt(1),
                        rs.getString(2),
                        "",
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getInt(8),
                        rs.getString("userrole"))
                );
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public int insertUser(User user) {

        int insertedID=0;
       try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

           String sql = "INSERT INTO USERS VALUES(default,?,?,?,?,?,default,default,(SELECT role_id from user_roles WHERE user_role = ?))";

           PreparedStatement ps = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
           ps.setString(1, user.getUserName().toLowerCase());
           ps.setString(2, user.getPassword());
           ps.setString(3, user.getFirstName());
           ps.setString(4, user.getLastName());
           ps.setString(5, user.getEmail().toLowerCase());
           ps.setString(6, user.getRole());


            ps.executeUpdate();
           ResultSet generatedKeys = ps.getGeneratedKeys();
           if (generatedKeys.next()) {
               insertedID = generatedKeys.getInt(1);

           }

        }catch(SQLException e){
            e.printStackTrace();
        }
       return insertedID;
    }

    @Override
    public User selectUserById(int id) {
        User user=null;

        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql = "SELECT u.*, ur.user_role AS userRole FROM users u \n" +
                    " INNER JOIN user_roles ur ON ur.role_id = u.user_role_id WHERE user_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery(); //<----query not update

            if(rs.next())
            user = new User(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getDate(7),
                    rs.getInt(8),
                    rs.getString("userrole"));


        }catch(SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User selectUserByNameOrEmail(String name) {
        User user=null;

        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql = "SELECT u.*, ur.user_role AS userRole FROM users u \n" +
                    " INNER JOIN user_roles ur ON ur.role_id = u.user_role_id WHERE user_name = ? OR user_email=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, name);

            ResultSet rs = ps.executeQuery();
            if(rs.next())
            user = new User(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getDate(7),
                    rs.getInt(8),
                    rs.getString("userrole"));


        }catch(SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public boolean checkIfUserExist(User user) {

        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql = "SELECT * FROM users \n" +
                    " WHERE user_name = ? OR user_email=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());

            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;


        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateUser(User user) {
        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql = "UPDATE users SET first_name=?, last_Name= ? WHERE user_id = ? ";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getUserID());

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    @Override
    public void updateUserPassword(User user) {
        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql = "UPDATE users SET first_name=?, last_Name= ?,pass=?, change_password=2 WHERE user_id = ? ";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getUserID());

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) {

        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql = "DELETE FROM users WHERE user_name = ? ";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());


            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
