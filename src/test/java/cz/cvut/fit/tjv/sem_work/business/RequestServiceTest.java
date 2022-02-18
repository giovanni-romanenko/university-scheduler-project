package cz.cvut.fit.tjv.sem_work.business;

import cz.cvut.fit.tjv.sem_work.dao.RequestJpaRepository;
import cz.cvut.fit.tjv.sem_work.domain.Organization;
import cz.cvut.fit.tjv.sem_work.domain.Request;
import cz.cvut.fit.tjv.sem_work.domain.RequestState;
import cz.cvut.fit.tjv.sem_work.domain.RichMan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RequestServiceTest {
    @Autowired
    private RequestService requestService;

    @MockBean
    private RequestJpaRepository repository;

    @MockBean
    private RichManService richManService;

    @MockBean
    private RequestStateService requestStateService;

    @MockBean
    private OrganizationService organizationService;

    Request request1;
    Request request2;
    Request request3;
    Request request4;
    Request nonExistingRequest;
    Request requestMissingDeadline;
    Request requestMissingDeadlineFee;
    Request updatedRequest;
    Request updatedRequestMissingDeadline;

    RichMan richMan1;
    RichMan richMan23;
    RichMan nonExistingRichMan;

    RequestState requestState12;
    RequestState requestState4;
    RequestState nonExistingRequestState;

    Organization organization13;
    Organization organization2;
    Organization nonExistingOrganization;

    @BeforeEach
    void setUp() {
        request1 = new Request(1L, LocalDateTime.now(), 10L);
        request2 = new Request(2L, LocalDateTime.now(), 10L);
        request3 = new Request(3L, LocalDateTime.now(), 10L);
        request4 = new Request(4L, LocalDateTime.now(), 10L);
        nonExistingRequest = new Request(5L, LocalDateTime.now(), 10L);
        requestMissingDeadline = new Request(6L, null, 10L);
        requestMissingDeadlineFee = new Request(7L, LocalDateTime.now(), null);
        updatedRequest = new Request(1L, LocalDateTime.now(), 40L);
        updatedRequestMissingDeadline = new Request(1L, null, 10L);

        richMan1 = new RichMan(1L, "TestFN", "TestSN");
        richMan23 = new RichMan(2L, "TestFN", "TestSN");
        nonExistingRichMan = new RichMan(3L, "TestFN", "TestSN");

        requestState12 = new RequestState(1L, "TestName1");
        requestState4 = new RequestState(2L, "TestName2");
        nonExistingRequestState = new RequestState(3L, "TestName3");

        organization13 = new Organization(1L, "TestName");
        organization2 = new Organization(2L, "TestName");
        nonExistingOrganization = new Organization(3L, "TestName");

        request1.setRequestedRichMan(richMan1);
        request2.setRequestedRichMan(richMan23);
        request3.setRequestedRichMan(richMan23);

        request1.setStateOfRequest(requestState12);
        request2.setStateOfRequest(requestState12);
        request4.setStateOfRequest(requestState4);

        request1.setOrganizationCreator(organization13);
        request3.setOrganizationCreator(organization13);
        request2.setOrganizationCreator(organization2);

        Mockito.when(repository.existsById(request1.getId())).thenReturn(true);
        Mockito.when(repository.existsById(request2.getId())).thenReturn(true);
        Mockito.when(repository.existsById(request3.getId())).thenReturn(true);
        Mockito.when(repository.existsById(request4.getId())).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingRequest.getId())).thenReturn(false);
        Mockito.when(repository.findById(request1.getId())).thenReturn(Optional.of(request1));
        Mockito.when(repository.findById(request2.getId())).thenReturn(Optional.of(request2));
        Mockito.when(repository.findById(request3.getId())).thenReturn(Optional.of(request3));
        Mockito.when(repository.findById(request4.getId())).thenReturn(Optional.of(request4));
        Mockito.when(repository.findById(nonExistingRequest.getId())).thenReturn(Optional.empty());
        Mockito.when(repository.findAll()).thenReturn(List.of(
                request1, request2, request3, request4));

        Mockito.when(richManService.readById(richMan1.getId())).thenReturn(Optional.of(richMan1));
        Mockito.when(richManService.readById(richMan23.getId())).thenReturn(Optional.of(richMan23));
        Mockito.when(richManService.readById(nonExistingRichMan.getId())).thenReturn(Optional.empty());

        Mockito.when(requestStateService.readById(requestState12.getId())).thenReturn(Optional.of(requestState12));
        Mockito.when(requestStateService.readById(requestState4.getId())).thenReturn(Optional.of(requestState4));
        Mockito.when(requestStateService.readById(nonExistingRequestState.getId())).thenReturn(Optional.empty());

        Mockito.when(organizationService.readById(organization13.getId())).thenReturn(Optional.of(organization13));
        Mockito.when(organizationService.readById(organization2.getId())).thenReturn(Optional.of(organization2));
        Mockito.when(organizationService.readById(nonExistingOrganization.getId())).thenReturn(Optional.empty());
    }

    @Test
    void create() {
        requestService.create(nonExistingRequest);
        Mockito.verify(repository, Mockito.times(1)).save(nonExistingRequest);
        // test checkMandatoryParametersToCreate
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                requestService.create(requestMissingDeadline));
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                requestService.create(requestMissingDeadlineFee));
    }

    @Test
    void readById() {
        Assertions.assertEquals(Optional.of(request1), requestService.readById(request1.getId()));
        Assertions.assertEquals(Optional.of(request2), requestService.readById(request2.getId()));
        Assertions.assertEquals(Optional.of(request3), requestService.readById(request3.getId()));
        Assertions.assertEquals(Optional.of(request4), requestService.readById(request4.getId()));
        Assertions.assertEquals(Optional.empty(), requestService.readById(nonExistingRequest.getId()));
    }

    @Test
    void readAll() {
        Assertions.assertEquals(List.of(
                request1, request2, request3, request4), requestService.readAll());
    }

    @Test
    void update() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.update(nonExistingRequest));
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                requestService.update(updatedRequestMissingDeadline));
        requestService.update(updatedRequest);
        Mockito.verify(repository, Mockito.times(1)).save(updatedRequest);
    }

    @Test
    void deleteById() {
        requestService.deleteById(request2.getId());
        Mockito.verify(repository, Mockito.times(1)).deleteById(request2.getId());
    }

    @Test
    void exists() {
        Assertions.assertTrue(requestService.exists(request1));
        Assertions.assertTrue(requestService.exists(request2));
        Assertions.assertTrue(requestService.exists(request3));
        Assertions.assertTrue(requestService.exists(request4));
        Assertions.assertFalse(requestService.exists(nonExistingRequest));
    }

    @Test
    void readRichManGivenRequest() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.readRichManGivenRequest(nonExistingRequest.getId()));
        Assertions.assertEquals(Optional.empty(),
                requestService.readRichManGivenRequest(request4.getId()));
        Assertions.assertEquals(Optional.of(richMan1),
                requestService.readRichManGivenRequest(request1.getId()));
        Assertions.assertEquals(Optional.of(richMan23),
                requestService.readRichManGivenRequest(request2.getId()));
        Assertions.assertEquals(Optional.of(richMan23),
                requestService.readRichManGivenRequest(request3.getId()));
    }

    @Test
    void updateRequestIsDemandingRichManRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.updateRequestIsDemandingRichManRelation(
                        request1.getId(), nonExistingRichMan.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.updateRequestIsDemandingRichManRelation(
                        nonExistingRequest.getId(), richMan23.getId()));
        Assertions.assertThrows(RelationOverflowException.class, () ->
                requestService.updateRequestIsDemandingRichManRelation(
                        request2.getId(), richMan1.getId()));
        Assertions.assertDoesNotThrow(() ->
                requestService.updateRequestIsDemandingRichManRelation(
                        request2.getId(), richMan23.getId()));
        requestService.updateRequestIsDemandingRichManRelation(
                request4.getId(), richMan1.getId());
        Assertions.assertEquals(richMan1, request4.getRequestedRichMan());
    }

    @Test
    void deleteRequestIsDemandingRichManRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.deleteRequestIsDemandingRichManRelation(
                        request1.getId(), nonExistingRichMan.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.deleteRequestIsDemandingRichManRelation(
                        nonExistingRequest.getId(), richMan23.getId()));
        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
                requestService.deleteRequestIsDemandingRichManRelation(
                        request1.getId(), richMan23.getId()));
        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
                requestService.deleteRequestIsDemandingRichManRelation(
                        request4.getId(), richMan1.getId()));
        Assertions.assertDoesNotThrow(() ->
                requestService.deleteRequestIsDemandingRichManRelation(
                        request1.getId(), richMan1.getId()));
        requestService.deleteRequestIsDemandingRichManRelation(
                request2.getId(), richMan23.getId());
        Assertions.assertNull(request2.getRequestedRichMan());
    }

    @Test
    void readStateOfRequest() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.readStateOfRequest(nonExistingRequest.getId()));
        Assertions.assertEquals(Optional.empty(),
                requestService.readStateOfRequest(request3.getId()));
        Assertions.assertEquals(Optional.of(requestState12),
                requestService.readStateOfRequest(request1.getId()));
        Assertions.assertEquals(Optional.of(requestState12),
                requestService.readStateOfRequest(request2.getId()));
        Assertions.assertEquals(Optional.of(requestState4),
                requestService.readStateOfRequest(request4.getId()));
    }

    @Test
    void updateRequestIsInRequestStateRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.updateRequestIsInRequestStateRelation(
                        request1.getId(), nonExistingRequestState.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.updateRequestIsInRequestStateRelation(
                        nonExistingRequest.getId(), requestState12.getId()));
        Assertions.assertThrows(RelationOverflowException.class, () ->
                requestService.updateRequestIsInRequestStateRelation(
                        request2.getId(), requestState4.getId()));
        Assertions.assertDoesNotThrow(() ->
                requestService.updateRequestIsInRequestStateRelation(
                        request2.getId(), requestState12.getId()));
        requestService.updateRequestIsInRequestStateRelation(
                request3.getId(), requestState12.getId());
        Assertions.assertEquals(requestState12, request3.getStateOfRequest());
    }

    @Test
    void deleteRequestIsInRequestStateRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.deleteRequestIsInRequestStateRelation(
                        request1.getId(), nonExistingRequestState.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.deleteRequestIsInRequestStateRelation(
                        nonExistingRequest.getId(), requestState12.getId()));
        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
                requestService.deleteRequestIsInRequestStateRelation(
                        request1.getId(), requestState4.getId()));
        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
                requestService.deleteRequestIsInRequestStateRelation(
                        request3.getId(), requestState12.getId()));
        Assertions.assertDoesNotThrow(() ->
                requestService.deleteRequestIsInRequestStateRelation(
                        request4.getId(), requestState4.getId()));
        requestService.deleteRequestIsInRequestStateRelation(
                request2.getId(), requestState12.getId());
        Assertions.assertNull(request2.getStateOfRequest());
    }

    @Test
    void readOrganizationWhichCreatedRequest() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.readOrganizationWhichCreatedRequest(nonExistingRequest.getId()));
        Assertions.assertEquals(Optional.empty(),
                requestService.readOrganizationWhichCreatedRequest(request4.getId()));
        Assertions.assertEquals(Optional.of(organization13),
                requestService.readOrganizationWhichCreatedRequest(request1.getId()));
        Assertions.assertEquals(Optional.of(organization13),
                requestService.readOrganizationWhichCreatedRequest(request3.getId()));
        Assertions.assertEquals(Optional.of(organization2),
                requestService.readOrganizationWhichCreatedRequest(request2.getId()));
    }

    @Test
    void updateRequestWasCreatedByOrganizationRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.updateRequestWasCreatedByOrganizationRelation(
                        request1.getId(), nonExistingOrganization.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.updateRequestWasCreatedByOrganizationRelation(
                        nonExistingRequest.getId(), organization13.getId()));
        Assertions.assertThrows(RelationOverflowException.class, () ->
                requestService.updateRequestWasCreatedByOrganizationRelation(
                        request2.getId(), organization13.getId()));
        Assertions.assertDoesNotThrow(() ->
                requestService.updateRequestWasCreatedByOrganizationRelation(
                        request3.getId(), organization13.getId()));
        requestService.updateRequestWasCreatedByOrganizationRelation(
                request4.getId(), organization2.getId());
        Assertions.assertEquals(organization2, request4.getOrganizationCreator());
    }

    @Test
    void deleteRequestWasCreatedByOrganizationRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.deleteRequestWasCreatedByOrganizationRelation(
                        request1.getId(), nonExistingOrganization.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestService.deleteRequestWasCreatedByOrganizationRelation(
                        nonExistingRequest.getId(), organization2.getId()));
        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
                requestService.deleteRequestWasCreatedByOrganizationRelation(
                        request1.getId(), organization2.getId()));
        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
                requestService.deleteRequestWasCreatedByOrganizationRelation(
                        request4.getId(), organization13.getId()));
        Assertions.assertDoesNotThrow(() ->
                requestService.deleteRequestWasCreatedByOrganizationRelation(
                        request2.getId(), organization2.getId()));
        requestService.deleteRequestWasCreatedByOrganizationRelation(
                request3.getId(), organization13.getId());
        Assertions.assertNull(request3.getOrganizationCreator());
    }
}