package cz.cvut.fit.tjv.sem_work.api.controller;

import cz.cvut.fit.tjv.sem_work.business.EntityDoesNotExistException;
import cz.cvut.fit.tjv.sem_work.business.MandatoryValueMissingException;
import cz.cvut.fit.tjv.sem_work.business.RequestService;
import cz.cvut.fit.tjv.sem_work.domain.Clerk;
import cz.cvut.fit.tjv.sem_work.domain.Request;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(RequestController.class)
class RequestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestService requestService;

    Request request1;
    Request request2;
    Request request3;
    Request request4;
    Request nonExistingRequest;
    Request requestToPost;
    Request updatedRequest;

    @BeforeEach
    void setUp() {
        request1 = new Request(1L, LocalDateTime.now(), 1L);
        request2 = new Request(2L, LocalDateTime.now(), 2L);
        request3 = new Request(3L, LocalDateTime.now(), 3L);
        request4 = new Request(4L, LocalDateTime.now(), 4L);
        nonExistingRequest = new Request(5L, LocalDateTime.now(), 4L);
        requestToPost = new Request(6L, LocalDateTime.now(), 3L);
        updatedRequest = new Request(2L, LocalDateTime.now(), 2L);

        Mockito.when(requestService.readAll()).thenReturn(List.of(
                request1, request2, request3, request4));
        Mockito.when(requestService.readById(request1.getId())).thenReturn(Optional.of(request1));
        Mockito.when(requestService.readById(request2.getId())).thenReturn(Optional.of(request2));
        Mockito.when(requestService.readById(request3.getId())).thenReturn(Optional.of(request3));
        Mockito.when(requestService.readById(request4.getId())).thenReturn(Optional.of(request4));
        Mockito.when(requestService.readById(nonExistingRequest.getId()))
                .thenReturn(Optional.empty());
    }

    @Test
    void create() throws Exception {
//        // tests for mandatory values and unique values exceptions
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/requests")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("deadline", requestToPost.getDeadline().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/requests")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("id", requestToPost.getId().toString())
//                        .param("deadlineFee", requestToPost.getDeadlineFee().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        // tests for failures of other layers
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/requests")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("deadline", requestToPost.getDeadline().toString())
//                        .param("deadlineFee", requestToPost.getDeadlineFee().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        Mockito.doAnswer(
//                invocationOnMock -> {
//                    Request paramRequest = invocationOnMock.getArgument(0);
//                    paramRequest.setId(requestToPost.getId());
//                    return null;
//                }
//        ).when(requestService).create(ArgumentMatchers.isA(Request.class));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/requests")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("deadline", requestToPost.getDeadline().toString())
//                        .param("deadlineFee", requestToPost.getDeadlineFee().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        // tests for successful post
//        Mockito.when(requestService.readById(requestToPost.getId()))
//                .thenReturn(Optional.of(requestToPost));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/requests")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("deadline", requestToPost.getDeadline().toString())
//                        .param("deadlineFee", requestToPost.getDeadlineFee().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$",
//                        Matchers.is(requestToPost.getId())));
    }

    @Test
    void readById() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/requests/" + request1.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.deadline",
//                        Matchers.is(request1.getDeadline())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.deadlineFee",
//                        Matchers.is(request1.getDeadlineFee())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/requests/" + request3.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.deadline",
//                        Matchers.is(request3.getDeadline())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.deadlineFee",
//                        Matchers.is(request3.getDeadlineFee())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/requests/" + request4.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.deadline",
//                        Matchers.is(request4.getDeadline())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.deadlineFee",
//                        Matchers.is(request4.getDeadlineFee())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/requests/" + nonExistingRequest.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void readAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/requests")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    void updateById() throws Exception {
//        // tests for non-existing id
//        Mockito.doThrow(new EntityDoesNotExistException())
//                .when(requestService)
//                .update(ArgumentMatchers.isA(Request.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/requests/" + nonExistingRequest.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("deadline", nonExistingRequest.getDeadline().toString())
//                        .param("deadlineFee", nonExistingRequest.getDeadlineFee().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        // tests for mandatory values and unique values exceptions
//        Mockito.doAnswer(
//                        invocationOnMock -> {
//                            throw new MandatoryValueMissingException(
//                                    invocationOnMock.getArgument(0));
//                        })
//                .when(requestService)
//                .update(ArgumentMatchers.isA(Request.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/requests/" + updatedRequest.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("deadline", updatedRequest.getDeadline().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/requests/" + updatedRequest.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("deadlineFee", updatedRequest.getDeadlineFee().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        // tests for successful put
//        Mockito.doReturn(null)
//                .when(requestService)
//                .update(ArgumentMatchers.isA(Request.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/requests/" + updatedRequest.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("deadline", updatedRequest.getDeadline().toString())
//                        .param("deadlineFee", updatedRequest.getDeadlineFee().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/requests/" + request2.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/requests/" + request3.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/requests/" + request3.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void readRichManGivenRequest() {
    }

    @Test
    void updateRequestIsDemandingRichManRelation() {
    }

    @Test
    void deleteRequestIsDemandingRichManRelation() {
    }

    @Test
    void readStateOfRequest() {
    }

    @Test
    void updateRequestIsInRequestStateRelation() {
    }

    @Test
    void deleteRequestIsInRequestStateRelation() {
    }

    @Test
    void readOrganizationWhichCreatedRequest() {
    }

    @Test
    void updateRequestWasCreatedByOrganizationRelation() {
    }

    @Test
    void deleteRequestWasCreatedByOrganizationRelation() {
    }
}