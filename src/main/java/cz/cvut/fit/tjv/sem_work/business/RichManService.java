package cz.cvut.fit.tjv.sem_work.business;

import cz.cvut.fit.tjv.sem_work.dao.RichManJpaRepository;
import cz.cvut.fit.tjv.sem_work.domain.RichMan;
import org.springframework.stereotype.Component;

@Component
public class RichManService extends AbstractCrudService<Long, RichMan, RichManJpaRepository>{
    public RichManService(RichManJpaRepository richManJpaRepository) {
        super(richManJpaRepository);
    }

    @Override
    public boolean exists(RichMan entity) {
        return repository.existsById(entity.getId());
    }

    @Override
    protected void checkMandatoryParametersToCreate(RichMan entity) {
        if (entity.getFirstName() == null || entity.getSecondName() == null)
            throw new MandatoryValueMissingException(entity);
    }

    @Override
    protected void checkUniqueConstraints(RichMan entity) {
    }
}
