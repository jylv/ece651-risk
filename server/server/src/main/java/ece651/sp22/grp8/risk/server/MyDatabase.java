package ece651.sp22.grp8.risk.server;

import ece651.sp22.grp8.risk.GamePrompt;

import java.sql.*;
import java.util.HashMap;

public class MyDatabase {
    static long userID = 0;
    HashMap<String, Long> usernameTable = new HashMap<>(); //username, userid
    HashMap<Long, String> passwordTable = new HashMap<>(); //userid, password

    public MyDatabase(){
    }

    /**
     * Check whether given username and password exist
     * @param username
     * @param password
     * @return
     */
    public String queryUserExist(String username, String password) {
        if(usernameTable.containsKey(username)){
            long id = usernameTable.get(username);
            if(passwordTable.get(id).equals(password)){
                return GamePrompt.OK;
            }else{
                return GamePrompt.BADLOGIN;
            }
        }else{
            return GamePrompt.BADLOGIN;
        }
    }

    /**
     * This method insert a new user into the database
     * @param username username
     * @param password user password
     * @return whether it executes successfully
     */
    public String insertNewUser(String username, String password) {
        if(username.equals("") || password.equals("")){
            return GamePrompt.BADREGISTER;
        } else if(usernameTable.containsKey(username)){
            return GamePrompt.REGISTED;
        }
        usernameTable.put(username,userID);
        passwordTable.put(userID++,password);
        return GamePrompt.OK;
    }



}