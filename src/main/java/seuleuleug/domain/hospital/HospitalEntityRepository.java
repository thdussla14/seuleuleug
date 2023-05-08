package seuleuleug.domain.hospital;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HospitalEntityRepository extends JpaRepository<HospitalEntity, Integer> {
    @Query( value = "select * from Hospital where if( :key = '' , true , if( :key = 'hname' , hname like %:keyword% , if( :key = 'hnum' , hnum like %:keyword% , haddr like %:keyword% ) ) )" , nativeQuery = true)
    Page<HospitalEntity> findBySearch(String key , String keyword , Pageable pageable);
}
