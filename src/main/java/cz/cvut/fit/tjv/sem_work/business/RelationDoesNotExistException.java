package cz.cvut.fit.tjv.sem_work.business;

public class RelationDoesNotExistException extends RuntimeException {
    public RelationDoesNotExistException() {
        super("Relation does not exist");
    }
}
