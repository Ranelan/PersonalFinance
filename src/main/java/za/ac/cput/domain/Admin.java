/*
 * Admin.java
 * Goal POJO Class
 * Author: Scelo Kevin Nyandeni(23018695)
 * Date: 11 May 2025*/

package za.ac.cput.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Admin extends User {

    private String adminCode;

    @ElementCollection(targetClass = Permission.class)
    @CollectionTable(name = "admin_permissions", joinColumns = @JoinColumn(name = "admin_id"))
    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    private List<Permission> permissions;

    public Admin() {
    }

    public Admin(Long userId, String userName, String email, String password,String adminCode, List<Permission> permissions ) {
        super(userId, userName, email, password);
        this.adminCode = adminCode;
        this.permissions = permissions;
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


    public List<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminCode='" + adminCode + '\'' +
                ", userID=" + getUserID() +
                ", userName='" + getUserName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", permissions=" + permissions +
                '}';
    }


    public static class AdminBuilder {
        public Long userID;
        private String userName;
        private String email;
        private String password;
        private String adminCode;
        private List<Permission> permissions;


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

        public AdminBuilder setPermissions(List<Permission> permissions) {
            this.permissions = permissions;
            return this;
        }

        public AdminBuilder copy(Admin admin){
            this.adminCode = admin.adminCode;
            this.permissions = admin.permissions;
            this.email = admin.email;
            this.password = admin.password;
            this.userID = admin.userID;
            this.userName = admin.userName;
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }

    }
}
