package cz.cvut.fit.tjv.sem_work.business;

public class MandatoryValueMissingException extends RuntimeException {
    public <E> MandatoryValueMissingException(E entity) {
        super("The next entity is missing some mandatory parameters: " + entity);
    }
}
