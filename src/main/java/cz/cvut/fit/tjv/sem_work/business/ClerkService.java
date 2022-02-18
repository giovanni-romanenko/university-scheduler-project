package cz.cvut.fit.tjv.sem_work.business;

import cz.cvut.fit.tjv.sem_work.dao.ClerkJpaRepository;
import cz.cvut.fit.tjv.sem_work.domain.Clerk;
import cz.cvut.fit.tjv.sem_work.domain.ClerkTimeSlot;
import cz.cvut.fit.tjv.sem_work.domain.Organization;
import cz.cvut.fit.tjv.sem_work.domain.Request;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Component
public class ClerkService extends AbstractCrudService<Long, Clerk, ClerkJpaRepository>{
    private final OrganizationService organizationService;
    private final RequestService requestService;

    public ClerkService(ClerkJpaRepository clerkJpaRepository, OrganizationService organizationService,
                        RequestService requestService) {
        super(clerkJpaRepository);
        this.organizationService = organizationService;
        this.requestService = requestService;
    }

    @Override
    public boolean exists(Clerk entity) {
        return repository.existsById(entity.getId());
    }

    @Override
    protected void checkMandatoryParametersToCreate(Clerk entity) {
        if (entity.getFirstName() == null || entity.getSecondName() == null)
            throw new MandatoryValueMissingException(entity);
    }

    @Override
    protected void checkUniqueConstraints(Clerk entity) {
    }

    public Optional<Organization> readOrganizationWhereClerkWorks(Long clerkId) {
        Clerk clerk = readById(clerkId).orElseThrow(EntityDoesNotExistException::new);
        return Optional.ofNullable(clerk.getJobOrganization());
    }

    @Transactional
    public void updateClerkWorksInOrganizationRelation(Long clerkId, Long organizationId) {
        Clerk clerk = readById(clerkId).orElseThrow(EntityDoesNotExistException::new);
        Organization organization = organizationService.readById(organizationId).orElseThrow(EntityDoesNotExistException::new);
        Organization existingOrganization = clerk.getJobOrganization();
        if (existingOrganization == null)
            clerk.setJobOrganization(organization);
        else if (!existingOrganization.equals(organization))
            throw new RelationOverflowException();
    }

    @Transactional
    public void deleteClerkWorksInOrganizationRelation(Long clerkId, Long organizationId) {
        Clerk clerk = readById(clerkId).orElseThrow(EntityDoesNotExistException::new);
        Organization organization = organizationService.readById(organizationId).orElseThrow(EntityDoesNotExistException::new);
        if (clerk.getJobOrganization() == null || !clerk.getJobOrganization().equals(organization))
            throw new RelationDoesNotExistException();
        clerk.setJobOrganization(null);
    }

    public Collection<Request> readAllRequestsSolvableByClerk(Long clerkId) {
        Clerk clerk = readById(clerkId).orElseThrow(EntityDoesNotExistException::new);
        return clerk.getSolvableRequests();
    }

    @Transactional
    public void updateClerkCanSolveRequestRelation(Long clerkId, Long requestId) {
        Clerk clerk = readById(clerkId).orElseThrow(EntityDoesNotExistException::new);
        Request request = requestService.readById(requestId).orElseThrow(EntityDoesNotExistException::new);
        clerk.getSolvableRequests().add(request);
    }

    @Transactional
    public void deleteClerkCanSolveRequestRelation(Long clerkId, Long requestId) {
        Clerk clerk = readById(clerkId).orElseThrow(EntityDoesNotExistException::new);
        Request request = requestService.readById(requestId).orElseThrow(EntityDoesNotExistException::new);
        boolean requestWasRemoved = clerk.getSolvableRequests().remove(request);
        if (!requestWasRemoved)
            throw new RelationDoesNotExistException();
    }

    public Collection<ClerkTimeSlot> readAllUnassignedTimeSlotsOfClerk(Long clerkId) {
        return repository.findAllUnassignedTimeSlotsOfClerk(clerkId);
    }
}
