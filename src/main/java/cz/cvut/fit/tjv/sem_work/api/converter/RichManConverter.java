package cz.cvut.fit.tjv.sem_work.api.converter;

import cz.cvut.fit.tjv.sem_work.api.dto.RichManDto;
import cz.cvut.fit.tjv.sem_work.domain.RichMan;

import java.util.ArrayList;
import java.util.Collection;

public class RichManConverter {
    public static RichMan fromDto(RichManDto dto) {
        return new RichMan(dto.getId(), dto.getFirstName(), dto.getSecondName());
    }

    public static RichManDto toDto(RichMan entity) {
        RichManDto dto = new RichManDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setSecondName(entity.getSecondName());
        return dto;
    }

    public static Collection<RichManDto> toDtoMany(Collection<RichMan> entities) {
        Collection<RichManDto> dtos = new ArrayList<>();
        entities.forEach((u) -> dtos.add(toDto(u)));
        return dtos;
    }
}
