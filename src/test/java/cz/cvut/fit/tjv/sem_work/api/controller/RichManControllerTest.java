package cz.cvut.fit.tjv.sem_work.api.controller;

import cz.cvut.fit.tjv.sem_work.business.EntityDoesNotExistException;
import cz.cvut.fit.tjv.sem_work.business.MandatoryValueMissingException;
import cz.cvut.fit.tjv.sem_work.business.RichManService;
import cz.cvut.fit.tjv.sem_work.domain.RichMan;
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

@WebMvcTest(RichManController.class)
class RichManControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RichManService richManService;

    RichMan richMan1;
    RichMan richMan2;
    RichMan richMan3;
    RichMan richMan4;
    RichMan nonExistingRichMan;
    RichMan richManToPost;
    RichMan updatedRichMan;

    @BeforeEach
    void setUp() {
        richMan1 = new RichMan(1L, "TestFN1", "TestSN1");
        richMan2 = new RichMan(2L, "TestFN2", "TestSN2");
        richMan3 = new RichMan(3L, "TestFN3", "TestSN3");
        richMan4 = new RichMan(4L, "TestFN4", "TestSN4");
        nonExistingRichMan = new RichMan(5L, "TestFN", "TestSN");
        richManToPost = new RichMan(6L, "TestFN", "TestSN");
        updatedRichMan = new RichMan(2L, "ChangedFN", "ChangedSN");

        Mockito.when(richManService.readAll()).thenReturn(List.of(
                richMan1, richMan2, richMan3, richMan4));
        Mockito.when(richManService.readById(richMan1.getId())).thenReturn(Optional.of(richMan1));
        Mockito.when(richManService.readById(richMan2.getId())).thenReturn(Optional.of(richMan2));
        Mockito.when(richManService.readById(richMan3.getId())).thenReturn(Optional.of(richMan3));
        Mockito.when(richManService.readById(richMan4.getId())).thenReturn(Optional.of(richMan4));
        Mockito.when(richManService.readById(nonExistingRichMan.getId()))
                .thenReturn(Optional.empty());
    }


    @Test
    void create() throws Exception {
//        // tests for mandatory values and unique values exceptions
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/rich_men")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstName", richManToPost.getFirstName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/rich_men")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("secondName", richManToPost.getSecondName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        // tests for failures of other layers
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/rich_men")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstName", richManToPost.getFirstName())
//                        .param("secondName", richManToPost.getSecondName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        Mockito.doAnswer(
//                invocationOnMock -> {
//                    RichMan paramRichMan = invocationOnMock.getArgument(0);
//                    paramRichMan.setId(richManToPost.getId());
//                    return null;
//                }
//        ).when(richManService).create(ArgumentMatchers.isA(RichMan.class));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/rich_men")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstName", richManToPost.getFirstName())
//                        .param("secondName", richManToPost.getSecondName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        // tests for successful post
//        Mockito.when(richManService.readById(richManToPost.getId()))
//                .thenReturn(Optional.of(richManToPost));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/rich_men")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstName", richManToPost.getFirstName())
//                        .param("secondName", richManToPost.getSecondName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$",
//                        Matchers.is(richManToPost.getId())));
    }

    @Test
    void readById() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/rich_men/" + richMan1.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
//                        Matchers.is(richMan1.getFirstName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.secondName",
//                        Matchers.is(richMan1.getSecondName())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/rich_men/" + richMan3.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
//                        Matchers.is(richMan3.getFirstName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.secondName",
//                        Matchers.is(richMan3.getSecondName())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/rich_men/" + richMan4.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
//                        Matchers.is(richMan4.getFirstName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.secondName",
//                        Matchers.is(richMan4.getSecondName())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/rich_men/" + nonExistingRichMan.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void readAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/rich_men")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    void updateById() throws Exception {
//        // tests for non-existing id
//        Mockito.doThrow(new EntityDoesNotExistException())
//                .when(richManService)
//                .update(ArgumentMatchers.isA(RichMan.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/rich_men/" + nonExistingRichMan.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstName", nonExistingRichMan.getFirstName())
//                        .param("secondName", nonExistingRichMan.getSecondName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        // tests for mandatory values and unique values exceptions
//        Mockito.doAnswer(
//                        invocationOnMock -> {
//                            throw new MandatoryValueMissingException(
//                                    invocationOnMock.getArgument(0));
//                        })
//                .when(richManService)
//                .update(ArgumentMatchers.isA(RichMan.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/rich_men/" + updatedRichMan.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstName", updatedRichMan.getFirstName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/rich_men/" + updatedRichMan.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("secondName", updatedRichMan.getSecondName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        // tests for successful put
//        Mockito.doReturn(null)
//                .when(richManService)
//                .update(ArgumentMatchers.isA(RichMan.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/rich_men/" + updatedRichMan.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("firstName", updatedRichMan.getFirstName())
//                        .param("secondName", updatedRichMan.getSecondName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/rich_men/" + richMan2.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/rich_men/" + richMan3.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/rich_men/" + richMan3.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}