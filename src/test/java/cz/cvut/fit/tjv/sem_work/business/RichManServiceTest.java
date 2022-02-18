package cz.cvut.fit.tjv.sem_work.business;

import cz.cvut.fit.tjv.sem_work.dao.RichManJpaRepository;
import cz.cvut.fit.tjv.sem_work.domain.RichMan;
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
class RichManServiceTest {
    @Autowired
    private RichManService richManService;

    @MockBean
    private RichManJpaRepository repository;

    RichMan richMan1;
    RichMan richMan2;
    RichMan richMan3;
    RichMan richMan4;
    RichMan nonExistingRichMan;
    RichMan richManMissingFirstName;
    RichMan richManMissingSecondName;
    RichMan updatedRichMan;
    RichMan updatedRichManMissingFirstName;

    @BeforeEach
    void setUp() {
        richMan1 = new RichMan(1L, "TestFN", "TestSN");
        richMan2 = new RichMan(2L, "TestFN", "TestSN");
        richMan3 = new RichMan(3L, "TestFN", "TestSN");
        richMan4 = new RichMan(4L, "TestFN", "TestSN");
        nonExistingRichMan = new RichMan(5L, "TestFN", "TestSN");
        richManMissingFirstName = new RichMan(6L, null, "TestSN");
        richManMissingSecondName = new RichMan(7L, "TestFN", null);
        updatedRichMan = new RichMan(3L, "ChangedFN", "ChangedSN");
        updatedRichManMissingFirstName = new RichMan(3L, null, "TestSN");

        Mockito.when(repository.existsById(richMan1.getId())).thenReturn(true);
        Mockito.when(repository.existsById(richMan2.getId())).thenReturn(true);
        Mockito.when(repository.existsById(richMan3.getId())).thenReturn(true);
        Mockito.when(repository.existsById(richMan4.getId())).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingRichMan.getId())).thenReturn(false);
        Mockito.when(repository.findById(richMan1.getId())).thenReturn(Optional.of(richMan1));
        Mockito.when(repository.findById(richMan2.getId())).thenReturn(Optional.of(richMan2));
        Mockito.when(repository.findById(richMan3.getId())).thenReturn(Optional.of(richMan3));
        Mockito.when(repository.findById(richMan4.getId())).thenReturn(Optional.of(richMan4));
        Mockito.when(repository.findById(nonExistingRichMan.getId())).thenReturn(Optional.empty());
        Mockito.when(repository.findAll()).thenReturn(List.of(
                richMan1, richMan2, richMan3, richMan4));
    }

    @Test
    void create() {
        richManService.create(nonExistingRichMan);
        Mockito.verify(repository, Mockito.times(1)).save(nonExistingRichMan);
        // test checkMandatoryParametersToCreate
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                richManService.create(richManMissingFirstName));
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                richManService.create(richManMissingSecondName));
    }

    @Test
    void readById() {
        Assertions.assertEquals(Optional.of(richMan1), richManService.readById(richMan1.getId()));
        Assertions.assertEquals(Optional.of(richMan2), richManService.readById(richMan2.getId()));
        Assertions.assertEquals(Optional.of(richMan3), richManService.readById(richMan3.getId()));
        Assertions.assertEquals(Optional.of(richMan4), richManService.readById(richMan4.getId()));
        Assertions.assertEquals(Optional.empty(), richManService.readById(nonExistingRichMan.getId()));
    }

    @Test
    void readAll() {
        Assertions.assertEquals(List.of(
                richMan1, richMan2, richMan3, richMan4), richManService.readAll());
    }

    @Test
    void update() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                richManService.update(nonExistingRichMan));
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                richManService.update(updatedRichManMissingFirstName));
        richManService.update(updatedRichMan);
        Mockito.verify(repository, Mockito.times(1)).save(updatedRichMan);
    }

    @Test
    void deleteById() {
        richManService.deleteById(richMan2.getId());
        Mockito.verify(repository, Mockito.times(1)).deleteById(richMan2.getId());
    }

    @Test
    void exists() {
        Assertions.assertTrue(richManService.exists(richMan1));
        Assertions.assertTrue(richManService.exists(richMan2));
        Assertions.assertTrue(richManService.exists(richMan3));
        Assertions.assertTrue(richManService.exists(richMan4));
        Assertions.assertFalse(richManService.exists(nonExistingRichMan));
    }
}