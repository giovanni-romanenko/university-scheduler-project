package cz.cvut.fit.tjv.sem_work.api.dto;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.sem_work.api.views.RequestViews;

import java.time.LocalDateTime;
import java.util.Objects;

public class RequestDto {

    @JsonView(RequestViews.FullDataWithId.class)
    private Long id;

    @JsonView(RequestViews.FullDataWithoutId.class)
    private LocalDateTime deadline;

    @JsonView(RequestViews.FullDataWithoutId.class)
    private Long deadlineFee;

    public RequestDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getDeadlineFee() {
        return deadlineFee;
    }

    public void setDeadlineFee(Long deadlineFee) {
        this.deadlineFee = deadlineFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestDto that = (RequestDto) o;
        return Objects.equals(id, that.id) && Objects.equals(deadline, that.deadline) && Objects.equals(deadlineFee, that.deadlineFee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deadline, deadlineFee);
    }
}
