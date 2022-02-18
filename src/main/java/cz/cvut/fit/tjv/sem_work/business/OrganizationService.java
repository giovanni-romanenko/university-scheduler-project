package cz.cvut.fit.tjv.sem_work.business;

import cz.cvut.fit.tjv.sem_work.dao.OrganizationJpaRepository;
import cz.cvut.fit.tjv.sem_work.domain.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationService extends AbstractCrudService<Long, Organization, OrganizationJpaRepository>{
    public OrganizationService(OrganizationJpaRepository organizationJpaRepository) {
        super(organizationJpaRepository);
    }

    @Override
    public boolean exists(Organization entity) {
        return repository.existsById(entity.getId());
    }

    @Override
    protected void checkMandatoryParametersToCreate(Organization entity) {
        if (entity.getName() == null)
            throw new MandatoryValueMissingException(entity);
    }

    @Override
    protected void checkUniqueConstraints(Organization entity) {
    }
}
