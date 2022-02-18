package cz.cvut.fit.tjv.sem_work.business;

import cz.cvut.fit.tjv.sem_work.dao.ClerkTimeSlotJpaRepository;
import cz.cvut.fit.tjv.sem_work.domain.Clerk;
import cz.cvut.fit.tjv.sem_work.domain.ClerkTimeSlot;
import cz.cvut.fit.tjv.sem_work.domain.Request;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class ClerkTimeSlotService extends AbstractCrudService<Long, ClerkTimeSlot, ClerkTimeSlotJpaRepository>{
    private final ClerkService clerkService;
    private final RequestService requestService;

    public ClerkTimeSlotService(ClerkTimeSlotJpaRepository clerkTimeSlotJpaRepository, ClerkService clerkService,
                                RequestService requestService) {
        super(clerkTimeSlotJpaRepository);
        this.clerkService = clerkService;
        this.requestService = requestService;
    }

    @Override
    public boolean exists(ClerkTimeSlot entity) {
        return repository.existsById(entity.getId());
    }

    @Override
    protected void checkMandatoryParametersToCreate(ClerkTimeSlot entity) {
        if (entity.getStartTime() == null || entity.getEndTime() == null)
            throw new MandatoryValueMissingException(entity);
    }

    @Override
    protected void checkUniqueConstraints(ClerkTimeSlot entity) {
    }

    public Optional<Clerk> readClerkWhichHasClerkTimeSlot(Long clerkTimeSlotId) {
        ClerkTimeSlot clerkTimeSlot = readById(clerkTimeSlotId).orElseThrow(EntityDoesNotExistException::new);
        return Optional.ofNullable(clerkTimeSlot.getClerk());
    }

    @Transactional
    public void updateClerkTimeSlotBelongsToClerkRelation(Long clerkTimeSlotId, Long clerkId) {
        ClerkTimeSlot clerkTimeSlot = readById(clerkTimeSlotId).orElseThrow(EntityDoesNotExistException::new);
        Clerk clerk = clerkService.readById(clerkId).orElseThrow(EntityDoesNotExistException::new);
        Clerk existingClerk = clerkTimeSlot.getClerk();
        if (existingClerk == null)
            clerkTimeSlot.setClerk(clerk);
        else if (!existingClerk.equals(clerk))
            throw new RelationOverflowException();
    }

    @Transactional
    public void deleteClerkTimeSlotBelongsToClerkRelation(Long clerkTimeSlotId, Long clerkId) {
        ClerkTimeSlot clerkTimeSlot = readById(clerkTimeSlotId).orElseThrow(EntityDoesNotExistException::new);
        Clerk clerk = clerkService.readById(clerkId).orElseThrow(EntityDoesNotExistException::new);
        if (clerkTimeSlot.getClerk() == null || !clerkTimeSlot.getClerk().equals(clerk))
            throw new RelationDoesNotExistException();
        clerkTimeSlot.setClerk(null);
    }

    public Optional<Request> readRequestThatHasClerkTimeSlotAssigned(Long clerkTimeSlotId) {
        ClerkTimeSlot clerkTimeSlot = readById(clerkTimeSlotId).orElseThrow(EntityDoesNotExistException::new);
        return Optional.ofNullable(clerkTimeSlot.getRequest());
    }

    @Transactional
    public void updateClerkTimeSlotIsAssignedToRequestRelation(Long clerkTimeSlotId, Long requestId) {
        ClerkTimeSlot clerkTimeSlot = readById(clerkTimeSlotId).orElseThrow(EntityDoesNotExistException::new);
        Request request = requestService.readById(requestId).orElseThrow(EntityDoesNotExistException::new);
        if (clerkTimeSlot.getRequest() == null && request.getAppointedTimeSlot() == null)
            clerkTimeSlot.setRequest(request);
        else if (!clerkTimeSlot.getRequest().equals(request))
            throw new RelationOverflowException();
    }

    @Transactional
    public void deleteClerkTimeSlotIsAssignedToRequestRelation(Long clerkTimeSlotId, Long requestId) {
        ClerkTimeSlot clerkTimeSlot = readById(clerkTimeSlotId).orElseThrow(EntityDoesNotExistException::new);
        Request request = requestService.readById(requestId).orElseThrow(EntityDoesNotExistException::new);
        if (clerkTimeSlot.getRequest() == null || !clerkTimeSlot.getRequest().equals(request))
            throw new RelationDoesNotExistException();
        clerkTimeSlot.setRequest(null);
    }

}
