package za.ac.cput.factory;

import za.ac.cput.domain.*;
import za.ac.cput.util.Helper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserFactory {

    public static User createUser(String userName, String email, String password, Role role, List<Transaction> transactions, List<RecurringTransaction> recurringTransactions, List<Budget> budgets, List<Goal> goals){
       if(Helper.isNullOrEmpty(userName)||
        !Helper.isValidEmail(email)||
        !Helper.isValidPassword(password)||
               role == null
       ){
           return null;
       }

       // Enforce permissions logic: ADMIN gets all permissions, others get none
       Set<Permission> effectivePermissions;
       if ("ADMIN".equals(role.getName())) {
           effectivePermissions = new HashSet<>(Arrays.asList(Permission.values()));
       } else {
           effectivePermissions = new HashSet<>();
       }

         return new User.UserBuilder()
                 .setUserName(userName)
                 .setEmail(email)
                 .setPassword(password)
                 .setRole(role)
                 .setPermissions(effectivePermissions)
                 .setTransactions(transactions)
                    .setRecurringTransactions(recurringTransactions)
                    .setBudgets(budgets)
                    .setGoals(goals)
                    .build();
    }
}
