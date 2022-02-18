package cz.cvut.fit.tjv.sem_work.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.sem_work.api.converter.ClerkConverter;
import cz.cvut.fit.tjv.sem_work.api.converter.ClerkTimeSlotConverter;
import cz.cvut.fit.tjv.sem_work.api.converter.OrganizationConverter;
import cz.cvut.fit.tjv.sem_work.api.converter.RequestConverter;
import cz.cvut.fit.tjv.sem_work.api.dto.ClerkDto;
import cz.cvut.fit.tjv.sem_work.api.dto.ClerkTimeSlotDto;
import cz.cvut.fit.tjv.sem_work.api.dto.OrganizationDto;
import cz.cvut.fit.tjv.sem_work.api.dto.RequestDto;
import cz.cvut.fit.tjv.sem_work.api.exception.*;
import cz.cvut.fit.tjv.sem_work.api.views.ClerkTimeSlotViews;
import cz.cvut.fit.tjv.sem_work.api.views.ClerkViews;
import cz.cvut.fit.tjv.sem_work.api.views.OrganizationViews;
import cz.cvut.fit.tjv.sem_work.api.views.RequestViews;
import cz.cvut.fit.tjv.sem_work.business.ClerkService;
import cz.cvut.fit.tjv.sem_work.domain.Clerk;
import cz.cvut.fit.tjv.sem_work.domain.Organization;
import cz.cvut.fit.tjv.sem_work.domain.Request;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ClerkController {
    private final ClerkService clerkService;

    public ClerkController(ClerkService clerkService) {
        this.clerkService = clerkService;
    }

    @PostMapping("/v1/clerks")
    public Long create(@JsonView(ClerkViews.FullDataWithoutId.class)
                       @RequestBody ClerkDto clerkDto) {
        Clerk clerk = ClerkConverter.fromDto(clerkDto);
        clerkService.create(clerk);
        if (clerk.getId() == null)
            throw new NoEntityFoundException();
        // There is no time-dependent error here, because POST is blocked
        clerk = clerkService.readById(clerk.getId()).orElseThrow(NoEntityFoundException::new);
        return clerk.getId();
    }

    @JsonView(ClerkViews.FullDataWithoutId.class)
    @GetMapping("/v1/clerks/{id}")
    public ClerkDto readById(@PathVariable Long id) {
        return ClerkConverter.toDto(clerkService.readById(id).orElseThrow(NoEntityFoundException::new));
    }

    @JsonView(ClerkViews.FullDataWithId.class)
    @GetMapping("/v1/clerks")
    public Collection<ClerkDto> readAll() {
        return ClerkConverter.toDtoMany(clerkService.readAll());
    }

    @PutMapping("/v1/clerks/{id}")
    public void updateById(@PathVariable Long id,
                           @JsonView(ClerkViews.FullDataWithoutId.class)
                           @RequestBody ClerkDto clerkDto) {
        Clerk clerkToUpdate = ClerkConverter.fromDto(clerkDto);
        clerkToUpdate.setId(id);
        clerkService.update(clerkToUpdate);
        // no checking in UPDATE or DELETE, because there are not blocked;
        // so checking could result in time-dependent error
    }

    @DeleteMapping("/v1/clerks/{id}")
    public void deleteById(@PathVariable Long id) {
        clerkService.deleteById(id);
        // no checking in UPDATE or DELETE, because there are not blocked;
        // so checking could result in time-dependent error
    }

    @JsonView(OrganizationViews.FullDataWithId.class)
    @GetMapping("/v1/clerks/{clerkId}/organizations")
    public OrganizationDto readOrganizationWhereClerkWorks(@PathVariable Long clerkId) {
        Organization organization = clerkService.readOrganizationWhereClerkWorks(clerkId).orElseThrow(
                SingleRelationIsNotSetException::new
        );
        return OrganizationConverter.toDto(organization);
    }

    @PutMapping("/v1/clerks/{clerkId}/organizations/{organizationId}")
    public void updateClerkWorksInOrganizationRelation(@PathVariable Long clerkId,
                                                       @PathVariable Long organizationId) {
        clerkService.updateClerkWorksInOrganizationRelation(clerkId, organizationId);
    }

    @DeleteMapping("/v1/clerks/{clerkId}/organizations/{organizationId}")
    public void deleteClerkWorksInOrganizationRelation(@PathVariable Long clerkId,
                                                       @PathVariable Long organizationId) {
        clerkService.deleteClerkWorksInOrganizationRelation(clerkId, organizationId);
    }

    @JsonView(RequestViews.FullDataWithId.class)
    @GetMapping("/v1/clerks/{clerkId}/requests")
    public Collection<RequestDto> readAllRequestsSolvableByClerk(@PathVariable Long clerkId) {
        return RequestConverter.toDtoMany(clerkService.readAllRequestsSolvableByClerk(clerkId));
    }

    @PutMapping("/v1/clerks/{clerkId}/requests/{requestId}")
    public void updateClerkCanSolveRequestRelation(@PathVariable Long clerkId,
                                                   @PathVariable Long requestId) {
        clerkService.updateClerkCanSolveRequestRelation(clerkId, requestId);
    }

    @DeleteMapping("/v1/clerks/{clerkId}/requests/{requestId}")
    public void deleteClerkCanSolveRequestRelation(@PathVariable Long clerkId,
                                                   @PathVariable Long requestId) {
        clerkService.deleteClerkCanSolveRequestRelation(clerkId, requestId);
    }

    /**
     * Gets all unassigned (no request assigned) time slots of a given clerk.
     */
    @JsonView(ClerkTimeSlotViews.FullDataWithId.class)
    @GetMapping("/v1/clerks/{clerkId}/unassigned_clerk_time_slots")
    public Collection<ClerkTimeSlotDto> readAllUnassignedTimeSlotsOfClerk(@PathVariable Long clerkId) {
        return ClerkTimeSlotConverter.toDtoMany(clerkService.readAllUnassignedTimeSlotsOfClerk(clerkId));
    }
}
