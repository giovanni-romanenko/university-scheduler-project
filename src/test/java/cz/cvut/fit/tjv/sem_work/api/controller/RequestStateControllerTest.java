package cz.cvut.fit.tjv.sem_work.api.controller;

import cz.cvut.fit.tjv.sem_work.business.EntityDoesNotExistException;
import cz.cvut.fit.tjv.sem_work.business.EntityViolatesUCException;
import cz.cvut.fit.tjv.sem_work.business.MandatoryValueMissingException;
import cz.cvut.fit.tjv.sem_work.business.RequestStateService;
import cz.cvut.fit.tjv.sem_work.domain.RequestState;
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

@WebMvcTest(RequestStateController.class)
class RequestStateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestStateService requestStateService;

    RequestState requestState1;
    RequestState requestState2;
    RequestState requestState3;
    RequestState requestState4;
    RequestState nonExistingRequestState;
    RequestState requestStateToPost;
    RequestState updatedRequestState;
    RequestState nonUniqueRequestState;

    @BeforeEach
    void setUp() {
        requestState1 = new RequestState(1L, "TestName1");
        requestState2 = new RequestState(2L, "TestName2");
        requestState3 = new RequestState(3L, "TestName3");
        requestState4 = new RequestState(4L, "TestName4");
        nonExistingRequestState = new RequestState(5L, "TestName5");
        requestStateToPost = new RequestState(6L, "TestName6");
        updatedRequestState = new RequestState(2L,"TestName7");
        nonUniqueRequestState = new RequestState(7L, "TestName3");

        Mockito.when(requestStateService.readAll()).thenReturn(List.of(
                requestState1, requestState2, requestState3, requestState4));
        Mockito.when(requestStateService.readById(requestState1.getId())).thenReturn(Optional.of(requestState1));
        Mockito.when(requestStateService.readById(requestState2.getId())).thenReturn(Optional.of(requestState2));
        Mockito.when(requestStateService.readById(requestState3.getId())).thenReturn(Optional.of(requestState3));
        Mockito.when(requestStateService.readById(requestState4.getId())).thenReturn(Optional.of(requestState4));
        Mockito.when(requestStateService.readById(nonExistingRequestState.getId()))
                .thenReturn(Optional.empty());
    }


    @Test
    void create() throws Exception {
//        // tests for mandatory values and unique values exceptions
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/request_states")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("id", requestStateToPost.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/request_states")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", nonUniqueRequestState.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isConflict());
//        // tests for failures of other layers
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/request_states")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", requestStateToPost.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        Mockito.doAnswer(
//                invocationOnMock -> {
//                    RequestState paramRequestState = invocationOnMock.getArgument(0);
//                    paramRequestState.setId(requestStateToPost.getId());
//                    return null;
//                }
//        ).when(requestStateService).create(ArgumentMatchers.isA(RequestState.class));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/request_states")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", requestStateToPost.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        // tests for successful post
//        Mockito.when(requestStateService.readById(requestStateToPost.getId()))
//                .thenReturn(Optional.of(requestStateToPost));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/request_states")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", requestStateToPost.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$",
//                        Matchers.is(requestStateToPost.getId())));
    }

    @Test
    void readById() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/request_states/" + requestState1.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
//                        Matchers.is(requestState1.getName())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/request_states/" + requestState3.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
//                        Matchers.is(requestState3.getName())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/request_states/" + requestState3.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
//                        Matchers.is(requestState3.getName())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/request_states/" + nonExistingRequestState.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void readAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/request_states")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    void updateById() throws Exception {
//        // tests for non-existing id
//        Mockito.doThrow(new EntityDoesNotExistException())
//                .when(requestStateService)
//                .update(ArgumentMatchers.isA(RequestState.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/request_states/" + nonExistingRequestState.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", nonExistingRequestState.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        // tests for mandatory values and unique values exceptions
//        Mockito.doAnswer(
//                        invocationOnMock -> {
//                            throw new MandatoryValueMissingException(
//                                    invocationOnMock.getArgument(0));
//                        })
//                .when(requestStateService)
//                .update(ArgumentMatchers.isA(RequestState.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/request_states/" + updatedRequestState.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("id", updatedRequestState.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        Mockito.doAnswer(
//                        invocationOnMock -> {
//                            throw new EntityViolatesUCException(
//                                    invocationOnMock.getArgument(0));
//                        })
//                .when(requestStateService)
//                .update(ArgumentMatchers.isA(RequestState.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/request_states/" + requestState2.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", requestState1.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isConflict());
//        // tests for successful put
//        Mockito.doReturn(null)
//                .when(requestStateService)
//                .update(ArgumentMatchers.isA(RequestState.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/request_states/" + updatedRequestState.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", updatedRequestState.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/request_states/" + requestState1.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", requestState1.getName())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/request_states/" + requestState2.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/request_states/" + requestState3.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/request_states/" + requestState3.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}