package com.mycompany.concertticketingsystem;

/**
 *
 * @author kohmi
 */
public class Guest {
    // Methods
    public boolean registerAccount(String username, String password, String accStatus) {
        if(username == "" || password == "" || accStatus == "") {
            return false;
        } else {
            Account newAccount = new Account(username, password, accStatus);
            return true;
        }
    }
}
