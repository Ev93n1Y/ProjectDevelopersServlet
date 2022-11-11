package service.converter;

public interface Convertable<E, T> {
    E from(T entity);
    T to(E entity);
}
