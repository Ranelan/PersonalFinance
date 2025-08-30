/* GoalRepository.java
     IService Interface
     Author: Ranelani Engel(221813853
     Date: 25 May 2025 */

package za.ac.cput.service;

public interface IService <T, Id> {
    T create(T t);

    T read(Id id);

    T update(T t);

    void delete(Id id);
}
