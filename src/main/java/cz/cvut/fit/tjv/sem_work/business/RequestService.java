package cz.cvut.fit.tjv.sem_work.business;

import cz.cvut.fit.tjv.sem_work.dao.RequestJpaRepository;
import cz.cvut.fit.tjv.sem_work.domain.Organization;
import cz.cvut.fit.tjv.sem_work.domain.Request;
import cz.cvut.fit.tjv.sem_work.domain.RequestState;
import cz.cvut.fit.tjv.sem_work.domain.RichMan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class RequestService extends AbstractCrudService<Long, Request, RequestJpaRepository>{
    private final RichManService richManService;
    private final RequestStateService requestStateService;
    private final OrganizationService organizationService;

    public RequestService(RequestJpaRepository requestJpaRepository, RichManService richManService,
                          RequestStateService requestStateService, OrganizationService organizationService) {
        super(requestJpaRepository);
        this.richManService = richManService;
        this.requestStateService = requestStateService;
        this.organizationService = organizationService;
    }

    @Override
    public boolean exists(Request entity) {
        return repository.existsById(entity.getId());
    }

    @Override
    protected void checkMandatoryParametersToCreate(Request entity) {
        if (entity.getDeadline() == null || entity.getDeadlineFee() == null)
            throw new MandatoryValueMissingException(entity);
    }

    @Override
    protected void checkUniqueConstraints(Request entity) {
    }

    public Optional<RichMan> readRichManGivenRequest(Long requestId) {
        Request request = readById(requestId).orElseThrow(EntityDoesNotExistException::new);
        return Optional.ofNullable(request.getRequestedRichMan());
    }

    @Transactional
    public void updateRequestIsDemandingRichManRelation(Long requestId, Long richManId) {
        Request request = readById(requestId).orElseThrow(EntityDoesNotExistException::new);
        RichMan richMan = richManService.readById(richManId).orElseThrow(EntityDoesNotExistException::new);
        RichMan existingRichMan = request.getRequestedRichMan();
        if (existingRichMan == null)
            request.setRequestedRichMan(richMan);
        else if (!existingRichMan.equals(richMan))
            throw new RelationOverflowException();
    }

    @Transactional
    public void deleteRequestIsDemandingRichManRelation(Long requestId, Long richManId) {
        Request request = readById(requestId).orElseThrow(EntityDoesNotExistException::new);
        RichMan richMan = richManService.readById(richManId).orElseThrow(EntityDoesNotExistException::new);
        if (request.getRequestedRichMan() == null || !request.getRequestedRichMan().equals(richMan))
            throw new RelationDoesNotExistException();
        request.setRequestedRichMan(null);
    }

    public Optional<RequestState> readStateOfRequest(Long requestId) {
        Request request = readById(requestId).orElseThrow(EntityDoesNotExistException::new);
        return Optional.ofNullable(request.getStateOfRequest());
    }

    @Transactional
    public void updateRequestIsInRequestStateRelation(Long requestId, Long requestStateId) {
        Request request = readById(requestId).orElseThrow(EntityDoesNotExistException::new);
        RequestState requestState = requestStateService.readById(requestStateId).orElseThrow(EntityDoesNotExistException::new);
        RequestState existingRequestState = request.getStateOfRequest();
        if (existingRequestState == null)
            request.setStateOfRequest(requestState);
        else if (!existingRequestState.equals(requestState))
            throw new RelationOverflowException();
    }

    @Transactional
    public void deleteRequestIsInRequestStateRelation(Long requestId, Long requestStateId) {
        Request request = readById(requestId).orElseThrow(EntityDoesNotExistException::new);
        RequestState requestState = requestStateService.readById(requestStateId).orElseThrow(EntityDoesNotExistException::new);
        if (request.getStateOfRequest() == null || !request.getStateOfRequest().equals(requestState))
            throw new RelationDoesNotExistException();
        request.setStateOfRequest(null);
    }

    public Optional<Organization> readOrganizationWhichCreatedRequest(Long requestId) {
        Request request = readById(requestId).orElseThrow(EntityDoesNotExistException::new);
        return Optional.ofNullable(request.getOrganizationCreator());
    }

    @Transactional
    public void updateRequestWasCreatedByOrganizationRelation(Long requestId, Long organizationId) {
        Request request = readById(requestId).orElseThrow(EntityDoesNotExistException::new);
        Organization organization = organizationService.readById(organizationId).orElseThrow(EntityDoesNotExistException::new);
        Organization existingOrganization = request.getOrganizationCreator();
        if (existingOrganization == null)
            request.setOrganizationCreator(organization);
        else if (!existingOrganization.equals(organization))
            throw new RelationOverflowException();
    }

    @Transactional
    public void deleteRequestWasCreatedByOrganizationRelation(Long requestId, Long organizationId) {
        Request request = readById(requestId).orElseThrow(EntityDoesNotExistException::new);
        Organization organization = organizationService.readById(organizationId).orElseThrow(EntityDoesNotExistException::new);
        if (request.getOrganizationCreator() == null || !request.getOrganizationCreator().equals(organization))
            throw new RelationDoesNotExistException();
        request.setOrganizationCreator(null);
    }
}
