package cz.cvut.fit.tjv.sem_work.api.converter;

import cz.cvut.fit.tjv.sem_work.api.dto.ClerkDto;
import cz.cvut.fit.tjv.sem_work.domain.Clerk;

import java.util.ArrayList;
import java.util.Collection;

public class ClerkConverter {
    public static Clerk fromDto(ClerkDto dto) {
        return new Clerk(dto.getId(), dto.getFirstName(), dto.getSecondName(),
                dto.getEssayAboutLifeGoals(), dto.getMoodInIntegerEquivalent());
    }

    public static ClerkDto toDto(Clerk entity) {
        ClerkDto dto = new ClerkDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setSecondName(entity.getSecondName());
        dto.setEssayAboutLifeGoals(entity.getEssayAboutLifeGoals());
        dto.setMoodInIntegerEquivalent(entity.getMoodInIntegerEquivalent());
        return dto;
    }

    public static Collection<ClerkDto> toDtoMany(Collection<Clerk> entities) {
        Collection<ClerkDto> dtos = new ArrayList<>();
        entities.forEach((u) -> dtos.add(ClerkConverter.toDto(u)));
        return dtos;
    }
}
