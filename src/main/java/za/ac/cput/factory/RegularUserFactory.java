/*
 *RegularUserFactory.java
 * Goal Factory Class
 * Author: Scelo Kevin Nyandeni(23018695)
 * Date: 18 May 2025*/
package za.ac.cput.factory;

import za.ac.cput.domain.RegularUser;
import za.ac.cput.util.Helper;

public class RegularUserFactory {
    public static RegularUser createRegularUser(Long userID, String userName, String email, String password, String  membershipID) {

        if (Helper.isNullOrEmpty(userName) || Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(password) || Helper.isNullOrEmpty(membershipID)) {
            return null;
        }
        return new RegularUser.RegularUserBuilder()
                .setUserID(userID)
                .setUserName(userName)
                .setEmail(email)
                .setPassword(password)
                .setMembershipID(membershipID)
                .build();
    }
}
