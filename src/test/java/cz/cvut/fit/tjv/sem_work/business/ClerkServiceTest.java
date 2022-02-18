package cz.cvut.fit.tjv.sem_work.business;

import cz.cvut.fit.tjv.sem_work.dao.ClerkJpaRepository;
import cz.cvut.fit.tjv.sem_work.domain.Clerk;
import cz.cvut.fit.tjv.sem_work.domain.ClerkTimeSlot;
import cz.cvut.fit.tjv.sem_work.domain.Organization;
import cz.cvut.fit.tjv.sem_work.domain.Request;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClerkServiceTest {
    @Autowired
    private ClerkService clerkService;

    @MockBean
    private ClerkJpaRepository repository;

    @MockBean
    private OrganizationService organizationService;

    @MockBean
    private RequestService requestService;

    Clerk clerk1;
    Clerk clerk2;
    Clerk clerk3;
    Clerk clerk4;
    Clerk nonExistingClerk;
    Clerk clerkMissingFirstName;
    Clerk clerkMissingSecondName;
    Clerk updatedClerk;
    Clerk updatedClerkMissingFirstName;

    Organization organization23;
    Organization organization4;
    Organization nonExistingOrg;

    Request request12;
    Request request23;
    Request request31;
    Request nonExistingRequest;

    ClerkTimeSlot clerkTimeSlot11;
    ClerkTimeSlot clerkTimeSlot12;
    ClerkTimeSlot clerkTimeSlot31;

    @BeforeEach
    void setUp() {
        clerk1 = new Clerk(1L, "TestFN", "TestSN", "", 0L);
        clerk2 = new Clerk(2L, "TestFN", "TestSN", "", 0L);
        clerk3 = new Clerk(3L, "TestFN", "TestSN", "", 0L);
        clerk4 = new Clerk(4L, "TestFN", "TestSN", "", 0L);
        nonExistingClerk = new Clerk(5L, "TestFN", "TestSN", "", 0L);
        clerkMissingFirstName = new Clerk(6L, null, "TestSN", null, 10L);
        clerkMissingSecondName = new Clerk(7L, "TestFN", null, null, null);
        updatedClerk = new Clerk(2L, "ChangedFN", "ChangedSN", null, null);
        updatedClerkMissingFirstName = new Clerk(2L, null, "ChangedSN", null, null);

        organization23 = new Organization(1L, "23org");
        organization4 = new Organization(2L, "4org");
        nonExistingOrg = new Organization(3L, "none");

        request12 = new Request(1L, LocalDateTime.now(), 10L);
        request23 = new Request(2L, LocalDateTime.now(), 20L);
        request31 = new Request(3L, LocalDateTime.now(), 30L);
        nonExistingRequest = new Request(4L, LocalDateTime.now(), 40L);

        clerkTimeSlot11 = new ClerkTimeSlot(1L, LocalDateTime.now(), LocalDateTime.now());
        clerkTimeSlot12 = new ClerkTimeSlot(2L, LocalDateTime.now(), LocalDateTime.now());
        clerkTimeSlot31 = new ClerkTimeSlot(3L, LocalDateTime.now(), LocalDateTime.now());

        clerk2.setJobOrganization(organization23);
        clerk3.setJobOrganization(organization23);
        clerk4.setJobOrganization(organization4);

        clerk1.setSolvableRequests(Set.of(request12, request31));
        clerk2.setSolvableRequests(Set.of(request12, request23));
        clerk3.setSolvableRequests(Set.of(request23, request31));

        clerk1.setTimeSlotsForRequests(Set.of(clerkTimeSlot11, clerkTimeSlot12));
        clerk3.setTimeSlotsForRequests(Set.of(clerkTimeSlot31));

        Mockito.when(repository.existsById(clerk1.getId())).thenReturn(true);
        Mockito.when(repository.existsById(clerk2.getId())).thenReturn(true);
        Mockito.when(repository.existsById(clerk3.getId())).thenReturn(true);
        Mockito.when(repository.existsById(clerk4.getId())).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingClerk.getId())).thenReturn(false);
        Mockito.when(repository.findById(clerk1.getId())).thenReturn(Optional.of(clerk1));
        Mockito.when(repository.findById(clerk2.getId())).thenReturn(Optional.of(clerk2));
        Mockito.when(repository.findById(clerk3.getId())).thenReturn(Optional.of(clerk3));
        Mockito.when(repository.findById(clerk4.getId())).thenReturn(Optional.of(clerk4));
        Mockito.when(repository.findById(nonExistingClerk.getId())).thenReturn(Optional.empty());
        Mockito.when(repository.findAll()).thenReturn(List.of(clerk1, clerk2, clerk3, clerk4));
        Mockito.when(repository.findAllUnassignedTimeSlotsOfClerk(clerk1.getId()))
                .thenReturn(List.of(clerkTimeSlot11));
        Mockito.when(repository.findAllUnassignedTimeSlotsOfClerk(clerk2.getId()))
                .thenReturn(List.of());
        Mockito.when(repository.findAllUnassignedTimeSlotsOfClerk(clerk3.getId()))
                .thenReturn(List.of());
        Mockito.when(repository.findAllUnassignedTimeSlotsOfClerk(clerk4.getId()))
                .thenReturn(List.of());

        Mockito.when(organizationService.readById(organization23.getId())).thenReturn(Optional.of(organization23));
        Mockito.when(organizationService.readById(organization4.getId())).thenReturn(Optional.of(organization4));
        Mockito.when(organizationService.readById(nonExistingOrg.getId())).thenReturn(Optional.empty());

        Mockito.when(requestService.readById(request12.getId())).thenReturn(Optional.of(request12));
        Mockito.when(requestService.readById(request23.getId())).thenReturn(Optional.of(request23));
        Mockito.when(requestService.readById(request31.getId())).thenReturn(Optional.of(request31));
        Mockito.when(requestService.readById(nonExistingRequest.getId())).thenReturn(Optional.empty());
    }

    @Test
    void create() {
        clerkService.create(nonExistingClerk);
        Mockito.verify(repository, Mockito.times(1)).save(nonExistingClerk);
        // test checkMandatoryParametersToCreate
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                clerkService.create(clerkMissingFirstName));
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                clerkService.create(clerkMissingSecondName));
    }

    @Test
    void readById() {
        Assertions.assertEquals(Optional.of(clerk1), clerkService.readById(clerk1.getId()));
        Assertions.assertEquals(Optional.of(clerk2), clerkService.readById(clerk2.getId()));
        Assertions.assertEquals(Optional.of(clerk3), clerkService.readById(clerk3.getId()));
        Assertions.assertEquals(Optional.of(clerk4), clerkService.readById(clerk4.getId()));
        Assertions.assertEquals(Optional.empty(), clerkService.readById(nonExistingClerk.getId()));
    }

    @Test
    void readAll() {
        Assertions.assertEquals(List.of(clerk1, clerk2, clerk3, clerk4), clerkService.readAll());
    }

    @Test
    void update() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkService.update(nonExistingClerk));
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                clerkService.update(updatedClerkMissingFirstName));
        clerkService.update(updatedClerk);
        Mockito.verify(repository, Mockito.times(1)).save(updatedClerk);
    }

    @Test
    void deleteById() {
        clerkService.deleteById(clerk3.getId());
        Mockito.verify(repository, Mockito.times(1)).deleteById(clerk3.getId());
    }

    @Test
    void exists() {
        Assertions.assertTrue(clerkService.exists(clerk1));
        Assertions.assertTrue(clerkService.exists(clerk2));
        Assertions.assertTrue(clerkService.exists(clerk3));
        Assertions.assertTrue(clerkService.exists(clerk4));
        Assertions.assertFalse(clerkService.exists(nonExistingClerk));
    }

    @Test
    void readOrganizationWhereClerkWorks() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkService.readOrganizationWhereClerkWorks(nonExistingClerk.getId()));
        Assertions.assertEquals(Optional.empty(),
                clerkService.readOrganizationWhereClerkWorks(clerk1.getId()));
        Assertions.assertEquals(Optional.of(organization23),
                clerkService.readOrganizationWhereClerkWorks(clerk2.getId()));
        Assertions.assertEquals(Optional.of(organization23),
                clerkService.readOrganizationWhereClerkWorks(clerk3.getId()));
        Assertions.assertEquals(Optional.of(organization4),
                clerkService.readOrganizationWhereClerkWorks(clerk4.getId()));
    }

    @Test
    void updateClerkWorksInOrganizationRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkService.updateClerkWorksInOrganizationRelation(
                        clerk1.getId(), nonExistingOrg.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkService.updateClerkWorksInOrganizationRelation(
                        nonExistingClerk.getId(), organization23.getId()));
        Assertions.assertThrows(RelationOverflowException.class, () ->
                clerkService.updateClerkWorksInOrganizationRelation(
                        clerk2.getId(), organization4.getId()));
        Assertions.assertDoesNotThrow(() ->
                clerkService.updateClerkWorksInOrganizationRelation(
                        clerk2.getId(), organization23.getId()));
        clerkService.updateClerkWorksInOrganizationRelation(
                clerk1.getId(), organization4.getId());
        Assertions.assertEquals(organization4, clerk1.getJobOrganization());
    }

    @Test
    void deleteClerkWorksInOrganizationRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkService.deleteClerkWorksInOrganizationRelation(
                        clerk3.getId(), nonExistingOrg.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkService.deleteClerkWorksInOrganizationRelation(
                        nonExistingClerk.getId(), organization4.getId()));
        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
                clerkService.deleteClerkWorksInOrganizationRelation(
                        clerk2.getId(), organization4.getId()));
        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
                clerkService.deleteClerkWorksInOrganizationRelation(
                        clerk1.getId(), organization23.getId()));
        Assertions.assertDoesNotThrow(() ->
                clerkService.deleteClerkWorksInOrganizationRelation(
                        clerk2.getId(), organization23.getId()));
        clerkService.deleteClerkWorksInOrganizationRelation(
                clerk4.getId(), organization4.getId());
        Assertions.assertNull(clerk4.getJobOrganization());
    }

    @Test
    void readAllRequestsSolvableByClerk() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkService.readAllRequestsSolvableByClerk(nonExistingClerk.getId()));
        var solvableRequests1 = clerkService.readAllRequestsSolvableByClerk(
                clerk1.getId());
        Assertions.assertEquals(2, solvableRequests1.size());
        Assertions.assertTrue(solvableRequests1.contains(request12));
        Assertions.assertTrue(solvableRequests1.contains(request31));
        var solvableRequests2 = clerkService.readAllRequestsSolvableByClerk(
                clerk2.getId());
        Assertions.assertEquals(2, solvableRequests2.size());
        Assertions.assertTrue(solvableRequests2.contains(request12));
        Assertions.assertTrue(solvableRequests2.contains(request23));
        var solvableRequests3 = clerkService.readAllRequestsSolvableByClerk(
                clerk3.getId());
        Assertions.assertEquals(2, solvableRequests3.size());
        Assertions.assertTrue(solvableRequests3.contains(request31));
        Assertions.assertTrue(solvableRequests3.contains(request23));
        var solvableRequests4 = clerkService.readAllRequestsSolvableByClerk(
                clerk4.getId());
        Assertions.assertEquals(0, solvableRequests4.size());
    }

    @Test
    void updateClerkCanSolveRequestRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkService.updateClerkCanSolveRequestRelation(
                        clerk1.getId(), nonExistingRequest.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkService.updateClerkCanSolveRequestRelation(
                        nonExistingClerk.getId(), request12.getId()));
