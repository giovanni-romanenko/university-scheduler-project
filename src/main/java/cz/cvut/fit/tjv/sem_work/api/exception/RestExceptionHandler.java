package cz.cvut.fit.tjv.sem_work.api.exception;

import cz.cvut.fit.tjv.sem_work.business.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // Handles case, when new entity violates unique constraints.
    @ExceptionHandler(EntityViolatesUCException.class)
    protected ResponseEntity<Object> handleUCConflict(Exception e, WebRequest r) {
        String bodyOfResponse = "Entity violates unique constraints";
        return handleExceptionInternal(e, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, r);
    }

    // Handles case, when request body does not contain one of mandatory values.
    @ExceptionHandler(MandatoryValueMissingException.class)
    protected ResponseEntity<Object> handleMandatoryValueMissing(MandatoryValueMissingException e, WebRequest r) {
        String bodyOfResponse = "Request is missing mandatory parameter";
        return handleExceptionInternal(e, bodyOfResponse, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, r);
    }

    // Handles case, when requested entity was not found.
    @ExceptionHandler(NoEntityFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(Exception e, WebRequest r) {
        String bodyOfResponse = "Entity not found";
        return handleExceptionInternal(e, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, r);
    }

    // Handles case, when entity, which existence was assumed, does not exist.
    @ExceptionHandler(EntityDoesNotExistException.class)
    protected ResponseEntity<Object> handleEntityDoesNotExist(Exception e, WebRequest r) {
        String bodyOfResponse = "Entity does not exist";
        return handleExceptionInternal(e, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, r);
    }

    // Handles case, when relation, which existence was assumed, does not exist.
    @ExceptionHandler(RelationDoesNotExistException.class)
    protected ResponseEntity<Object> handleRelationDoesNotExist(Exception e, WebRequest r) {
        String bodyOfResponse = "Relation does not exist";
        return handleExceptionInternal(e, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, r);
    }

    // Handles case, when adding relation results in breaking top limit of existing relations for an entity.
    @ExceptionHandler(RelationOverflowException.class)
    protected ResponseEntity<Object> handleRelationOverflow(Exception e, WebRequest r) {
        String bodyOfResponse = "Relation breaks top limit of existing relations for an entity";
        return handleExceptionInternal(e, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, r);
    }

    // Handles case, when relation of 1:n type was not found.
    @ExceptionHandler(SingleRelationIsNotSetException.class)
    protected ResponseEntity<Object> handleSingleRelationIsNotSet(Exception e, WebRequest r) {
        String bodyOfResponse = "Relation of 1:n type not found";
        return handleExceptionInternal(e, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, r);
    }

}
