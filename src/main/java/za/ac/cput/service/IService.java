package za.ac.cput.service;

public interface IService <T, Id> {
    T create(T t);

    T read(Id id);

    T update(T t);

    void delete(Id id);
}
