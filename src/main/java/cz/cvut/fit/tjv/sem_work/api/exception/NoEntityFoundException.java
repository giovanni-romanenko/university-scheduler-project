package cz.cvut.fit.tjv.sem_work.api.exception;

public class NoEntityFoundException extends RuntimeException {

    public NoEntityFoundException() {
        super("No entity found");
    }
}
