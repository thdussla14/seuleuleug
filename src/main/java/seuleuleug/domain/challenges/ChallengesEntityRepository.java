package seuleuleug.domain.challenges;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengesEntityRepository  extends JpaRepository< ChallengesEntity , Integer > {
}
