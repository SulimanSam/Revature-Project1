package com.sam.dao;

import com.sam.model.AddReimbursement;
import com.sam.model.Reimbursement;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;

import java.util.List;

import static com.sam.ConnectionHelper.ConnectionInfo.*;

public class ReimbursementDaoImpl implements ReimbursementDAO {

    @Override
    public int insertReimbursement(Reimbursement reimbursement) {
        int insertedID=0;
        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            //reimb_id serial PRIMARY KEY
            //	, reimb_amount INTEGER NOT NULL
            //	, reimb_submitted TIMESTAMP NOT NULL DEFAULT CURRENT_DATE
            //	, reimb_resolved TIMESTAMP
            //	, reimb_description VARCHAR(250)
            //	, author_id INTEGER NOT NULL
            //	, resolver_id INTEGER
            //	, status_id INTEGER
            //	, type_id INTEGER
            String sql = "INSERT INTO reimbursements VALUES(default,?,default,null,?," +
                    "(SELECT user_id FROM users WHERE user_name =?)," +
                    "null," +
                    "default," +
                    "(SELECT type_id FROM reimbursement_type WHERE reimb_type = ?))";


            PreparedStatement ps = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, reimbursement.getAmount());
            ps.setString(2, reimbursement.getDescription());
            ps.setString(3, reimbursement.getAuthor());
            ps.setString(4, reimbursement.getType());

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
    public void insertImages(AddReimbursement reimbursement,int reimbursementID) {

        for (String s:reimbursement.getImages()) {
            try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

                String sql = "INSERT INTO images VALUES(default,?,?)";


                PreparedStatement ps = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setDouble(1, reimbursementID);
                ps.setBytes(2, s.getBytes());


                ps.executeUpdate();



            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Reimbursement> selectAllReimbursement() {
        List<Reimbursement> reimbursements = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql = "SELECT r.*,u.user_name AS authorName ,u2.user_name AS resolverName,rs.reimb_status AS status, rt.reimb_type AS typp FROM reimbursements AS r\n" +
                    "                    INNER JOIN users AS u ON r.author_id = u.user_id\n" +
                    "                    LEFT JOIN users AS u2 ON r.resolver_id = u2.user_id\n" +
                    "                    INNER JOIN reimbursement_status AS rs ON rs.status_id = r.status_id\n" +
                    "                    INNER JOIN reimbursement_type AS rt ON rt.type_id = r.type_id ORDER BY r.reimb_id";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                reimbursements.add(
                        new Reimbursement(rs.getInt(1)
                                ,rs.getDouble(2)
                                ,rs.getDate(3)
                                ,rs.getDate(4)
                                ,rs.getString(5)
                                ,rs.getString("authorName")
                                ,rs.getString("resolverName")
                                ,rs.getString("status")
                                ,rs.getString("typp")
                        ));
            }


        }catch(SQLException e){
            e.printStackTrace();
        }

        return reimbursements;
    }

    @Override
    public Reimbursement selectReimbursementById(int id) {
        Reimbursement reimbursement=null;

        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql = "SELECT r.*,(u.user_name) AS authorName\n" +
                    "                    ,(u2.user_name) AS resolverName \n" +
                    "                    ,rs.reimb_status AS status, rt.reimb_type AS typp  FROM reimbursements r\n" +
                    "                    INNER JOIN users u ON r.author_id = u.user_id\n" +
                    "                    LEFT JOIN users u2 ON r.resolver_id = u2.user_id \n" +
                    "                    INNER JOIN reimbursement_status rs ON r.status_id = rs.status_id\n" +
                    "                    INNER JOIN reimbursement_type rt ON r.type_id = rt.type_id where r.reimb_id=?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                reimbursement= new Reimbursement(rs.getInt(1)
                        ,rs.getDouble(2)
                        ,rs.getDate(3)
                        ,rs.getDate(4)
                        ,rs.getString(5)
                        ,rs.getString("authorName")
                        ,rs.getString("resolverName")
                        ,rs.getString("status")
                        ,rs.getString("typp"));

