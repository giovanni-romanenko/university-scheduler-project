package cz.cvut.fit.tjv.sem_work.business;

import cz.cvut.fit.tjv.sem_work.dao.RequestStateJpaRepository;
import cz.cvut.fit.tjv.sem_work.domain.RequestState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RequestStateServiceTest {
    @Autowired
    private RequestStateService requestStateService;

    @MockBean
    private RequestStateJpaRepository repository;

    RequestState requestState1;
    RequestState requestState2;
    RequestState requestState3;
    RequestState requestState4;
    RequestState nonExistingRequestState;
    RequestState requestStateMissingName;
    RequestState updatedRequestState;
    RequestState updatedRequestStateMissingName;
    RequestState requestStateNonUniqueName;
    RequestState updatedRequestStateNonUniqueName;
    RequestState updatedRequestStateSameName;

    @BeforeEach
    void setUp() {
        requestState1 = new RequestState(1L, "TestName1");
        requestState2 = new RequestState(2L, "TestName2");
        requestState3 = new RequestState(3L, "TestName3");
        requestState4 = new RequestState(4L, "TestName4");
        nonExistingRequestState = new RequestState(5L, "TestName5");
        requestStateMissingName = new RequestState(6L, null);
        updatedRequestState = new RequestState(2L, "ChangedName");
        updatedRequestStateMissingName = new RequestState(2L, null);
        updatedRequestStateNonUniqueName = new RequestState(2L, "TestName4");
        updatedRequestStateSameName = new RequestState(2L, "TestName2");
        requestStateNonUniqueName = new RequestState(8L, "TestName3");

        Mockito.when(repository.existsById(requestState1.getId())).thenReturn(true);
        Mockito.when(repository.existsById(requestState2.getId())).thenReturn(true);
        Mockito.when(repository.existsById(requestState3.getId())).thenReturn(true);
        Mockito.when(repository.existsById(requestState4.getId())).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingRequestState.getId())).thenReturn(false);
        Mockito.when(repository.findById(requestState1.getId())).thenReturn(Optional.of(requestState1));
        Mockito.when(repository.findById(requestState2.getId())).thenReturn(Optional.of(requestState2));
        Mockito.when(repository.findById(requestState3.getId())).thenReturn(Optional.of(requestState3));
        Mockito.when(repository.findById(requestState4.getId())).thenReturn(Optional.of(requestState4));
        Mockito.when(repository.findById(nonExistingRequestState.getId())).thenReturn(Optional.empty());
        Mockito.when(repository.findAll()).thenReturn(List.of(
                requestState1, requestState2, requestState3, requestState4));
        Mockito.when(repository.findByName("TestName1")).thenReturn(Optional.of(requestState1));
        Mockito.when(repository.findByName("TestName2")).thenReturn(Optional.of(requestState2));
        Mockito.when(repository.findByName("TestName3")).thenReturn(Optional.of(requestState3));
        Mockito.when(repository.findByName("TestName4")).thenReturn(Optional.of(requestState4));
        Mockito.when(repository.findByName("TestName5")).thenReturn(Optional.empty());
        Mockito.when(repository.findByName(null)).thenReturn(Optional.empty());
        Mockito.when(repository.findByName("ChangedName")).thenReturn(Optional.empty());
    }

    @Test
    void create() {
        requestStateService.create(nonExistingRequestState);
        Mockito.verify(repository, Mockito.times(1)).save(nonExistingRequestState);
        // test checkMandatoryParametersToCreate
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                requestStateService.create(requestStateMissingName));
        // test checkUniqueConstraints
        Assertions.assertThrows(EntityViolatesUCException.class, () ->
                requestStateService.create(requestStateNonUniqueName));
    }

    @Test
    void readById() {
        Assertions.assertEquals(Optional.of(requestState1), requestStateService.readById(requestState1.getId()));
        Assertions.assertEquals(Optional.of(requestState2), requestStateService.readById(requestState2.getId()));
        Assertions.assertEquals(Optional.of(requestState3), requestStateService.readById(requestState3.getId()));
        Assertions.assertEquals(Optional.of(requestState4), requestStateService.readById(requestState4.getId()));
        Assertions.assertEquals(Optional.empty(), requestStateService.readById(
                nonExistingRequestState.getId()));
    }

    @Test
    void readAll() {
        Assertions.assertEquals(List.of(
                requestState1, requestState2, requestState3, requestState4), requestStateService.readAll());
    }

    @Test
    void update() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                requestStateService.update(nonExistingRequestState));
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                requestStateService.update(updatedRequestStateMissingName));
        Assertions.assertThrows(EntityViolatesUCException.class, () ->
                requestStateService.update(updatedRequestStateNonUniqueName));
        Assertions.assertDoesNotThrow(() ->
                requestStateService.update(updatedRequestStateSameName));
        requestStateService.update(updatedRequestState);
//        Mockito.verify(repository, Mockito.times(1)).save(updatedRequestState);
    }

    @Test
    void deleteById() {
        requestStateService.deleteById(requestState2.getId());
        Mockito.verify(repository, Mockito.times(1)).deleteById(requestState2.getId());
    }

    @Test
    void exists() {
        Assertions.assertTrue(requestStateService.exists(requestState1));
        Assertions.assertTrue(requestStateService.exists(requestState2));
        Assertions.assertTrue(requestStateService.exists(requestState3));
        Assertions.assertTrue(requestStateService.exists(requestState4));
        Assertions.assertFalse(requestStateService.exists(nonExistingRequestState));
    }
}