/*
 * UserFactory.java
 * Goal Factory Class
 * Author: Scelo Kevin Nyandeni(23018695)
 * Date: 18 May 2025*/

package za.ac.cput.factory;

import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

public class UserFactory {
    public static User createUser(Long userID, String userName, String email, String password){
        if(Helper.isNullOrEmpty(userName) || Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(password)){
            return null;
        }
        return new User.UserBuilder()
                .setUserID(userID)
                .setUserName(userName)
                .setEmail(email)
                .setPassword(password)
                .build();
    }
}
