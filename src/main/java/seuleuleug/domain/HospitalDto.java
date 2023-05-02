package seuleuleug.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class HospitalDto {
    private int hno;       //병원 번호
    private String hname;      //병원 이름
    private String hcertification;    //병원 사업자등록증

    public HospitalEntity toEntity(){
        return HospitalEntity.builder()
                .hno(this.hno)
                .hname(this.hname)
                .hcertification(this.hcertification)
                .build();
    }


}
