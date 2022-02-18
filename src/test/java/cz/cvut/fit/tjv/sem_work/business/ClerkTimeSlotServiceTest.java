package cz.cvut.fit.tjv.sem_work.business;

import cz.cvut.fit.tjv.sem_work.dao.ClerkTimeSlotJpaRepository;
import cz.cvut.fit.tjv.sem_work.domain.Clerk;
import cz.cvut.fit.tjv.sem_work.domain.ClerkTimeSlot;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClerkTimeSlotServiceTest {
    @Autowired
    private ClerkTimeSlotService clerkTimeSlotService;

    @MockBean
    private ClerkTimeSlotJpaRepository repository;

    @MockBean
    private ClerkService clerkService;

    @MockBean
    private RequestService requestService;

    ClerkTimeSlot clerkTimeSlot1;
    ClerkTimeSlot clerkTimeSlot2;
    ClerkTimeSlot clerkTimeSlot3;
    ClerkTimeSlot clerkTimeSlot4;
    ClerkTimeSlot nonExistingClerkTimeSlot;
    ClerkTimeSlot clerkTimeSlotMissingStartTime;
    ClerkTimeSlot clerkTimeSlotMissingEndTime;
    ClerkTimeSlot updatedClerkTimeSlot;
    ClerkTimeSlot updatedClerkTimeSlotMissingStartTime;

    Clerk clerk12;
    Clerk clerk3;
    Clerk nonExistingClerk;

    Request request1;
    Request request2;
    Request nonExistingRequest;
    Request unassignedRequest;

    @BeforeEach
    void setUp() {
        clerkTimeSlot1 = new ClerkTimeSlot(1L, LocalDateTime.now(), LocalDateTime.now());
        clerkTimeSlot2 = new ClerkTimeSlot(2L, LocalDateTime.now(), LocalDateTime.now());
        clerkTimeSlot3 = new ClerkTimeSlot(3L, LocalDateTime.now(), LocalDateTime.now());
        clerkTimeSlot4 = new ClerkTimeSlot(4L, LocalDateTime.now(), LocalDateTime.now());
        nonExistingClerkTimeSlot = new ClerkTimeSlot(5L, LocalDateTime.now(), LocalDateTime.now());
        clerkTimeSlotMissingStartTime = new ClerkTimeSlot(6L, null, LocalDateTime.now());
        clerkTimeSlotMissingEndTime = new ClerkTimeSlot(7L, LocalDateTime.now(), null);
        updatedClerkTimeSlot = new ClerkTimeSlot(3L, LocalDateTime.now(), LocalDateTime.now());
        updatedClerkTimeSlotMissingStartTime = new ClerkTimeSlot(3L, null, LocalDateTime.now());

        clerk12 = new Clerk(1L, "TestFN", "TestSN", null, null);
        clerk3 = new Clerk(2L, "TestFN", "TestSN", null, null);
        nonExistingClerk = new Clerk(3L, "TestFN", "TestSN", null, 0L);

        request1 = new Request(1L, LocalDateTime.now(), 10L);
        request2 = new Request(2L, LocalDateTime.now(), 20L);
        nonExistingRequest = new Request(3L, LocalDateTime.now(), 30L);
        unassignedRequest = new Request(4L, LocalDateTime.now(), 30L);

        clerkTimeSlot1.setClerk(clerk12);
        clerkTimeSlot2.setClerk(clerk12);
        clerkTimeSlot3.setClerk(clerk3);

        clerkTimeSlot1.setRequest(request1);
        clerkTimeSlot2.setRequest(request2);

        Mockito.when(repository.existsById(clerkTimeSlot1.getId())).thenReturn(true);
        Mockito.when(repository.existsById(clerkTimeSlot2.getId())).thenReturn(true);
        Mockito.when(repository.existsById(clerkTimeSlot3.getId())).thenReturn(true);
        Mockito.when(repository.existsById(clerkTimeSlot4.getId())).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingClerkTimeSlot.getId())).thenReturn(false);
        Mockito.when(repository.findById(clerkTimeSlot1.getId())).thenReturn(Optional.of(clerkTimeSlot1));
        Mockito.when(repository.findById(clerkTimeSlot2.getId())).thenReturn(Optional.of(clerkTimeSlot2));
        Mockito.when(repository.findById(clerkTimeSlot3.getId())).thenReturn(Optional.of(clerkTimeSlot3));
        Mockito.when(repository.findById(clerkTimeSlot4.getId())).thenReturn(Optional.of(clerkTimeSlot4));
        Mockito.when(repository.findById(nonExistingClerkTimeSlot.getId())).thenReturn(Optional.empty());
        Mockito.when(repository.findAll()).thenReturn(List.of(clerkTimeSlot1, clerkTimeSlot2,
                clerkTimeSlot3, clerkTimeSlot4));

        Mockito.when(clerkService.readById(clerk12.getId())).thenReturn(Optional.of(clerk12));
        Mockito.when(clerkService.readById(clerk3.getId())).thenReturn(Optional.of(clerk3));
        Mockito.when(clerkService.readById(nonExistingClerk.getId())).thenReturn(Optional.empty());

        Mockito.when(requestService.readById(request1.getId())).thenReturn(Optional.of(request1));
        Mockito.when(requestService.readById(request2.getId())).thenReturn(Optional.of(request2));
        Mockito.when(requestService.readById(nonExistingRequest.getId())).thenReturn(Optional.empty());
        Mockito.when(requestService.readById(unassignedRequest.getId())).thenReturn(Optional.of(unassignedRequest));
    }

    @Test
    void create() {
        clerkTimeSlotService.create(nonExistingClerkTimeSlot);
        Mockito.verify(repository, Mockito.times(1)).save(nonExistingClerkTimeSlot);
        // test checkMandatoryParametersToCreate
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                clerkTimeSlotService.create(clerkTimeSlotMissingStartTime));
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                clerkTimeSlotService.create(clerkTimeSlotMissingEndTime));
    }

    @Test
    void readById() {
        Assertions.assertEquals(Optional.of(clerkTimeSlot1), clerkTimeSlotService.readById(clerkTimeSlot1.getId()));
        Assertions.assertEquals(Optional.of(clerkTimeSlot2), clerkTimeSlotService.readById(clerkTimeSlot2.getId()));
        Assertions.assertEquals(Optional.of(clerkTimeSlot3), clerkTimeSlotService.readById(clerkTimeSlot3.getId()));
        Assertions.assertEquals(Optional.of(clerkTimeSlot4), clerkTimeSlotService.readById(clerkTimeSlot4.getId()));
        Assertions.assertEquals(Optional.empty(), clerkTimeSlotService.readById(nonExistingClerkTimeSlot.getId()));
    }

    @Test
    void readAll() {
        Assertions.assertEquals(List.of(clerkTimeSlot1, clerkTimeSlot2, clerkTimeSlot3, clerkTimeSlot4),
                clerkTimeSlotService.readAll());
    }

    @Test
    void update() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkTimeSlotService.update(nonExistingClerkTimeSlot));
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                clerkTimeSlotService.update(updatedClerkTimeSlotMissingStartTime));
        clerkTimeSlotService.update(updatedClerkTimeSlot);
        Mockito.verify(repository, Mockito.times(1)).save(updatedClerkTimeSlot);
    }

    @Test
    void deleteById() {
        clerkTimeSlotService.deleteById(clerkTimeSlot3.getId());
        Mockito.verify(repository, Mockito.times(1)).deleteById(clerkTimeSlot3.getId());
    }

    @Test
    void exists() {
        Assertions.assertTrue(clerkTimeSlotService.exists(clerkTimeSlot1));
        Assertions.assertTrue(clerkTimeSlotService.exists(clerkTimeSlot2));
        Assertions.assertTrue(clerkTimeSlotService.exists(clerkTimeSlot3));
        Assertions.assertTrue(clerkTimeSlotService.exists(clerkTimeSlot4));
        Assertions.assertFalse(clerkTimeSlotService.exists(nonExistingClerkTimeSlot));
    }

    @Test
    void readClerkWhichHasClerkTimeSlot() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkTimeSlotService.readClerkWhichHasClerkTimeSlot(
                        nonExistingClerkTimeSlot.getId()));
        Assertions.assertEquals(Optional.empty(),
                clerkTimeSlotService.readClerkWhichHasClerkTimeSlot(clerkTimeSlot4.getId()));
        Assertions.assertEquals(Optional.of(clerk12),
                clerkTimeSlotService.readClerkWhichHasClerkTimeSlot(clerkTimeSlot1.getId()));
        Assertions.assertEquals(Optional.of(clerk12),
                clerkTimeSlotService.readClerkWhichHasClerkTimeSlot(clerkTimeSlot2.getId()));
        Assertions.assertEquals(Optional.of(clerk3),
                clerkTimeSlotService.readClerkWhichHasClerkTimeSlot(clerkTimeSlot3.getId()));
    }

    @Test
    void updateClerkTimeSlotBelongsToClerkRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkTimeSlotService.updateClerkTimeSlotBelongsToClerkRelation(
                        clerkTimeSlot1.getId(), nonExistingClerk.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkTimeSlotService.updateClerkTimeSlotBelongsToClerkRelation(
                        nonExistingClerkTimeSlot.getId(), clerk12.getId()));
        Assertions.assertThrows(RelationOverflowException.class, () ->
                clerkTimeSlotService.updateClerkTimeSlotBelongsToClerkRelation(
                        clerkTimeSlot2.getId(), clerk3.getId()));
        Assertions.assertDoesNotThrow(() ->
                clerkTimeSlotService.updateClerkTimeSlotBelongsToClerkRelation(
                        clerkTimeSlot1.getId(), clerk12.getId()));
        clerkTimeSlotService.updateClerkTimeSlotBelongsToClerkRelation(
                clerkTimeSlot4.getId(), clerk12.getId());
        Assertions.assertEquals(clerk12, clerkTimeSlot4.getClerk());
    }

    @Test
    void deleteClerkTimeSlotBelongsToClerkRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkTimeSlotService.deleteClerkTimeSlotBelongsToClerkRelation(
                        clerkTimeSlot1.getId(), nonExistingClerk.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkTimeSlotService.deleteClerkTimeSlotBelongsToClerkRelation(
                        nonExistingClerkTimeSlot.getId(), clerk12.getId()));
        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
                clerkTimeSlotService.deleteClerkTimeSlotBelongsToClerkRelation(
                        clerkTimeSlot1.getId(), clerk3.getId()));
        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
                clerkTimeSlotService.deleteClerkTimeSlotBelongsToClerkRelation(
                        clerkTimeSlot4.getId(), clerk12.getId()));
        Assertions.assertDoesNotThrow(() ->
                clerkTimeSlotService.deleteClerkTimeSlotBelongsToClerkRelation(
                        clerkTimeSlot2.getId(), clerk12.getId()));
        clerkTimeSlotService.deleteClerkTimeSlotBelongsToClerkRelation(
                clerkTimeSlot3.getId(), clerk3.getId());
        Assertions.assertNull(clerkTimeSlot3.getClerk());
    }

    @Test
    void readRequestThatHasClerkTimeSlotAssigned() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkTimeSlotService.readRequestThatHasClerkTimeSlotAssigned(
                        nonExistingClerkTimeSlot.getId()));
        Assertions.assertEquals(Optional.empty(),
                clerkTimeSlotService.readRequestThatHasClerkTimeSlotAssigned(clerkTimeSlot4.getId()));
        Assertions.assertEquals(Optional.of(request1),
                clerkTimeSlotService.readRequestThatHasClerkTimeSlotAssigned(clerkTimeSlot1.getId()));
        Assertions.assertEquals(Optional.of(request2),
                clerkTimeSlotService.readRequestThatHasClerkTimeSlotAssigned(clerkTimeSlot2.getId()));
    }

    @Test
    void updateClerkTimeSlotIsAssignedToRequestRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkTimeSlotService.updateClerkTimeSlotIsAssignedToRequestRelation(
                        clerkTimeSlot1.getId(), nonExistingRequest.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkTimeSlotService.updateClerkTimeSlotIsAssignedToRequestRelation(
                        nonExistingClerkTimeSlot.getId(), request2.getId()));
