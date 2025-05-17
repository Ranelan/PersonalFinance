package za.ac.cput.factory;

import za.ac.cput.domain.Admin;
import za.ac.cput.util.Helper;

public class AdminFactory {
    public static Admin createAdmin(String adminCode, String userName, String email, String password) {
        if(Helper.isNullOrEmpty(adminCode) ||
                Helper.isNullOrEmpty(userName) ||
                Helper.isNullOrEmpty(email) ||
                Helper.isNullOrEmpty(password)){
            return null;
        }

        return new Admin.AdminBuilder()
                .setAdminCode(adminCode)
                .setUserName(userName)
                .setEmail(email)
                .setPassword(password)
                .build();
    }
}
