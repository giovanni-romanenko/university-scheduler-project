package cz.cvut.fit.tjv.sem_work.business;

import cz.cvut.fit.tjv.sem_work.dao.OrganizationJpaRepository;
import cz.cvut.fit.tjv.sem_work.domain.Organization;
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
class OrganizationServiceTest {
    @Autowired
    private OrganizationService organizationService;

    @MockBean
    private OrganizationJpaRepository repository;

    Organization organization1;
    Organization organization2;
    Organization organization3;
    Organization organization4;
    Organization nonExistingOrganization;
    Organization organizationMissingName;
    Organization updatedOrganization;
    Organization updatedOrganizationMissingName;

    @BeforeEach
    void setUp() {
        organization1 = new Organization(1L, "TestName");
        organization2 = new Organization(2L, "TestName");
        organization3 = new Organization(3L, "TestName");
        organization4 = new Organization(4L, "TestName");
        nonExistingOrganization = new Organization(5L, "TestName");
        organizationMissingName = new Organization(6L, null);
        updatedOrganization = new Organization(4L, "ChangedName");
        updatedOrganizationMissingName = new Organization(4L, null);

        Mockito.when(repository.existsById(organization1.getId())).thenReturn(true);
        Mockito.when(repository.existsById(organization2.getId())).thenReturn(true);
        Mockito.when(repository.existsById(organization3.getId())).thenReturn(true);
        Mockito.when(repository.existsById(organization4.getId())).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingOrganization.getId())).thenReturn(false);
        Mockito.when(repository.findById(organization1.getId())).thenReturn(Optional.of(organization1));
        Mockito.when(repository.findById(organization2.getId())).thenReturn(Optional.of(organization2));
        Mockito.when(repository.findById(organization3.getId())).thenReturn(Optional.of(organization3));
        Mockito.when(repository.findById(organization4.getId())).thenReturn(Optional.of(organization4));
        Mockito.when(repository.findById(nonExistingOrganization.getId())).thenReturn(Optional.empty());
        Mockito.when(repository.findAll()).thenReturn(List.of(organization1, organization2,
                organization3, organization4));

    }

    @Test
    void create() {
        organizationService.create(nonExistingOrganization);
        Mockito.verify(repository, Mockito.times(1)).save(nonExistingOrganization);
        // test checkMandatoryParametersToCreate
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                organizationService.create(organizationMissingName));
    }

    @Test
    void readById() {
        Assertions.assertEquals(Optional.of(organization1), organizationService.readById(organization1.getId()));
        Assertions.assertEquals(Optional.of(organization2), organizationService.readById(organization2.getId()));
        Assertions.assertEquals(Optional.of(organization3), organizationService.readById(organization3.getId()));
        Assertions.assertEquals(Optional.of(organization4), organizationService.readById(organization4.getId()));
        Assertions.assertEquals(Optional.empty(), organizationService.readById(nonExistingOrganization.getId()));
    }

    @Test
    void readAll() {
        Assertions.assertEquals(List.of(organization1, organization2,
                organization3, organization4), organizationService.readAll());
    }

    @Test
    void update() {
        Assertions.assertThrows(EntityDoesNotExistException.class, () ->
                organizationService.update(nonExistingOrganization));
        Assertions.assertThrows(MandatoryValueMissingException.class, () ->
                organizationService.update(updatedOrganizationMissingName));
        organizationService.update(updatedOrganization);
        Mockito.verify(repository, Mockito.times(1)).save(updatedOrganization);
    }

    @Test
    void deleteById() {
        organizationService.deleteById(organization2.getId());
        Mockito.verify(repository, Mockito.times(1)).deleteById(organization2.getId());
    }

    @Test
    void exists() {
        Assertions.assertTrue(organizationService.exists(organization1));
        Assertions.assertTrue(organizationService.exists(organization2));
        Assertions.assertTrue(organizationService.exists(organization3));
        Assertions.assertTrue(organizationService.exists(organization4));
        Assertions.assertFalse(organizationService.exists(nonExistingOrganization));
    }
}