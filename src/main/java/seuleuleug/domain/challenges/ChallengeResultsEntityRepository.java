package seuleuleug.domain.challenges;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChallengeResultsEntityRepository extends JpaRepository< ChallengeResultsEntity , Integer > {
    @Query( value = "select * from challenges_results where chno = :chno and cdate like :datenow% " , nativeQuery = true )
    List<ChallengeResultsEntity> findByChno(int chno , String datenow);

    @Query( value = "select * from challenges_results where chno = :chno and mno = :mno order by cdate desc " , nativeQuery = true )
    List<ChallengeResultsEntity> findByMno(int chno , int mno);

    @Query( value = "select * from challenges_results where chno = :chno order by sstate asc, cdate desc " , nativeQuery = true )
    List<ChallengeResultsEntity> findByChnoAndState(int chno);

    /*@Query( value = "select count(distinct mno) as mno , chno from challenges_results group by chno " , nativeQuery = true )
    List<ChallengesEntity> findByCount();*/

    @Query( value = "select count(distinct mno) as count , chno from challenges_results where chno=:chno " , nativeQuery = true )
    List<ChallengeCount> findByCount(int chno);
}
