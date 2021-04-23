package com.sam.service;

import com.sam.dao.UsersDAO;
import com.sam.dao.UsersDaoImpl;
import com.sam.model.User;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class UsersServiceImpl implements UsersService {
    UsersDAO usersDAO=new UsersDaoImpl();
    static public User currentUser = null;

    /**
     * This method handle Restaurants and Health Inspectors login.<br>
     * method parameters:
     * @param loginType This is the second parameter <h4>TYPE: charter</h4> <h4>DESCRIPTION:
     *                 'R' for restaurant account 'I' for health inspector account.</h4>
     * @return boolean returns true after checking username and password
     */

    @Override
    public int login(String email , String password, String loginType, String rememberMe) {
        String hashPassword = hashPassword(password);
        User loginUser = usersDAO.selectUserByNameOrEmail(email);
        int loginStatus=0;
        loop:
        if(loginUser != null) {
            if ((email.equalsIgnoreCase(loginUser.getUserName())|| email.equalsIgnoreCase(loginUser.getEmail())) & hashPassword.equals(loginUser.getPassword())) {
                if (loginUser.getRole().equals(loginType)) {
                    currentUser = new User(loginUser.getUserID()
                            , loginUser.getUserName()
                            ,""
                            , loginUser.getFirstName()
                            , loginUser.getLastName()
                            , loginUser.getEmail()
                            , loginUser.getDate()
                            , loginUser.getIsChange()
                            , loginUser.getRole());
                    loginStatus=1;
                    break loop;

                } else {
                    switch (loginUser.getRole()) {
                        case "employee":
                            loginStatus=2;
                            break loop;
                        case "manager":
                            currentUser = new User(loginUser.getUserID()
                                    , loginUser.getUserName()
                                    ,""
                                    , loginUser.getFirstName()
                                    , loginUser.getLastName()
                                    , loginUser.getEmail()
                                    , loginUser.getDate()
                                    , loginUser.getIsChange()
                                    , loginUser.getRole());
                            loginStatus=3;
                            break loop;

                    }

                }
            }
        }
        return loginStatus;
    }

    @Override
    public String hashPassword(String password) {

        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        System.out.println(generatedPassword);
        return generatedPassword;
    }

    @Override
    public User getUserByID(int id) {
        return usersDAO.selectUserById(id);
    }

    @Override
    public List<User> geAllUsers() {
        return usersDAO.selectAllUsers();
    }

    @Override
    public int updateUser(User user) {

        usersDAO.updateUser(user);
        return 1;
    }

    @Override
    public int updateUserPassword(User user) {

        String hashPass = hashPassword(user.getPassword());
        User u = usersDAO.selectUserById(user.getUserID());

        if(!hashPassword(user.getRole()).equals(u.getPassword())){
            return 2;
        }
        user.setPassword(hashPass);
        usersDAO.updateUserPassword(user);
        return 1;
    }

    /**
     * This method handle Restaurants and Health Inspectors registration.<br>
     * method parameters:
     * @param user This is the second parameter <h4>TYPE: User</h4> <h4>DESCRIPTION:
     *                 'R' for restaurant account 'I' for health inspector account.</h4>
     * @return void
     *
     */
    @Override
    public int createNewUser(User user) {
        String hashPass = hashPassword(user.getPassword());
        user.setPassword(hashPass);
        return usersDAO.insertUser(user);
    }

    /**
     * check if the user name is used before in uses file.<br>
     * method parameters:
     * @param user This is the first parameter<h4>TYPE:String</h4> <h4>DESCRIPTION: "username" .</h4>
     * @return boolean
     */
    @Override
    public boolean checkIfUserExist(User user) {
        return usersDAO.checkIfUserExist(user);
    }
}
