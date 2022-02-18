package cz.cvut.fit.tjv.sem_work.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.sem_work.api.converter.OrganizationConverter;
import cz.cvut.fit.tjv.sem_work.api.dto.OrganizationDto;
import cz.cvut.fit.tjv.sem_work.api.exception.NoEntityFoundException;
import cz.cvut.fit.tjv.sem_work.api.views.OrganizationViews;
import cz.cvut.fit.tjv.sem_work.business.OrganizationService;
import cz.cvut.fit.tjv.sem_work.domain.Organization;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("/v1/organizations")
    public Long create(@JsonView(OrganizationViews.FullDataWithoutId.class)
                       @RequestBody OrganizationDto organizationDto){
        Organization organization = OrganizationConverter.fromDto(organizationDto);
        organizationService.create(organization);
        if (organization.getId() == null)
            throw new NoEntityFoundException();
        organization = organizationService.readById(organization.getId()).orElseThrow(NoEntityFoundException::new);
        return organization.getId();
    }

    @JsonView(OrganizationViews.FullDataWithoutId.class)
    @GetMapping("/v1/organizations/{id}")
    public OrganizationDto readById(@PathVariable Long id) {
        return OrganizationConverter.toDto(organizationService.readById(id).orElseThrow(NoEntityFoundException::new));
    }

    @JsonView(OrganizationViews.FullDataWithId.class)
    @GetMapping("/v1/organizations")
    public Collection<OrganizationDto> readAll() {
        return OrganizationConverter.toDtoMany(organizationService.readAll());
    }

    @PutMapping("/v1/organizations/{id}")
    public void updateById(@PathVariable Long id,
                           @JsonView(OrganizationViews.FullDataWithoutId.class)
                           @RequestBody OrganizationDto organizationDto) {
        Organization organizationToUpdate = OrganizationConverter.fromDto(organizationDto);
        organizationToUpdate.setId(id);
        organizationService.update(organizationToUpdate);
    }

    @DeleteMapping("/v1/organizations/{id}")
    public void deleteById(@PathVariable Long id) {
        organizationService.deleteById(id);
    }

}