                //System.out.println(reimbursement);
            }


        }catch(SQLException e){
            e.printStackTrace();
        }

        return reimbursement;
    }

    @Override
    public List<Reimbursement> selectReimbursementByAuthorID(int authorID) {
        List<Reimbursement> reimbursements = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql = "SELECT r.*,u.user_name AS authorName ,u2.user_name AS resolverName,rs.reimb_status AS status, rt.reimb_type AS typp FROM reimbursements AS r\n" +
                    "                    INNER JOIN users AS u ON r.author_id = u.user_id\n" +
                    "                    LEFT JOIN users AS u2 ON r.resolver_id = u2.user_id\n" +
                    "                    INNER JOIN reimbursement_status AS rs ON rs.status_id = r.status_id\n" +
                    "                    INNER JOIN reimbursement_type AS rt ON rt.type_id = r.type_id WHERE r.author_id = ? ORDER BY r.reimb_id";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,authorID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                reimbursements.add(
                        new Reimbursement(rs.getInt(1)
                                ,rs.getDouble(2)
                                ,rs.getDate(3)
                                ,rs.getDate(4)
                                ,rs.getString(5)
                                ,rs.getString("authorName")
                                ,rs.getString("resolverName")
                                ,rs.getString("status")
                                ,rs.getString("typp")
                        ));
            }


        }catch(SQLException e){
            e.printStackTrace();
        }

        return reimbursements;
    }

    @Override
    public List<String> selectImagesByID(int id) {
        List<String> list= new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String sql = "select * from images where reimb_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                list.add(new String(rs.getBytes(3), StandardCharsets.UTF_8));
            }

        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String selectUserEmailByReimbursementID(int id) {
        List<String> list= new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String sql = "SELECT user_email FROM users WHERE user_id = (SELECT author_id FROM reimbursements r WHERE reimb_id = ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getString(1);
            }

        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public void updateReimbursementStatus(int reimbID, int resolverID, int status) {
        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql = "UPDATE reimbursements SET reimb_resolved=current_date" +
                    ", resolver_id= ?, status_id= ? WHERE reimb_id = ? ";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, resolverID);
            ps.setInt(2, status);
            ps.setInt(3, reimbID);

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateReimbursement(Reimbursement reimbursement) {
        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql = "UPDATE reimbursements SET reimb_amount=?" +
                    ", reimb_resolved= ?" +
                    ", reimb_description= ?" +
                    ", status_id = (SELECT status_id FROM reimbursement_status WHERE reimb_status = ?)" +
                    ", type_id = (SELECT type_id FROM reimbursement_type WHERE reimb_type = ?)" +
                    ", resolver_id= (SELECT user_id FROM users WHERE user_name =? )" +
                    " WHERE reimb_id = ? ";




            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, reimbursement.getAmount());
            ps.setDate(2, reimbursement.getResolveDate());
            ps.setString(3, reimbursement.getDescription());
            ps.setString(4, reimbursement.getStatus());
            ps.setString(5, reimbursement.getType());
            ps.setString(6, reimbursement.getResolver());
            ps.setInt(7, reimbursement.getReimbursementID());


            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReimbursement(Reimbursement reimbursement) {
        try(Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){

            String sql = "DELETE FROM reimbursements WHERE reimb_id = ? ";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reimbursement.getReimbursementID());

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public int countOfType(String status , int authorID) {
        try (Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String sql = "select count(status_id) AS count from reimbursements " +
                    "where status_id = (select status_id from reimbursement_status where reimb_status = ?) " +
                    "AND author_id =?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,status);
            ps.setInt(2,authorID);
            ResultSet rs = ps.executeQuery();



            if(rs.next()) {
                return rs.getInt("count");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double sumOfApprovedAmount(int authorID) {
        try (Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String sql = "select sum(reimb_amount) AS sum from reimbursements \n" +
                    "                    where status_id = (select status_id from reimbursement_status where reimb_status = 'approved')\n" +
                    "                    AND author_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,authorID);
            ResultSet rs = ps.executeQuery();



            if(rs.next()) {
                return rs.getDouble("sum");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public int countOfType(String status) {
        try (Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String sql = "select count(status_id) AS count from reimbursements " +
                    "where status_id = (select status_id from reimbursement_status where reimb_status = ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,status);

            ResultSet rs = ps.executeQuery();



            if(rs.next()) {
                return rs.getInt("count");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double sumOfApprovedAmount() {
        try (Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            String sql = "select sum(reimb_amount) AS sum from reimbursements \n" +
                    "                    where status_id = (select status_id from reimbursement_status where reimb_status = 'approved')";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();



            if(rs.next()) {
                return rs.getDouble("sum");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }
}
