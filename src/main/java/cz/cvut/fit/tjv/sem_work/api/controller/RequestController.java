package cz.cvut.fit.tjv.sem_work.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.sem_work.api.converter.OrganizationConverter;
import cz.cvut.fit.tjv.sem_work.api.converter.RequestConverter;
import cz.cvut.fit.tjv.sem_work.api.converter.RequestStateConverter;
import cz.cvut.fit.tjv.sem_work.api.converter.RichManConverter;
import cz.cvut.fit.tjv.sem_work.api.dto.OrganizationDto;
import cz.cvut.fit.tjv.sem_work.api.dto.RequestDto;
import cz.cvut.fit.tjv.sem_work.api.dto.RequestStateDto;
import cz.cvut.fit.tjv.sem_work.api.dto.RichManDto;
import cz.cvut.fit.tjv.sem_work.api.exception.NoEntityFoundException;
import cz.cvut.fit.tjv.sem_work.api.exception.SingleRelationIsNotSetException;
import cz.cvut.fit.tjv.sem_work.api.views.OrganizationViews;
import cz.cvut.fit.tjv.sem_work.api.views.RequestStateViews;
import cz.cvut.fit.tjv.sem_work.api.views.RequestViews;
import cz.cvut.fit.tjv.sem_work.api.views.RichManViews;
import cz.cvut.fit.tjv.sem_work.business.RequestService;
import cz.cvut.fit.tjv.sem_work.domain.Organization;
import cz.cvut.fit.tjv.sem_work.domain.Request;
import cz.cvut.fit.tjv.sem_work.domain.RequestState;
import cz.cvut.fit.tjv.sem_work.domain.RichMan;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class RequestController {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/v1/requests")
    public Long create(@JsonView(RequestViews.FullDataWithoutId.class)
                       @RequestBody RequestDto requestDto){
        Request request = RequestConverter.fromDto(requestDto);
        requestService.create(request);
        if (request.getId() == null)
            throw new NoEntityFoundException();
        request = requestService.readById(request.getId()).orElseThrow(NoEntityFoundException::new);
        return request.getId();
    }

    @JsonView(RequestViews.FullDataWithoutId.class)
    @GetMapping("/v1/requests/{id}")
    public RequestDto readById(@PathVariable Long id) {
        return RequestConverter.toDto(requestService.readById(id).orElseThrow(NoEntityFoundException::new));
    }

    @JsonView(RequestViews.FullDataWithId.class)
    @GetMapping("/v1/requests")
    public Collection<RequestDto> readAll() {
        return RequestConverter.toDtoMany(requestService.readAll());
    }

    @PutMapping("/v1/requests/{id}")
    public void updateById(@PathVariable Long id,
                           @JsonView(RequestViews.FullDataWithoutId.class)
                           @RequestBody RequestDto requestDto) {
        Request requestToUpdate = RequestConverter.fromDto(requestDto);
        requestToUpdate.setId(id);
        requestService.update(requestToUpdate);
    }

    @DeleteMapping("/v1/requests/{id}")
    public void deleteById(@PathVariable Long id) {
        requestService.deleteById(id);
    }

    @JsonView(RichManViews.FullDataWithId.class)
    @GetMapping("/v1/requests/{requestId}/rich_men")
    public RichManDto readRichManGivenRequest(@PathVariable Long requestId) {
        RichMan richMan = requestService.readRichManGivenRequest(requestId).orElseThrow(
                SingleRelationIsNotSetException::new
        );
        return RichManConverter.toDto(richMan);
    }

    @PutMapping("/v1/requests/{requestId}/rich_men/{richManId}")
    public void updateRequestIsDemandingRichManRelation(@PathVariable Long requestId,
                                                        @PathVariable Long richManId) {
        requestService.updateRequestIsDemandingRichManRelation(requestId, richManId);
    }

    @DeleteMapping("/v1/requests/{requestId}/rich_men/{richManId}")
    public void deleteRequestIsDemandingRichManRelation(@PathVariable Long requestId,
                                                        @PathVariable Long richManId) {
        requestService.deleteRequestIsDemandingRichManRelation(requestId, richManId);
    }

    @JsonView(RequestStateViews.FullDataWithId.class)
    @GetMapping("/v1/requests/{requestId}/request_states")
    public RequestStateDto readStateOfRequest(@PathVariable Long requestId) {
        RequestState requestState = requestService.readStateOfRequest(requestId).orElseThrow(
                SingleRelationIsNotSetException::new
        );
        return RequestStateConverter.toDto(requestState);
    }

    @PutMapping("/v1/requests/{requestId}/request_states/{requestStateId}")
    public void updateRequestIsInRequestStateRelation(@PathVariable Long requestId,
                                                      @PathVariable Long requestStateId) {
        requestService.updateRequestIsInRequestStateRelation(requestId, requestStateId);
    }

    @DeleteMapping("/v1/requests/{requestId}/request_states/{requestStateId}")
    public void deleteRequestIsInRequestStateRelation(@PathVariable Long requestId,
                                                      @PathVariable Long requestStateId) {
        requestService.deleteRequestIsInRequestStateRelation(requestId, requestStateId);
    }

    @JsonView(OrganizationViews.FullDataWithId.class)
    @GetMapping("/v1/requests/{requestId}/organizations")
    public OrganizationDto readOrganizationWhichCreatedRequest(@PathVariable Long requestId) {
        Organization organization = requestService.readOrganizationWhichCreatedRequest(requestId).orElseThrow(
                SingleRelationIsNotSetException::new
        );
        return OrganizationConverter.toDto(organization);
    }

    @PutMapping("/v1/requests/{requestId}/organizations/{organizationId}")
    public void updateRequestWasCreatedByOrganizationRelation(@PathVariable Long requestId,
                                                              @PathVariable Long organizationId) {
        requestService.updateRequestWasCreatedByOrganizationRelation(requestId, organizationId);
    }

    @DeleteMapping("/v1/requests/{requestId}/organizations/{organizationId}")
    public void deleteRequestWasCreatedByOrganizationRelation(@PathVariable Long requestId,
                                                              @PathVariable Long organizationId) {
        requestService.deleteRequestWasCreatedByOrganizationRelation(requestId, organizationId);
    }

}
