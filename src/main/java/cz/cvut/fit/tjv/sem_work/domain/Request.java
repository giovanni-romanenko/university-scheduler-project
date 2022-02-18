package cz.cvut.fit.tjv.sem_work.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Request implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime deadline;
    @Column(nullable = false)
    private Long deadlineFee;

    @ManyToOne
    @JoinColumn(name = "rich_man_request_id", nullable = true)
    private RichMan requestedRichMan;
    @OneToOne(mappedBy = "request")
    private ClerkTimeSlot appointedTimeSlot;
    @ManyToOne
    @JoinColumn(name = "request_state_request_id", nullable = true)
    private RequestState stateOfRequest;
    @ManyToMany
    @JoinTable(
            name = "possible_clerk_request",
            joinColumns = @JoinColumn(name = "possible_clerk_id"),
            inverseJoinColumns = @JoinColumn(name = "request_id")
    )
    private Set<Clerk> possibleClerks = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "organization_request_id", nullable = true)
    private Organization organizationCreator;

    public Request() {
    }

    public Request(Long id, LocalDateTime deadline, Long deadlineFee) {
        this.id = id;
        this.deadline = deadline;
        this.deadlineFee = deadlineFee;
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

    public RichMan getRequestedRichMan() {
        return requestedRichMan;
    }

    public void setRequestedRichMan(RichMan requestedRichMan) {
        this.requestedRichMan = requestedRichMan;
    }

    public ClerkTimeSlot getAppointedTimeSlot() {
        return appointedTimeSlot;
    }

    public void setAppointedTimeSlot(ClerkTimeSlot appointedTimeSlot) {
        this.appointedTimeSlot = appointedTimeSlot;
    }

    public RequestState getStateOfRequest() {
        return stateOfRequest;
    }

    public void setStateOfRequest(RequestState stateOfRequest) {
        this.stateOfRequest = stateOfRequest;
    }

    public Set<Clerk> getPossibleClerks() {
        return possibleClerks;
    }

    public void setPossibleClerks(Set<Clerk> possibleClerks) {
        this.possibleClerks = possibleClerks;
    }

    public Organization getOrganizationCreator() {
        return organizationCreator;
    }

    public void setOrganizationCreator(Organization organizationCreator) {
        this.organizationCreator = organizationCreator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return getId().equals(request.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", deadline=" + deadline +
                ", deadlineFee=" + deadlineFee +
                '}';
    }
}
