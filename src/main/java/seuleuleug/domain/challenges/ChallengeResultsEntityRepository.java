package seuleuleug.domain.challenges;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChallengeResultsEntityRepository extends JpaRepository< ChallengeResultsEntity , Integer > {
    @Query( value = "select * from challenges_results where chno = :chno" , nativeQuery = true )
    List<ChallengeResultsEntity> findByChno(int chno);
}
