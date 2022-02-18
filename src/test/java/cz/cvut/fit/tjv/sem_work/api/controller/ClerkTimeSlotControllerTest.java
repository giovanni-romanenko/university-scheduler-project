package cz.cvut.fit.tjv.sem_work.api.controller;

import cz.cvut.fit.tjv.sem_work.business.ClerkTimeSlotService;
import cz.cvut.fit.tjv.sem_work.business.EntityDoesNotExistException;
import cz.cvut.fit.tjv.sem_work.business.MandatoryValueMissingException;
import cz.cvut.fit.tjv.sem_work.domain.ClerkTimeSlot;
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


@WebMvcTest(ClerkTimeSlotController.class)
class ClerkTimeSlotControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClerkTimeSlotService clerkTimeSlotService;

    ClerkTimeSlot clerkTimeSlot1;
    ClerkTimeSlot clerkTimeSlot2;
    ClerkTimeSlot clerkTimeSlot3;
    ClerkTimeSlot clerkTimeSlot4;
    ClerkTimeSlot nonExistingClerkTimeSlot;
    ClerkTimeSlot clerkTimeSlotToPost;
    ClerkTimeSlot updatedClerkTimeSlot;

    @BeforeEach
    void setUp() {
        clerkTimeSlot1 = new ClerkTimeSlot(1L, LocalDateTime.now(), LocalDateTime.now());
        clerkTimeSlot2 = new ClerkTimeSlot(2L, LocalDateTime.now(), LocalDateTime.now());
        clerkTimeSlot3 = new ClerkTimeSlot(3L, LocalDateTime.now(), LocalDateTime.now());
        clerkTimeSlot4 = new ClerkTimeSlot(4L, LocalDateTime.now(), LocalDateTime.now());
        nonExistingClerkTimeSlot = new ClerkTimeSlot(5L, LocalDateTime.now(), LocalDateTime.now());
        clerkTimeSlotToPost = new ClerkTimeSlot(6L, LocalDateTime.now(), LocalDateTime.now());
        updatedClerkTimeSlot = new ClerkTimeSlot(2L, LocalDateTime.now(), LocalDateTime.now());

        Mockito.when(clerkTimeSlotService.readAll()).thenReturn(List.of(
                clerkTimeSlot1, clerkTimeSlot2, clerkTimeSlot3, clerkTimeSlot4));
        Mockito.when(clerkTimeSlotService.readById(clerkTimeSlot1.getId())).thenReturn(Optional.of(clerkTimeSlot1));
        Mockito.when(clerkTimeSlotService.readById(clerkTimeSlot2.getId())).thenReturn(Optional.of(clerkTimeSlot2));
        Mockito.when(clerkTimeSlotService.readById(clerkTimeSlot3.getId())).thenReturn(Optional.of(clerkTimeSlot3));
        Mockito.when(clerkTimeSlotService.readById(clerkTimeSlot4.getId())).thenReturn(Optional.of(clerkTimeSlot4));
        Mockito.when(clerkTimeSlotService.readById(nonExistingClerkTimeSlot.getId()))
                .thenReturn(Optional.empty());
    }

    @Test
    void create() throws Exception {
//        // tests for mandatory values and unique values exceptions
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/clerk_time_slots")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("startTime", clerkTimeSlotToPost.getStartTime().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/clerk_time_slots")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("endTime", clerkTimeSlotToPost.getStartTime().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        // tests for failures of other layers
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/clerk_time_slots")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("startTime", clerkTimeSlotToPost.getStartTime().toString())
//                        .param("endTime", clerkTimeSlotToPost.getStartTime().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        Mockito.doAnswer(
//                invocationOnMock -> {
//                    ClerkTimeSlot paramClerkTimeSlot = invocationOnMock.getArgument(0);
//                    paramClerkTimeSlot.setId(clerkTimeSlotToPost.getId());
//                    return null;
//                }
//        ).when(clerkTimeSlotService).create(ArgumentMatchers.isA(ClerkTimeSlot.class));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/clerk_time_slots")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("startTime", clerkTimeSlotToPost.getStartTime().toString())
//                        .param("endTime", clerkTimeSlotToPost.getStartTime().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        // tests for successful post
//        Mockito.when(clerkTimeSlotService.readById(clerkTimeSlotToPost.getId()))
//                .thenReturn(Optional.of(clerkTimeSlotToPost));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/clerk_time_slots")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("startTime", clerkTimeSlotToPost.getStartTime().toString())
//                        .param("endTime", clerkTimeSlotToPost.getStartTime().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$",
//                        Matchers.is(clerkTimeSlotToPost.getId())));
    }

    @Test
    void readById() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clerk_time_slots/" + clerkTimeSlot1.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime",
//                        Matchers.is(clerkTimeSlot1.getStartTime())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime",
//                        Matchers.is(clerkTimeSlot1.getEndTime())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clerk_time_slots/" + clerkTimeSlot3.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime",
//                        Matchers.is(clerkTimeSlot3.getStartTime())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime",
//                        Matchers.is(clerkTimeSlot3.getEndTime())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clerk_time_slots/" + clerkTimeSlot4.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime",
//                        Matchers.is(clerkTimeSlot4.getStartTime())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime",
//                        Matchers.is(clerkTimeSlot4.getEndTime())));
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clerk_time_slots/" + nonExistingClerkTimeSlot.getId().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void readAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clerk_time_slots")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    void updateById() throws Exception {
//        // tests for non-existing id
//        Mockito.doThrow(new EntityDoesNotExistException())
//                .when(clerkTimeSlotService)
//                .update(ArgumentMatchers.isA(ClerkTimeSlot.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/clerk_time_slots/" + nonExistingClerkTimeSlot.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("startTime", nonExistingClerkTimeSlot.getStartTime().toString())
//                        .param("endTime", nonExistingClerkTimeSlot.getEndTime().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        // tests for mandatory values and unique values exceptions
//        Mockito.doAnswer(
//                        invocationOnMock -> {
//                            throw new MandatoryValueMissingException(
//                                    invocationOnMock.getArgument(0));
//                        })
//                .when(clerkTimeSlotService)
//                .update(ArgumentMatchers.isA(ClerkTimeSlot.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/clerk_time_slots/" + updatedClerkTimeSlot.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("startTime", updatedClerkTimeSlot.getStartTime().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/clerk_time_slots/" + updatedClerkTimeSlot.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("endTime", updatedClerkTimeSlot.getEndTime().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
//        // tests for successful put
//        Mockito.doReturn(null)
//                .when(clerkTimeSlotService)
//                .update(ArgumentMatchers.isA(ClerkTimeSlot.class));
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/clerk_time_slots/" + updatedClerkTimeSlot.getId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("startTime", updatedClerkTimeSlot.getStartTime().toString())
//                        .param("endTime", updatedClerkTimeSlot.getEndTime().toString())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/clerk_time_slots/" + clerkTimeSlot2.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/clerk_time_slots/" + clerkTimeSlot3.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/clerk_time_slots/" + clerkTimeSlot3.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void readClerkWhichHasClerkTimeSlot() throws Exception {
    }

    @Test
    void updateClerkTimeSlotBelongsToClerkRelation() throws Exception {
    }

    @Test
    void deleteClerkTimeSlotBelongsToClerkRelation() throws Exception {
    }

    @Test
    void readRequestThatHasClerkTimeSlotAssigned() throws Exception {
    }

    @Test
    void updateClerkTimeSlotIsAssignedToRequestRelation() throws Exception {
    }

    @Test
    void deleteClerkTimeSlotIsAssignedToRequestRelation() throws Exception {
    }
}