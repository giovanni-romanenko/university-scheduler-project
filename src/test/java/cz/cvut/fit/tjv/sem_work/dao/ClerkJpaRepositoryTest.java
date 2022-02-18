package cz.cvut.fit.tjv.sem_work.dao;

import cz.cvut.fit.tjv.sem_work.domain.Clerk;
import cz.cvut.fit.tjv.sem_work.domain.ClerkTimeSlot;
import cz.cvut.fit.tjv.sem_work.domain.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClerkJpaRepositoryTest {
    @Autowired
    private ClerkJpaRepository repository;

    @Autowired
    private ClerkTimeSlotJpaRepository clerkTimeSlotJpaRepository;

    @Autowired
    private RequestJpaRepository requestJpaRepository;

    @Test
    void findAllUnassignedTimeSlotsOfClerk() {
//        Clerk clerk1 = new Clerk(1L, "TestFN", "TestSN", null, null);
//        Clerk clerk2 = new Clerk(2L, "TestFN2", "TestSN2", null, null);
//        Clerk clerk3 = new Clerk(3L, "TestFN3", "TestSN3", null, null);
//        ClerkTimeSlot clerkTimeSlot1 = new ClerkTimeSlot(1L, LocalDateTime.now(), LocalDateTime.now());
//        ClerkTimeSlot clerkTimeSlot2 = new ClerkTimeSlot(2L, LocalDateTime.now(), LocalDateTime.now());
//        ClerkTimeSlot clerkTimeSlot3 = new ClerkTimeSlot(3L, LocalDateTime.now(), LocalDateTime.now());
//        ClerkTimeSlot clerkTimeSlot4 = new ClerkTimeSlot(4L, LocalDateTime.now(), LocalDateTime.now());
//        ClerkTimeSlot clerkTimeSlot5 = new ClerkTimeSlot(5L, LocalDateTime.now(), LocalDateTime.now());
//        ClerkTimeSlot clerkTimeSlot6 = new ClerkTimeSlot(6L, LocalDateTime.now(), LocalDateTime.now());
//        Request request1 = new Request(1L, LocalDateTime.now(), 10L);
//        Request request2 = new Request(2L, LocalDateTime.now(), 20L);
//        Request request3 = new Request(3L, LocalDateTime.now(), 30L);
//        Request request4 = new Request(4L, LocalDateTime.now(), 40L);
//        clerkTimeSlot1.setClerk(clerk1);
//        clerkTimeSlot2.setClerk(clerk1);
//        clerkTimeSlot3.setClerk(clerk1);
//        clerkTimeSlot4.setClerk(clerk2);
//        clerkTimeSlot5.setClerk(clerk2);
//        clerkTimeSlot6.setClerk(clerk3);
//        request1.setAppointedTimeSlot(clerkTimeSlot1);
//        request2.setAppointedTimeSlot(clerkTimeSlot3);
//        request3.setAppointedTimeSlot(clerkTimeSlot4);
//        request4.setAppointedTimeSlot(clerkTimeSlot6);
//        repository.save(clerk1);
//        repository.save(clerk2);
//        repository.save(clerk3);
//        clerkTimeSlotJpaRepository.save(clerkTimeSlot1);
//        clerkTimeSlotJpaRepository.save(clerkTimeSlot2);
//        clerkTimeSlotJpaRepository.save(clerkTimeSlot3);
//        clerkTimeSlotJpaRepository.save(clerkTimeSlot4);
//        clerkTimeSlotJpaRepository.save(clerkTimeSlot5);
//        clerkTimeSlotJpaRepository.save(clerkTimeSlot6);
//        requestJpaRepository.save(request1);
//        requestJpaRepository.save(request2);
//        requestJpaRepository.save(request3);
//        requestJpaRepository.save(request4);
//
//        var ret1 = repository.findAllUnassignedTimeSlotsOfClerk(clerk1.getId());
//        Assertions.assertTrue(ret1.contains(clerkTimeSlot2));
//        Assertions.assertEquals(1, ret1.size());
//        var ret2 = repository.findAllUnassignedTimeSlotsOfClerk(clerk2.getId());
//        Assertions.assertTrue(ret2.contains(clerkTimeSlot5));
//        Assertions.assertEquals(1, ret2.size());
//        var ret3 = repository.findAllUnassignedTimeSlotsOfClerk(clerk3.getId());
//        Assertions.assertEquals(0, ret3.size());
    }
}