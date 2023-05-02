package seuleuleug.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengesEntityRepository  extends JpaRepository< ChallengesEntity , Integer > {
}
