package cz.cvut.fit.tjv.sem_work.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.sem_work.api.converter.RichManConverter;
import cz.cvut.fit.tjv.sem_work.api.dto.RichManDto;
import cz.cvut.fit.tjv.sem_work.api.exception.NoEntityFoundException;
import cz.cvut.fit.tjv.sem_work.api.views.RichManViews;
import cz.cvut.fit.tjv.sem_work.business.RichManService;
import cz.cvut.fit.tjv.sem_work.domain.RichMan;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class RichManController {
    private final RichManService richManService;

    public RichManController(RichManService richManService) {
        this.richManService = richManService;
    }

    @PostMapping("/v1/rich_men")
    public Long create(@JsonView(RichManViews.FullDataWithoutId.class)
                       @RequestBody RichManDto richManDto){
        RichMan richMan = RichManConverter.fromDto(richManDto);
        richManService.create(richMan);
        if (richMan.getId() == null)
            throw new NoEntityFoundException();
        richMan = richManService.readById(richMan.getId()).orElseThrow(NoEntityFoundException::new);
        return richMan.getId();
    }

    @JsonView(RichManViews.FullDataWithoutId.class)
    @GetMapping("/v1/rich_men/{id}")
    public RichManDto readById(@PathVariable Long id) {
        return RichManConverter.toDto(richManService.readById(id).orElseThrow(NoEntityFoundException::new));
    }

    @JsonView(RichManViews.FullDataWithId.class)
    @GetMapping("/v1/rich_men")
    public Collection<RichManDto> readAll() {
        return RichManConverter.toDtoMany(richManService.readAll());
    }

    @PutMapping("/v1/rich_men/{id}")
    public void updateById(@PathVariable Long id,
                           @JsonView(RichManViews.FullDataWithoutId.class)
                           @RequestBody RichManDto richManDto) {
        RichMan richManToUpdate = RichManConverter.fromDto(richManDto);
        richManToUpdate.setId(id);
        richManService.update(richManToUpdate);
    }

    @DeleteMapping("/v1/rich_men/{id}")
    public void deleteById(@PathVariable Long id) {
        richManService.deleteById(id);
    }

}
