package cz.cvut.fit.tjv.sem_work.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.sem_work.api.converter.ClerkConverter;
import cz.cvut.fit.tjv.sem_work.api.converter.ClerkTimeSlotConverter;
import cz.cvut.fit.tjv.sem_work.api.converter.RequestConverter;
import cz.cvut.fit.tjv.sem_work.api.dto.ClerkDto;
import cz.cvut.fit.tjv.sem_work.api.dto.ClerkTimeSlotDto;
import cz.cvut.fit.tjv.sem_work.api.dto.RequestDto;
import cz.cvut.fit.tjv.sem_work.api.exception.NoEntityFoundException;
import cz.cvut.fit.tjv.sem_work.api.exception.SingleRelationIsNotSetException;
import cz.cvut.fit.tjv.sem_work.api.views.ClerkTimeSlotViews;
import cz.cvut.fit.tjv.sem_work.api.views.ClerkViews;
import cz.cvut.fit.tjv.sem_work.api.views.RequestViews;
import cz.cvut.fit.tjv.sem_work.business.ClerkTimeSlotService;
import cz.cvut.fit.tjv.sem_work.domain.Clerk;
import cz.cvut.fit.tjv.sem_work.domain.ClerkTimeSlot;
import cz.cvut.fit.tjv.sem_work.domain.Request;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ClerkTimeSlotController {
    private final ClerkTimeSlotService clerkTimeSlotService;

    public ClerkTimeSlotController(ClerkTimeSlotService clerkTimeSlotService) {
        this.clerkTimeSlotService = clerkTimeSlotService;
    }

    @PostMapping("/v1/clerk_time_slots")
    public Long create(@JsonView(ClerkTimeSlotViews.FullDataWithoutId.class)
                       @RequestBody ClerkTimeSlotDto clerkTimeSlotDto){
        ClerkTimeSlot clerkTimeSlot = ClerkTimeSlotConverter.fromDto(clerkTimeSlotDto);
        clerkTimeSlotService.create(clerkTimeSlot);
        if (clerkTimeSlot.getId() == null)
            throw new NoEntityFoundException();
        clerkTimeSlot = clerkTimeSlotService.readById(clerkTimeSlot.getId()).orElseThrow(NoEntityFoundException::new);
        return clerkTimeSlot.getId();
    }

    @JsonView(ClerkTimeSlotViews.FullDataWithoutId.class)
    @GetMapping("/v1/clerk_time_slots/{id}")
    public ClerkTimeSlotDto readById(@PathVariable Long id) {
        return ClerkTimeSlotConverter.toDto(clerkTimeSlotService.readById(id).orElseThrow(NoEntityFoundException::new));
    }

    @JsonView(ClerkTimeSlotViews.FullDataWithId.class)
    @GetMapping("/v1/clerk_time_slots")
    public Collection<ClerkTimeSlotDto> readAll() {
        return ClerkTimeSlotConverter.toDtoMany(clerkTimeSlotService.readAll());
    }

    @PutMapping("/v1/clerk_time_slots/{id}")
    public void updateById(@PathVariable Long id,
                           @JsonView(ClerkTimeSlotViews.FullDataWithoutId.class)
                           @RequestBody ClerkTimeSlotDto clerkTimeSlotDto) {
        ClerkTimeSlot clerkTimeSlotToUpdate = ClerkTimeSlotConverter.fromDto(clerkTimeSlotDto);
        clerkTimeSlotToUpdate.setId(id);
        clerkTimeSlotService.update(clerkTimeSlotToUpdate);
    }

    @DeleteMapping("/v1/clerk_time_slots/{id}")
    public void deleteById(@PathVariable Long id) {
        clerkTimeSlotService.deleteById(id);
    }

    @JsonView(ClerkViews.FullDataWithId.class)
    @GetMapping("/v1/clerk_time_slots/{clerkTimeSlotId}/clerks")
    public ClerkDto readClerkWhichHasClerkTimeSlot(@PathVariable Long clerkTimeSlotId) {
        Clerk clerk = clerkTimeSlotService.readClerkWhichHasClerkTimeSlot(clerkTimeSlotId).orElseThrow(
                SingleRelationIsNotSetException::new
        );
        return ClerkConverter.toDto(clerk);
    }

    @PutMapping("/v1/clerk_time_slots/{clerkTimeSlotId}/clerks/{clerkId}")
    public void updateClerkTimeSlotBelongsToClerkRelation(@PathVariable Long clerkTimeSlotId,
                                                          @PathVariable Long clerkId) {
        clerkTimeSlotService.updateClerkTimeSlotBelongsToClerkRelation(clerkTimeSlotId, clerkId);
    }

    @DeleteMapping("/v1/clerk_time_slots/{clerkTimeSlotId}/clerks/{clerkId}")
    public void deleteClerkTimeSlotBelongsToClerkRelation(@PathVariable Long clerkTimeSlotId,
                                                          @PathVariable Long clerkId) {
        clerkTimeSlotService.deleteClerkTimeSlotBelongsToClerkRelation(clerkTimeSlotId, clerkId);
    }

    @JsonView(RequestViews.FullDataWithId.class)
    @GetMapping("/v1/clerk_time_slots/{clerkTimeSlotId}/requests")
    public RequestDto readRequestThatHasClerkTimeSlotAssigned(@PathVariable Long clerkTimeSlotId) {
        Request request = clerkTimeSlotService.readRequestThatHasClerkTimeSlotAssigned(clerkTimeSlotId).orElseThrow(
                SingleRelationIsNotSetException::new
        );
        return RequestConverter.toDto(request);
    }

    @PutMapping("/v1/clerk_time_slots/{clerkTimeSlotId}/requests/{requestId}")
    public void updateClerkTimeSlotIsAssignedToRequestRelation(@PathVariable Long clerkTimeSlotId,
                                                               @PathVariable Long requestId) {
        clerkTimeSlotService.updateClerkTimeSlotIsAssignedToRequestRelation(clerkTimeSlotId, requestId);
    }

    @DeleteMapping("/v1/clerk_time_slots/{clerkTimeSlotId}/requests/{requestId}")
    public void deleteClerkTimeSlotIsAssignedToRequestRelation(@PathVariable Long clerkTimeSlotId,
                                                               @PathVariable Long requestId) {
        clerkTimeSlotService.deleteClerkTimeSlotIsAssignedToRequestRelation(clerkTimeSlotId, requestId);
    }

}
