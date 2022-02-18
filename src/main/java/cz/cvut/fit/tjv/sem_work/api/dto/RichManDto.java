package cz.cvut.fit.tjv.sem_work.api.dto;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.sem_work.api.views.RichManViews;

import java.util.Objects;

public class RichManDto {

    @JsonView(RichManViews.FullDataWithId.class)
    private Long id;

    @JsonView(RichManViews.FullDataWithoutId.class)
    private String firstName;

    @JsonView(RichManViews.FullDataWithoutId.class)
    private String secondName;

    public RichManDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RichManDto that = (RichManDto) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(secondName, that.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName);
    }
}
