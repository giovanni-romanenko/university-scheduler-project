package cz.cvut.fit.tjv.sem_work.api.converter;

import cz.cvut.fit.tjv.sem_work.api.dto.RequestStateDto;
import cz.cvut.fit.tjv.sem_work.domain.RequestState;

import java.util.ArrayList;
import java.util.Collection;

public class RequestStateConverter {
    public static RequestState fromDto(RequestStateDto dto) {
        return new RequestState(dto.getId(), dto.getName());
    }

    public static RequestStateDto toDto(RequestState entity) {
        RequestStateDto dto = new RequestStateDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public static Collection<RequestStateDto> toDtoMany(Collection<RequestState> entities) {
        Collection<RequestStateDto> dtos = new ArrayList<>();
        entities.forEach((u) -> dtos.add(toDto(u)));
        return dtos;
    }
}
