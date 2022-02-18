package cz.cvut.fit.tjv.sem_work.business;

public class EntityDoesNotExistException extends RuntimeException {
    public EntityDoesNotExistException() {
        super("Entity does not exist");
    }
}
