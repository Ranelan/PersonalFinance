/*
* RegularUser.java
* Goal POJO Class
* Author: Scelo Kevin Nyandeni(23018695)
* Date: 11 May 2025*/

package za.ac.cput.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("REGULAR-USER")
public class RegularUser extends User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String membershipID;
    @OneToMany(mappedBy = "regularUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "regularUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecurringTransaction> recurringTransactions;

    @OneToMany(mappedBy = "regularUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Budget> budgets;

    @OneToMany(mappedBy = "regularUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Goal> goals;

    public RegularUser() {
    }

    public RegularUser(Long userID, String userName, String email, String password, String membershipID) {
        super(userID, userName, email, password);
        this.membershipID = membershipID;
    }

    public RegularUser(RegularUserBuilder regularUserBuilder) {
        super(new UserBuilder()
                .setUserID(regularUserBuilder.userID)
                .setUserName(regularUserBuilder.userName)
                .setEmail(regularUserBuilder.email)
                .setPassword(regularUserBuilder.password));
        this.membershipID = regularUserBuilder.membershipID;
    }
    public String getMembershipID() {
        return membershipID;
    }
    @Override
    public String toString() {
        return "RegularUser{" +
                "membershipID='" + membershipID + '\'' +
                ", userID=" + getUserID() +
                ", userName='" + getUserName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
    public static class RegularUserBuilder {
        private Long userID;
        private String userName;
        private String email;
        private String password;

        public Long getUserID() {
            return userID;
        }

        public String getUserName() {
            return userName;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getMembershipID() {
            return membershipID;
        }

        private String membershipID;

        public RegularUserBuilder() {
        }

        public RegularUserBuilder setUserID(Long userID) {
            this.userID = userID;
            return this;
        }

        public RegularUserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public RegularUserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public RegularUserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public RegularUserBuilder setMembershipID(String membershipID) {
            this.membershipID = membershipID;
            return this;
        }

        public RegularUserBuilder copy(RegularUser regularUser) {
            this.membershipID = regularUser.membershipID;
            this.email = regularUser.email;
            this.password = regularUser.password;
            this.userName = regularUser.userName;
            this.userID = regularUser.userID;
            return this;

        }

        public RegularUser build() {
            return new RegularUser(this);
        }
    }
}
