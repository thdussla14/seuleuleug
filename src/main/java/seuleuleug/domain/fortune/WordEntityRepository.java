package seuleuleug.domain.fortune;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordEntityRepository extends JpaRepository< WordEntity,Integer > {

    @Query(value = "SELECT * FROM fortune order by RAND() limit 1",nativeQuery = true)
    Optional<WordEntity> findrandom();

}
