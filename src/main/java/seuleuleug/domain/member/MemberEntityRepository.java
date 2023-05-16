package seuleuleug.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberEntityRepository extends JpaRepository<MemberEntity, Integer> {
    Optional<MemberEntity> findByMemailAndMphone(String meamil, String mphone);
    Optional<MemberEntity> findByMemail(String meamil);

}
