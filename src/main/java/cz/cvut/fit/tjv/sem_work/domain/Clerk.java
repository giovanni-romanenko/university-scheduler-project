package cz.cvut.fit.tjv.sem_work.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Clerk implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String secondName;
    @Column(nullable = true)
    private String essayAboutLifeGoals;
    @Column(nullable = true)
    private Long moodInIntegerEquivalent;

    @ManyToOne
    @JoinColumn(name = "organization_clerk_id", nullable = true)
    private Organization jobOrganization;
    @OneToMany(mappedBy = "clerk")
    private Set<ClerkTimeSlot> timeSlotsForRequests = new HashSet<>();
    @ManyToMany(mappedBy = "possibleClerks")
    private Set<Request> solvableRequests = new HashSet<>();

    public Clerk() {
    }

    public Clerk(Long id, String firstName, String secondName, String essayAboutLifeGoals, Long moodInIntegerEquivalent) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.essayAboutLifeGoals = essayAboutLifeGoals;
        this.moodInIntegerEquivalent = moodInIntegerEquivalent;
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

    public Organization getJobOrganization() {
        return jobOrganization;
    }

    public void setJobOrganization(Organization jobOrganization) {
        this.jobOrganization = jobOrganization;
    }

    public Set<ClerkTimeSlot> getTimeSlotsForRequests() {
        return timeSlotsForRequests;
    }

    public void setTimeSlotsForRequests(Set<ClerkTimeSlot> timeSlotsForRequests) {
        this.timeSlotsForRequests = timeSlotsForRequests;
    }

    public Set<Request> getSolvableRequests() {
        return solvableRequests;
    }

    public void setSolvableRequests(Set<Request> solvableRequests) {
        this.solvableRequests = solvableRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clerk clerk = (Clerk) o;
        return getId().equals(clerk.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Clerk{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", essayAboutLifeGoals='" + essayAboutLifeGoals + '\'' +
                ", moodInIntegerEquivalent=" + moodInIntegerEquivalent +
                '}';
    }
}
