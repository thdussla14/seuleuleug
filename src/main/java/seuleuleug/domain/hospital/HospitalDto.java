package seuleuleug.domain.hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class HospitalDto {

    private  int hno;
    private String hname;
    private String hnum;
    private String haddr;
    private String hurl;
    private int halliance;
    // 병원 의사 목록
    List<HMemberDto> list ;

    public HospitalEntity toEntity(){

        return HospitalEntity.builder()
                .hno(this.hno)
                .hname(this.hname)
                .hnum(this.hnum)
                .haddr(this.haddr)
                .hurl(this.hurl)
                .halliance(this.halliance)
                .build();
    }


}
