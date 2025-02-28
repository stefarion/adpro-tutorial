package id.ac.ui.cs.advprog.eshop.repository;

public interface RepositoryWrite<T> {
    public T create(T object);

    public T update(String id, T object);

    public T delete(String id);
}
