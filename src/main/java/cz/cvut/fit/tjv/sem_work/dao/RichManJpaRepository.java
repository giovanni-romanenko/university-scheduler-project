package cz.cvut.fit.tjv.sem_work.dao;

import cz.cvut.fit.tjv.sem_work.domain.RichMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RichManJpaRepository extends JpaRepository<RichMan, Long> {

}