//        Assertions.assertThrows(RelationOverflowException.class, () ->
//                clerkTimeSlotService.updateClerkTimeSlotIsAssignedToRequestRelation(
//                        clerkTimeSlot2.getId(), unassignedRequest.getId()));
//        Assertions.assertThrows(RelationOverflowException.class, () ->
//                clerkTimeSlotService.updateClerkTimeSlotIsAssignedToRequestRelation(
//                        clerkTimeSlot3.getId(), request1.getId()));
//        clerkTimeSlotService.updateClerkTimeSlotIsAssignedToRequestRelation(
//                clerkTimeSlot4.getId(), unassignedRequest.getId());
//        Assertions.assertEquals(unassignedRequest, clerkTimeSlot4.getRequest());
    }

    @Test
    void deleteClerkTimeSlotIsAssignedToRequestRelation() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkTimeSlotService.deleteClerkTimeSlotIsAssignedToRequestRelation(
                        clerkTimeSlot1.getId(), nonExistingRequest.getId()));
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                clerkTimeSlotService.deleteClerkTimeSlotIsAssignedToRequestRelation(
                        nonExistingClerkTimeSlot.getId(), request2.getId()));
//        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
//                clerkTimeSlotService.deleteClerkTimeSlotIsAssignedToRequestRelation(
//                        clerkTimeSlot1.getId(), request2.getId()));
//        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
//                clerkTimeSlotService.deleteClerkTimeSlotIsAssignedToRequestRelation(
//                        clerkTimeSlot2.getId(), unassignedRequest.getId()));
//        Assertions.assertThrows(RelationDoesNotExistException.class, () ->
//                clerkTimeSlotService.deleteClerkTimeSlotIsAssignedToRequestRelation(
//                        clerkTimeSlot3.getId(), request1.getId()));
//        clerkTimeSlotService.deleteClerkTimeSlotIsAssignedToRequestRelation(
//                clerkTimeSlot1.getId(), request1.getId());
//        Assertions.assertNull(clerkTimeSlot1.getRequest());
    }
}