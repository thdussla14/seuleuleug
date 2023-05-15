package seuleuleug.domain.hospital;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HMemberRepository extends JpaRepository<HMemberEntity , Integer> {
    @Query(value = "select * from hmember where hmemail = :hmemail and hpassword = :hpassword", nativeQuery = true)
    Optional<HMemberEntity> findByHmemailAndHpassword(String hmemail, String hpassword);
    @Query(value = "select * from hmember where hmemail = :hmemail", nativeQuery = true)
    Optional<HMemberEntity> findByHmemail(String hmemail);
}
