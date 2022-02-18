package cz.cvut.fit.tjv.sem_work.dao;

import cz.cvut.fit.tjv.sem_work.domain.ClerkTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClerkTimeSlotJpaRepository extends JpaRepository<ClerkTimeSlot, Long> {

}
