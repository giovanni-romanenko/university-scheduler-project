package cz.cvut.fit.tjv.sem_work.api.dto;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.sem_work.api.views.RequestStateViews;

import java.util.Objects;

public class RequestStateDto {

    @JsonView(RequestStateViews.FullDataWithId.class)
    private Long id;

    @JsonView(RequestStateViews.FullDataWithoutId.class)
    private String name;

    public RequestStateDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestStateDto that = (RequestStateDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
