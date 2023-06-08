package hu.gde.runnersdemo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeNameRepository extends JpaRepository<ShoeName, Long> {
}