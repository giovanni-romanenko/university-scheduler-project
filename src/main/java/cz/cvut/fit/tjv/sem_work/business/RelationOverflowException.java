package cz.cvut.fit.tjv.sem_work.business;

public class RelationOverflowException extends RuntimeException {
    public RelationOverflowException() {
        super("Relation overflow");
    }
}