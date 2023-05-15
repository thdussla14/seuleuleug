package seuleuleug.domain.challenges;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChallengeResultsEntityRepository extends JpaRepository< ChallengeResultsEntity , Integer > {
    @Query( value = "select * from challenges_results where chno = :chno and cdate like :datenow% " , nativeQuery = true )
    List<ChallengeResultsEntity> findByChno(int chno , String datenow);

    @Query( value = "select * from challenges_results where chno = :chno and mno = :mno order by cdate " , nativeQuery = true )
    List<ChallengeResultsEntity> findByMno(int chno , int mno);

    @Query( value = "select * from challenges_results where chno = :chno order by sstate " , nativeQuery = true )
    List<ChallengeResultsEntity> findByChnoAndState(int chno);
}
