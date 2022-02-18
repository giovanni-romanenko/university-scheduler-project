package cz.cvut.fit.tjv.sem_work.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

/**
 * Common superclass for business logic of all entities supporting operations Create, Read, Update, Delete.
 *
 * @param <K> Type of (primary) key.
 * @param <E> Type of entity
 */
public abstract class AbstractCrudService<K, E, R extends JpaRepository<E, K>> {
    /**
     * Reference to data (persistence) layer.
     */
    protected final R repository;

    protected AbstractCrudService(R repository) {
        this.repository = repository;
    }

    /**
     * Attempts to store a new entity. Throws exception if an entity with the same key is already stored.
     *
     * @param entity entity to be stored
     */
    @Transactional
    public void create(E entity) {
        createOrUpdateRoutine(entity);
    }

    public abstract boolean exists(E entity);

    protected void createOrUpdateRoutine(E entity) {
        checkMandatoryParametersToCreate(entity);
        checkUniqueConstraints(entity);
        repository.save(entity); //delegate call to data layer
    }

    protected abstract void checkMandatoryParametersToCreate(E entity);

    /**
     * Checks, if unique constraints are being violated. If so, throws exception.
     * If entity has non-null id (PK), then found entity with such id won't be considered a violation.
     *
     * @param entity entity used to check unique constraints violation
     * @throws EntityViolatesUCException if violation of UC was found
     */
    protected abstract void checkUniqueConstraints(E entity);

    public Optional<E> readById(K id) {
        return repository.findById(id);
    }

    public Collection<E> readAll() {
        return repository.findAll();
    }

    /**
     * Attempts to replace an already stored entity.
     *
     * @param entity the new state of the entity to be updated; the instance must contain a key value
     * @throws EntityDoesNotExistException if the entity cannot be found
     */
    @Transactional
    public void update(E entity) {
        if (exists(entity))
            createOrUpdateRoutine(entity);
        else
            throw new EntityDoesNotExistException();
    }

    @Transactional
    public void deleteById(K id) {
        repository.deleteById(id);
    }
}
