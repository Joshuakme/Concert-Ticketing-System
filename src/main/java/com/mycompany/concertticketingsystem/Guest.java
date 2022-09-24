package com.mycompany.concertticketingsystem;

/**
 *
 * @author kohmi
 */
public class Guest {
    
    // Constructor
    public Guest() {
    }

    // Methods
    public boolean registerAccount(String username, String password, String accStatus) {
        if(username.trim().equals("") || password.trim().equals("") || accStatus.trim().equals("")) {
            return false;
        } else {
            AccountStatus accountStatus;
            if(accStatus.toUpperCase().equals(AccountStatus.ACTIVE.toString()))
                accountStatus = AccountStatus.ACTIVE;
            else if(accStatus.toUpperCase().equals(AccountStatus.BLACKLISTED.toString()))
                accountStatus = AccountStatus.BLACKLISTED;
            else if(accStatus.toUpperCase().equals(AccountStatus.BLOCKED.toString()))
                accountStatus = AccountStatus.BLOCKED;
            else if(accStatus.toUpperCase().equals(AccountStatus.CLOSED.toString()))
                accountStatus = AccountStatus.CLOSED;
            else
                accountStatus = AccountStatus.CANCELED;
            
            Account newAccount = new Account(username, password, accountStatus);
            return true;
        }
    }
}
