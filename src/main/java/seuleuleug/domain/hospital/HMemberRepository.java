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

    // 아이디찾기 [ 이름 과 전화번호 ]
    Optional<HMemberEntity> findByHmnameAndHmphone( String hmname , String hmphone );
    // 비밀번호찾기 [ 아이디 와 전화번호 ]
    boolean existsByHmemailAndHmphone( String hmemail , String hmphone );

}
