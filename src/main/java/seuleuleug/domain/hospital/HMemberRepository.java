package seuleuleug.domain.hospital;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HMemberRepository extends JpaRepository<HMemberEntity , Integer> {
    Optional<HMemberEntity> findByHmemailAndHpassword(String hmemail, String hpassword);
    Optional<HMemberEntity> findByHmemail(String hmemail);
}
