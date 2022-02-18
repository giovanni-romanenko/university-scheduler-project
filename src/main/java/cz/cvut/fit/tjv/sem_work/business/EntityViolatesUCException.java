package cz.cvut.fit.tjv.sem_work.business;

public class EntityViolatesUCException extends RuntimeException {
    public <E> EntityViolatesUCException(E entity) {
        super("The next entity violates unique constraints: " + entity);
    }
}
