package cz.cvut.fit.tjv.sem_work.dao;

import cz.cvut.fit.tjv.sem_work.domain.Clerk;
import cz.cvut.fit.tjv.sem_work.domain.ClerkTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClerkJpaRepository extends JpaRepository<Clerk, Long> {
    @Query("SELECT timeSlot FROM ClerkTimeSlot timeSlot WHERE timeSlot.clerk = :clerkId AND timeSlot.request IS NULL")
    List<ClerkTimeSlot> findAllUnassignedTimeSlotsOfClerk(Long clerkId);
}
