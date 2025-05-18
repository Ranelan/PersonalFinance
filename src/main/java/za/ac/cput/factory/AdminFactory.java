/*
 * AdminFactory.java
 * Goal Factory Class
 * Author: Scelo Kevin Nyandeni(23018695)
 * Date: 18 May 2025*/

package za.ac.cput.factory;

import za.ac.cput.domain.Admin;
import za.ac.cput.util.Helper;

public class AdminFactory {
    public static Admin createAdmin(Long userID, String userName, String email, String password, String adminCode) {
        if (Helper.isNullOrEmpty(userName) || Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(password) || Helper.isNullOrEmpty(adminCode)) {
            return null;
        }
        return new Admin.AdminBuilder()
                .setUserID(userID)
                .setUserName(userName)
                .setEmail(email)
                .setPassword(password)
                .setAdminCode(adminCode)
                .build();
    }
}
