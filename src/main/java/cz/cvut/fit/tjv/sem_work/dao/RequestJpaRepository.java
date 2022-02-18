package cz.cvut.fit.tjv.sem_work.dao;

import cz.cvut.fit.tjv.sem_work.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestJpaRepository extends JpaRepository<Request, Long> {

}
