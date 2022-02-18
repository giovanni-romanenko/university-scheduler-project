package cz.cvut.fit.tjv.sem_work.api.exception;

public class SingleRelationIsNotSetException extends RuntimeException {
    public SingleRelationIsNotSetException() {
        super("Single relation is not set");
    }
}
