package za.ac.cput.service;

import za.ac.cput.domain.Admin;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.RegularUser;

import java.util.List;
import java.util.Map;


public interface IAdminService extends IService<Admin, Long> {
    Admin read(Long id);
    List<Admin> findByUserName(String userName);
    List<Admin> findByEmail(String email);
    List<Admin> findAll();

    Admin logIn(String email, String password);

    //Regular USer
    List<RegularUser> viewAllRegularUsers();
    void deleteRegularUserById(Long id);
    void updateRegularUserById(Long id, RegularUser regularUser);

    //manage categories
    Category createCategory(String name, String type);
    Category updateCategory(Long id, String name, String type);
    Category deleteCategory(Long id);
    List viewAllCategories();

    //VIEW ANONYMIZED ANALYTICS
    Map<String, Object> viewAnonymizedAnalytics();

    Map<String, Object> viewAnonymizedAnalyticsByCategory(String category);

    Map<String, Object> viewAnonymizedAnalyticsByDateRange(String startDate, String endDate);

    Map<String, Object> viewAnonymizedAnalyticsByTransactionType(String transactionType);

}
