/*
 *RegularUserFactory.java
 * Goal Factory Class
 * Author: Scelo Kevin Nyandeni(23018695)
 * Date: 18 May 2025*/
package za.ac.cput.factory;

import za.ac.cput.domain.RegularUser;
import za.ac.cput.util.Helper;

import java.util.UUID;

public class RegularUserFactory {
    public static RegularUser createRegularUser( String userName, String email, String password) {

        if (Helper.isNullOrEmpty(userName) ||
                Helper.isNullOrEmpty(email) ||
                Helper.isNullOrEmpty(password) ) {
            return null;
        }

        String membershipID = UUID.randomUUID().toString(); // I updated it to generate a unique membership ID

        return new RegularUser.RegularUserBuilder()
                .setUserName(userName)
                .setEmail(email)
                .setPassword(password)
                .setMembershipID(membershipID)
                .build();
    }
}
