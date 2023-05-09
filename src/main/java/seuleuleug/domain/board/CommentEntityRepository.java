package seuleuleug.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {
    @Query(
            value ="select * from comment where bno = :bno  ",
            nativeQuery = true
    )
    List<CommentEntity> findByBno(int bno);

}
