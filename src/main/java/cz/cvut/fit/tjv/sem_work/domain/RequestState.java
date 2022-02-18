package cz.cvut.fit.tjv.sem_work.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class RequestState implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "stateOfRequest")
    private Set<Request> requestsInThisState = new HashSet<>();

    public RequestState() {
    }

    public RequestState(Long id, String name) {
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

    public Set<Request> getRequestsInThisState() {
        return requestsInThisState;
    }

    public void setRequestsInThisState(Set<Request> requestsInThisState) {
        this.requestsInThisState = requestsInThisState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestState that = (RequestState) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "RequestState{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
