package cz.cvut.fit.tjv.sem_work.api.converter;

import cz.cvut.fit.tjv.sem_work.api.dto.ClerkTimeSlotDto;
import cz.cvut.fit.tjv.sem_work.domain.ClerkTimeSlot;

import java.util.ArrayList;
import java.util.Collection;

public class ClerkTimeSlotConverter {
    public static ClerkTimeSlot fromDto(ClerkTimeSlotDto dto) {
        return new ClerkTimeSlot(dto.getId(), dto.getStartTime(), dto.getEndTime());
    }

    public static ClerkTimeSlotDto toDto(ClerkTimeSlot entity) {
        ClerkTimeSlotDto dto = new ClerkTimeSlotDto();
        dto.setId(entity.getId());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        return dto;
    }

    public static Collection<ClerkTimeSlotDto> toDtoMany(Collection<ClerkTimeSlot> entities) {
        Collection<ClerkTimeSlotDto> dtos = new ArrayList<>();
        entities.forEach((u) -> dtos.add(toDto(u)));
        return dtos;
    }
}
