package cz.cvut.fit.tjv.sem_work.api.dto;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.sem_work.api.views.ClerkTimeSlotViews;

import java.time.LocalDateTime;
import java.util.Objects;

public class ClerkTimeSlotDto {

    @JsonView(ClerkTimeSlotViews.FullDataWithId.class)
    private Long id;

    @JsonView(ClerkTimeSlotViews.FullDataWithoutId.class)
    private LocalDateTime startTime;

    @JsonView(ClerkTimeSlotViews.FullDataWithoutId.class)
    private LocalDateTime endTime;

    public ClerkTimeSlotDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClerkTimeSlotDto that = (ClerkTimeSlotDto) o;
        return Objects.equals(id, that.id) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, endTime);
    }
}
