package cz.cvut.fit.tjv.sem_work.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Organization implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "jobOrganization")
    private Set<Clerk> clerks = new HashSet<>();
    @OneToMany(mappedBy = "organizationCreator")
    private Set<Request> createdRequests = new HashSet<>();

    public Organization() {
    }

    public Organization(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public Set<Clerk> getClerks() {
        return clerks;
    }

    public void setClerks(Set<Clerk> clerks) {
        this.clerks = clerks;
    }

    public Set<Request> getCreatedRequests() {
        return createdRequests;
    }

    public void setCreatedRequests(Set<Request> createdRequests) {
        this.createdRequests = createdRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
