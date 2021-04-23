package com.sam.service;

import com.sam.model.User;

import java.util.List;

public interface UsersService {
    int login(String email, String password, String userType, String rememberMe);


    String hashPassword(String password);
    User getUserByID(int id);
    List<User> geAllUsers();
    int updateUser(User user);
    int updateUserPassword(User user);

    int createNewUser(User user);
    boolean checkIfUserExist(User user);

}
