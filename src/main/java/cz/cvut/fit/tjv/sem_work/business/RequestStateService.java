package cz.cvut.fit.tjv.sem_work.business;

import cz.cvut.fit.tjv.sem_work.dao.RequestStateJpaRepository;
import cz.cvut.fit.tjv.sem_work.domain.RequestState;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RequestStateService extends AbstractCrudService<Long, RequestState, RequestStateJpaRepository>{
    public RequestStateService(RequestStateJpaRepository requestStateJpaRepository) {
        super(requestStateJpaRepository);
    }

    @Override
    public boolean exists(RequestState entity) {
        return repository.existsById(entity.getId());
    }

    @Override
    protected void checkMandatoryParametersToCreate(RequestState entity) {
        if (entity.getName() == null)
            throw new MandatoryValueMissingException(entity);
    }

    @Override
    protected void checkUniqueConstraints(RequestState entity) {
        Optional<RequestState> sameNameRequestState = repository.findByName(entity.getName());
        if (!(sameNameRequestState.isEmpty() || sameNameRequestState.orElseThrow().getId().equals(entity.getId())))
            throw new EntityViolatesUCException(entity);
    }
}
