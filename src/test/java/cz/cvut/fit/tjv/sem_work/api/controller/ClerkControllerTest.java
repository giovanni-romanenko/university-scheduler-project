package cz.cvut.fit.tjv.sem_work.api.controller;

import cz.cvut.fit.tjv.sem_work.business.ClerkService;
import cz.cvut.fit.tjv.sem_work.business.EntityDoesNotExistException;
import cz.cvut.fit.tjv.sem_work.business.MandatoryValueMissingException;
import cz.cvut.fit.tjv.sem_work.domain.Clerk;
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

@WebMvcTest(ClerkController.class)
class ClerkControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClerkService clerkService;

    Clerk clerk1;
    Clerk clerk2;
    Clerk clerk3;
    Clerk clerk4;
    Clerk nonExistingClerk;
    Clerk clerkToPost;
    Clerk updatedClerk;

    @BeforeEach
    void setUp() {
        clerk1 = new Clerk(1L, "TestFN1", "TestSN1", null, null);
        clerk2 = new Clerk(2L, "TestFN2", "TestSN2", null, null);
        clerk3 = new Clerk(3L, "TestFN3", "TestSN3", null, 0L);
        clerk4 = new Clerk(4L, "TestFN4", "TestSN4", "test", null);
        nonExistingClerk = new Clerk(5L, "TestFN", "TestSN", "test", null);
        clerkToPost = new Clerk(6L, "TestFN", "TestSN", "test", 1L);
        updatedClerk = new Clerk(2L, "ChangedFN", "ChangedSN", "abc", 23L);

        Mockito.when(clerkService.readAll()).thenReturn(List.of(
                clerk1, clerk2, clerk3, clerk4));
        Mockito.when(clerkService.readById(clerk1.getId())).thenReturn(Optional.of(clerk1));
        Mockito.when(clerkService.readById(clerk2.getId())).thenReturn(Optional.of(clerk2));
        Mockito.when(clerkService.readById(clerk3.getId())).thenReturn(Optional.of(clerk3));
        Mockito.when(clerkService.readById(clerk4.getId())).thenReturn(Optional.of(clerk4));
        Mockito.when(clerkService.readById(nonExistingClerk.getId()))
                .thenReturn(Optional.empty());
    }


    @Test
    void create() throws Exception {
        // tests for mandatory values and unique values exceptions
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/clerks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(
//                                "{\"firstName\":\"" + clerkToPost.getFirstName() + "\","
//                                + "\"essayAboutLifeGoals\":\"" + clerkToPost.getEssayAboutLifeGoals() + "\","
//                                + "\"moodInIntegerEquivalent\":" + clerkToPost.getMoodInIntegerEquivalent() + "}"
//                        )
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/clerks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(
//                                "{\"id\":\"" + clerkToPost.getId() + "\","
//                                + "\"secondName\":\"" + clerkToPost.getSecondName() + "\","
//                                + "\"moodInIntegerEquivalent\":\"" + clerkToPost.getMoodInIntegerEquivalent() + "\"}"
//                        )
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        // tests for failures of other layers
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/clerks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstName", clerkToPost.getFirstName())
//                        .param("secondName", clerkToPost.getSecondName())
//                        .param("essayAboutLifeGoals", clerkToPost.getEssayAboutLifeGoals())
//                        .param("moodInIntegerEquivalent", clerkToPost.getMoodInIntegerEquivalent().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        Mockito.doAnswer(
//                invocationOnMock -> {
//                    Clerk paramClerk = invocationOnMock.getArgument(0);
//                    paramClerk.setId(clerkToPost.getId());
//                    return null;
//                }
//        ).when(clerkService).create(ArgumentMatchers.isA(Clerk.class));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/clerks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstName", clerkToPost.getFirstName())
//                        .param("secondName", clerkToPost.getSecondName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        // tests for successful post
//        Mockito.when(clerkService.readById(clerkToPost.getId())).thenReturn(Optional.of(clerkToPost));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/clerks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstName", clerkToPost.getFirstName())
//                        .param("secondName", clerkToPost.getSecondName())
//                        .param("essayAboutLifeGoals", clerkToPost.getEssayAboutLifeGoals())
//                        .param("moodInIntegerEquivalent", clerkToPost.getMoodInIntegerEquivalent().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$",
//                        Matchers.is(clerkToPost.getId())));
    }

    @Test
    void readById() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clerks/" + clerk1.getId().toString())
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
//                        Matchers.is(clerk1.getFirstName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.secondName",
//                        Matchers.is(clerk1.getSecondName())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clerks/" + clerk3.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
//                        Matchers.is(clerk3.getFirstName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.secondName",
//                        Matchers.is(clerk3.getSecondName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.moodInIntegerEquivalent",
//                        Matchers.is(clerk3.getMoodInIntegerEquivalent())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clerks/" + clerk4.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
//                        Matchers.is(clerk4.getFirstName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.secondName",
//                        Matchers.is(clerk4.getSecondName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.essayAboutLifeGoals",
//                        Matchers.is(clerk4.getEssayAboutLifeGoals())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clerks/" + nonExistingClerk.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void readAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clerks")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    void updateById() throws Exception {
//        // tests for non-existing id
//        Mockito.doThrow(new EntityDoesNotExistException())
//                .when(clerkService)
//                .update(ArgumentMatchers.isA(Clerk.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/clerks/" + nonExistingClerk.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstName", nonExistingClerk.getFirstName())
//                        .param("secondName", nonExistingClerk.getSecondName())
//                        .param("essayAboutLifeGoals", nonExistingClerk.getEssayAboutLifeGoals())
//                        .param("moodInIntegerEquivalent", nonExistingClerk.getMoodInIntegerEquivalent().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        // tests for mandatory values and unique values exceptions
//        Mockito.doAnswer(
//                        invocationOnMock -> {
//                            throw new MandatoryValueMissingException(
//                                    invocationOnMock.getArgument(0));
//                        })
//                .when(clerkService)
//                .update(ArgumentMatchers.isA(Clerk.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/clerks/" + updatedClerk.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstName", updatedClerk.getFirstName())
//                        .param("essayAboutLifeGoals", updatedClerk.getEssayAboutLifeGoals())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/clerks/" + updatedClerk.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("secondName", updatedClerk.getSecondName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        // tests for successful put
//        Mockito.doReturn(null)
//                .when(clerkService)
//                .update(ArgumentMatchers.isA(Clerk.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/clerks/" + updatedClerk.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstName", updatedClerk.getFirstName())
//                        .param("secondName", updatedClerk.getSecondName())
//                        .param("essayAboutLifeGoals", updatedClerk.getEssayAboutLifeGoals())
//                        .param("moodInIntegerEquivalent", updatedClerk.getMoodInIntegerEquivalent().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/clerks/" + clerk2.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/clerks/" + clerk3.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/clerks/" + clerk3.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void readOrganizationWhereClerkWorks() throws Exception {
        // todo - just mock all possible reactions - only reactions, not actual data
    }

    @Test
    void updateClerkWorksInOrganizationRelation() throws Exception {
    }

    @Test
    void deleteClerkWorksInOrganizationRelation() throws Exception {
    }

    @Test
    void readAllRequestsSolvableByClerk() throws Exception {
    }

    @Test
    void updateClerkCanSolveRequestRelation() throws Exception {
    }

    @Test
    void deleteClerkCanSolveRequestRelation() throws Exception {
    }

    @Test
    void readAllUnassignedTimeSlotsOfClerk() throws Exception {
    }
}