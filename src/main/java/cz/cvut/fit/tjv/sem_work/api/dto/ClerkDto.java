package cz.cvut.fit.tjv.sem_work.api.dto;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.sem_work.api.views.ClerkViews;

import java.util.Objects;

public class ClerkDto {

    @JsonView(ClerkViews.FullDataWithId.class)
    private Long id;

    @JsonView(ClerkViews.FullDataWithoutId.class)
    private String firstName;

    @JsonView(ClerkViews.FullDataWithoutId.class)
    private String secondName;

    @JsonView(ClerkViews.FullDataWithoutId.class)
    private String essayAboutLifeGoals;

    @JsonView(ClerkViews.FullDataWithoutId.class)
    private Long moodInIntegerEquivalent;

    public ClerkDto() {
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

    public String getEssayAboutLifeGoals() {
        return essayAboutLifeGoals;
    }

    public void setEssayAboutLifeGoals(String essayAboutLifeGoals) {
        this.essayAboutLifeGoals = essayAboutLifeGoals;
    }

    public Long getMoodInIntegerEquivalent() {
        return moodInIntegerEquivalent;
    }

    public void setMoodInIntegerEquivalent(Long moodInIntegerEquivalent) {
        this.moodInIntegerEquivalent = moodInIntegerEquivalent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClerkDto clerkDto = (ClerkDto) o;
        return Objects.equals(id, clerkDto.id) && Objects.equals(firstName, clerkDto.firstName) && Objects.equals(secondName, clerkDto.secondName) && Objects.equals(essayAboutLifeGoals, clerkDto.essayAboutLifeGoals) && Objects.equals(moodInIntegerEquivalent, clerkDto.moodInIntegerEquivalent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, essayAboutLifeGoals, moodInIntegerEquivalent);
    }
}
