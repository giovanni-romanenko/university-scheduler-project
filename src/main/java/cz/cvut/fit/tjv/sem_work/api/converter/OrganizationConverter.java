package cz.cvut.fit.tjv.sem_work.api.converter;

import cz.cvut.fit.tjv.sem_work.api.dto.OrganizationDto;
import cz.cvut.fit.tjv.sem_work.domain.Organization;

import java.util.ArrayList;
import java.util.Collection;

public class OrganizationConverter {
    public static Organization fromDto(OrganizationDto dto) {
        return new Organization(dto.getId(), dto.getName());
    }

    public static OrganizationDto toDto(Organization entity) {
        OrganizationDto dto = new OrganizationDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public static Collection<OrganizationDto> toDtoMany(Collection<Organization> entities) {
        Collection<OrganizationDto> dtos = new ArrayList<>();
        entities.forEach((u) -> dtos.add(toDto(u)));
        return dtos;
    }
}
