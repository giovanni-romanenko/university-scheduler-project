package cz.cvut.fit.tjv.sem_work.api.controller;

import cz.cvut.fit.tjv.sem_work.business.EntityDoesNotExistException;
import cz.cvut.fit.tjv.sem_work.business.MandatoryValueMissingException;
import cz.cvut.fit.tjv.sem_work.business.OrganizationService;
import cz.cvut.fit.tjv.sem_work.domain.Organization;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(OrganizationController.class)
class OrganizationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationService organizationService;

    Organization organization1;
    Organization organization2;
    Organization organization3;
    Organization organization4;
    Organization nonExistingOrganization;
    Organization organizationToPost;
    Organization updatedOrganization;

    @BeforeEach
    void setUp() {
        organization1 = new Organization(1L, "TestName1");
        organization2 = new Organization(2L, "TestName2");
        organization3 = new Organization(3L, "TestName3");
        organization4 = new Organization(4L, "TestName4");
        nonExistingOrganization = new Organization(5L, "TestName5");
        organizationToPost = new Organization(6L, "TestName6");
        updatedOrganization = new Organization(2L, "TestName7");

        Mockito.when(organizationService.readAll()).thenReturn(List.of(
                organization1, organization2, organization3, organization4));
        Mockito.when(organizationService.readById(organization1.getId())).thenReturn(Optional.of(organization1));
        Mockito.when(organizationService.readById(organization2.getId())).thenReturn(Optional.of(organization2));
        Mockito.when(organizationService.readById(organization3.getId())).thenReturn(Optional.of(organization3));
        Mockito.when(organizationService.readById(organization4.getId())).thenReturn(Optional.of(organization4));
        Mockito.when(organizationService.readById(nonExistingOrganization.getId()))
                .thenReturn(Optional.empty());
    }

    @Test
    void create() throws Exception {
//        // tests for mandatory values and unique values exceptions
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/organizations")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("id", organizationToPost.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        // tests for failures of other layers
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/organizations")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", organizationToPost.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        Mockito.doAnswer(
//                invocationOnMock -> {
//                    Organization paramOrganization = invocationOnMock.getArgument(0);
//                    paramOrganization.setId(organizationToPost.getId());
//                    return null;
//                }
//        ).when(organizationService).create(ArgumentMatchers.isA(Organization.class));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/organizations")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", organizationToPost.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        // tests for successful post
//        Mockito.when(organizationService.readById(organizationToPost.getId()))
//                .thenReturn(Optional.of(organizationToPost));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/organizations")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", organizationToPost.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$",
//                        Matchers.is(organizationToPost.getId())));
    }

    @Test
    void readById() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/organizations/" + organization1.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
//                        Matchers.is(organization1.getName())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/organizations/" + organization3.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
//                        Matchers.is(organization3.getName())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/organizations/" + organization4.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
//                        Matchers.is(organization4.getName())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/organizations/" + nonExistingOrganization.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void readAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/organizations")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    void updateById() throws Exception {
//        // tests for non-existing id
//        Mockito.doThrow(new EntityDoesNotExistException())
//                .when(organizationService)
//                .update(ArgumentMatchers.isA(Organization.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/organizations/" + nonExistingOrganization.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", nonExistingOrganization.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        // tests for mandatory values and unique values exceptions
//        Mockito.doAnswer(
//                        invocationOnMock -> {
//                            throw new MandatoryValueMissingException(
//                                    invocationOnMock.getArgument(0));
//                        })
//                .when(organizationService)
//                .update(ArgumentMatchers.isA(Organization.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/organizations/" + updatedOrganization.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("id", updatedOrganization.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        // tests for successful put
//        Mockito.doReturn(null)
//                .when(organizationService)
//                .update(ArgumentMatchers.isA(Organization.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/organizations/" + updatedOrganization.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", nonExistingOrganization.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/organizations/" + organization2.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/organizations/" + organization3.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/organizations/" + organization3.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}