//        Assertions.assertDoesNotThrow(() ->
//                clerkService.updateClerkCanSolveRequestRelation(
//                        clerk2.getId(), request12.getId()));
//        clerkService.updateClerkCanSolveRequestRelation(
//                clerk1.getId(), request23.getId());
//        Assertions.assertEquals(3, clerk1.getSolvableRequests().size());
//        Assertions.assertTrue(clerk1.getSolvableRequests().contains(request23));
//        Assertions.assertTrue(clerk1.getSolvableRequests().contains(request12));
//        Assertions.assertTrue(clerk1.getSolvableRequests().contains(request31));
    }

    @Test
    void deleteClerkCanSolveRequestRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkService.deleteClerkCanSolveRequestRelation(
                        clerk3.getId(), nonExistingRequest.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkService.deleteClerkCanSolveRequestRelation(
                        nonExistingClerk.getId(), request23.getId()));
//        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
//                clerkService.deleteClerkCanSolveRequestRelation(
//                        clerk1.getId(), request23.getId()));
//        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
//                clerkService.deleteClerkCanSolveRequestRelation(
//                        clerk4.getId(), request31.getId()));
//        Assertions.assertDoesNotThrow(() ->
//                clerkService.deleteClerkCanSolveRequestRelation(
//                        clerk3.getId(), request31.getId()));
//        clerkService.deleteClerkCanSolveRequestRelation(
//                clerk2.getId(), request12.getId());
//        Assertions.assertEquals(1, clerk2.getSolvableRequests().size());
//        Assertions.assertTrue(clerk2.getSolvableRequests().contains(request23));
//        Assertions.assertEquals(2, clerk1.getSolvableRequests().size());
//        Assertions.assertTrue(clerk1.getSolvableRequests().contains(request12));
//        Assertions.assertTrue(clerk1.getSolvableRequests().contains(request31));
    }

    @Test
    void readAllUnassignedTimeSlotsOfClerk() {
        var unassignedTimeSlots1 = clerkService.readAllUnassignedTimeSlotsOfClerk(
                clerk1.getId());
        Assertions.assertEquals(1, unassignedTimeSlots1.size());
        Assertions.assertTrue(unassignedTimeSlots1.contains(clerkTimeSlot11));
        var unassignedTimeSlots2 = clerkService.readAllUnassignedTimeSlotsOfClerk(
                clerk2.getId());
        Assertions.assertEquals(0, unassignedTimeSlots2.size());
        var unassignedTimeSlots3 = clerkService.readAllUnassignedTimeSlotsOfClerk(
                clerk3.getId());
        Assertions.assertEquals(0, unassignedTimeSlots3.size());
        var unassignedTimeSlots4 = clerkService.readAllUnassignedTimeSlotsOfClerk(
                clerk4.getId());
        Assertions.assertEquals(0, unassignedTimeSlots4.size());
    }
}