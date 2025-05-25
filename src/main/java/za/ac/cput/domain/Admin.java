/*
 * Admin.java
 * Goal POJO Class
 * Author: Scelo Kevin Nyandeni(23018695)
 * Date: 11 May 2025*/

package za.ac.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Admin extends User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String adminCode;

    public Admin() {
    }
    public Admin(AdminBuilder adminBuilder) {
        super(new UserBuilder()
                .setUserID(adminBuilder.userID)
                .setUserName(adminBuilder.userName)
                .setEmail(adminBuilder.email)
                .setPassword(adminBuilder.password));;
        this.adminCode = adminBuilder.adminCode;
    }

    public String getAdminCode() {
        return adminCode;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminCode='" + adminCode + '\'' +
                ", userID=" + getUserID() +
                ", userName='" + getUserName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }


    public static class AdminBuilder {
        public Long userID;
        private String userName;
        private String email;
        private String password;
        private String adminCode;

        public AdminBuilder() {
        }

        public AdminBuilder setUserID(Long userID) {
            this.userID = userID;
            return this;
        }

        public AdminBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public AdminBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AdminBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public AdminBuilder setAdminCode(String adminCode) {
            this.adminCode = adminCode;
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }

    }
}
