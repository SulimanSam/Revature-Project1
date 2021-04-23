package com.sam.dao;

import com.sam.model.User;
import java.util.List;

public interface UsersDAO {

    //CRUD METHODS ONLY

    //CREATE
    int insertUser(User user);

    //READ All
    List<User> selectAllUsers();

    //Read By id
    User selectUserById(int id);
    //Read By name
    User selectUserByNameOrEmail(String name);
    boolean checkIfUserExist(User user);

    //UPDATE Name
    void updateUser(User user);
    void updateUserPassword(User user);

    //DELETE
    void deleteUser(User user);


}
