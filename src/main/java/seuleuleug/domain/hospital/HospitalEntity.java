package seuleuleug.domain.hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Entity
@Table(name = "hospital")
public class HospitalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hno;                                           // 병원 번호
    @Column(nullable = false) private String hname;            // 병원 이름
    @Column private String hnum;                               // 병원 전화번호
    @Column private String haddr;                              // 병원 주소
    @Column private String hurl;                               // 병원 홈페이지
    @ColumnDefault("0") @Column private int halliance;         // 제휴 여부( 0 : 제휴x , 1 : 제휴o )

    public HospitalDto toDto(){
        return HospitalDto.builder()
                .hno(this.hno)
                .hname(this.hname)
                .hnum(this.hnum)
                .haddr(this.haddr)
                .hurl(this.hurl)
                .halliance(this.halliance)
                .build();

    }


}
