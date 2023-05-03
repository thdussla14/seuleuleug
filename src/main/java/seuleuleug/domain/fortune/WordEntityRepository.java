package seuleuleug.domain.fortune;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordEntityRepository extends JpaRepository< WordEntity,Integer > {
}
