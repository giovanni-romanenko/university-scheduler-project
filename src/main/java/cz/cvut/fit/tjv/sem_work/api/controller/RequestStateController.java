package cz.cvut.fit.tjv.sem_work.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.sem_work.api.converter.RequestStateConverter;
import cz.cvut.fit.tjv.sem_work.api.dto.RequestStateDto;
import cz.cvut.fit.tjv.sem_work.api.exception.NoEntityFoundException;
import cz.cvut.fit.tjv.sem_work.api.views.RequestStateViews;
import cz.cvut.fit.tjv.sem_work.business.RequestStateService;
import cz.cvut.fit.tjv.sem_work.domain.RequestState;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class RequestStateController {
    private final RequestStateService requestStateService;

    public RequestStateController(RequestStateService requestStateService) {
        this.requestStateService = requestStateService;
    }

    @PostMapping("/v1/request_states")
    public Long create(@JsonView(RequestStateViews.FullDataWithoutId.class)
                       @RequestBody RequestStateDto requestStateDto){
        RequestState requestState = RequestStateConverter.fromDto(requestStateDto);
        requestStateService.create(requestState);
        if (requestState.getId() == null)
            throw new NoEntityFoundException();
        requestState = requestStateService.readById(requestState.getId()).orElseThrow(NoEntityFoundException::new);
        return requestState.getId();
    }

    @JsonView(RequestStateViews.FullDataWithoutId.class)
    @GetMapping("/v1/request_states/{id}")
    public RequestStateDto readById(@PathVariable Long id) {
        return RequestStateConverter.toDto(requestStateService.readById(id).orElseThrow(NoEntityFoundException::new));
    }

    @JsonView(RequestStateViews.FullDataWithId.class)
    @GetMapping("/v1/request_states")
    public Collection<RequestStateDto> readAll() {
        return RequestStateConverter.toDtoMany(requestStateService.readAll());
    }

    @PutMapping("/v1/request_states/{id}")
    public void updateById(@PathVariable Long id,
                           @JsonView(RequestStateViews.FullDataWithoutId.class)
                           @RequestBody RequestStateDto requestStateDto) {
        RequestState requestStateToUpdate = RequestStateConverter.fromDto(requestStateDto);
        requestStateToUpdate.setId(id);
        requestStateService.update(requestStateToUpdate);
    }

    @DeleteMapping("/v1/request_states/{id}")
    public void deleteById(@PathVariable Long id) {
        requestStateService.deleteById(id);
    }

}
