/*
 * User.java
 * Goal POJO Class
 * Author: Scelo Kevin Nyandeni(23018695)
 * Date: 11 May 2025*/
package za.ac.cput.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String userName;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    //Permissions (only for Admins, can be empty for regular users)
    @ElementCollection(targetClass = Permission.class)
    @CollectionTable(name = "user_permissions", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecurringTransaction> recurringTransactions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Budget> budgets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Goal> goals;


    public User(){
    }

    public User(Long userID, String userName, String email, String password, Role role, Set<Permission> permissions, List<Transaction> transactions, List<RecurringTransaction> recurringTransactions, List<Budget> budgets, List<Goal> goals) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.permissions = permissions;
        this.transactions = transactions;
        this.recurringTransactions = recurringTransactions;
        this.budgets = budgets;
        this.goals = goals;
    }

    public User(UserBuilder userBuilder){
        this.userID = userBuilder.userID;
        this.userName = userBuilder.userName;
        this.email = userBuilder.email;
        this.password = userBuilder.password;
        this.role = userBuilder.role;
        this.permissions = userBuilder.permissions;
        this.transactions = userBuilder.transactions;
        this.recurringTransactions = userBuilder.recurringTransactions;
        this.budgets = userBuilder.budgets;
        this.goals = userBuilder.goals;

    }

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

    public Role getRole() {
        return role;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<RecurringTransaction> getRecurringTransactions() {
        return recurringTransactions;
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", userName=" + userName + ", email=" + email +
                ", role=" + role + ", permissions=" + permissions + '}';
    }


    public static class UserBuilder {
        private Long userID;
        private String userName;
        private String email;
        private String password;
        private Role role;
        private Set<Permission> permissions;
        private List<Transaction> transactions;
        private List<RecurringTransaction> recurringTransactions;
        private List<Budget> budgets;
        private List<Goal> goals;

        public UserBuilder setUserID(Long userID) {
            this.userID = userID;
            return this;
        }

        public UserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setRole(Role role) {
            this.role = role;
            return this;
        }

        public UserBuilder setPermissions(Set<Permission> permissions) {
            this.permissions = permissions;
            return this;
        }

        public UserBuilder setTransactions(List<Transaction> transactions) {
            this.transactions = transactions;
            return this;
        }

        public UserBuilder setRecurringTransactions(List<RecurringTransaction> recurringTransactions) {
            this.recurringTransactions = recurringTransactions;
            return this;
        }

        public UserBuilder setBudgets(List<Budget> budgets) {
            this.budgets = budgets;
            return this;
        }

        public UserBuilder setGoals(List<Goal> goals) {
            this.goals = goals;
            return this;
        }

        public UserBuilder copy(User user) {
            this.userID = user.userID;
            this.userName = user.userName;
            this.email = user.email;
            this.password = user.password;
            this.role = user.role;
            this.permissions = user.permissions;
            this.transactions = user.transactions;
            this.recurringTransactions = user.recurringTransactions;
            this.budgets = user.budgets;
            this.goals = user.goals;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}