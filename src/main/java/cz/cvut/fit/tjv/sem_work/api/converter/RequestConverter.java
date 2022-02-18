package cz.cvut.fit.tjv.sem_work.api.converter;

import cz.cvut.fit.tjv.sem_work.api.dto.RequestDto;
import cz.cvut.fit.tjv.sem_work.domain.Request;

import java.util.ArrayList;
import java.util.Collection;

public class RequestConverter {
    public static Request fromDto(RequestDto dto) {
        return new Request(dto.getId(), dto.getDeadline(), dto.getDeadlineFee());
    }

    public static RequestDto toDto(Request entity) {
        RequestDto dto = new RequestDto();
        dto.setId(entity.getId());
        dto.setDeadline(entity.getDeadline());
        dto.setDeadlineFee(entity.getDeadlineFee());
        return dto;
    }

    public static Collection<RequestDto> toDtoMany(Collection<Request> entities) {
        Collection<RequestDto> dtos = new ArrayList<>();
        entities.forEach((u) -> dtos.add(toDto(u)));
        return dtos;
    }
}
