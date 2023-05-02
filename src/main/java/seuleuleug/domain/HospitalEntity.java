package seuleuleug.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Entity
@Table(name = "hospital")
public class HospitalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hno;                    //병원 번호
    @Column(nullable = false)
    private String hname;               //병원 이름
    @Column(nullable = false)
    private String hcertification;      //병원 사업자등록증

    public HospitalDto toDto(){
        return HospitalDto.builder()
                .hno(this.hno)
                .hname(this.hname)
                .hcertification(this.hcertification)
                .build();

    }


}
