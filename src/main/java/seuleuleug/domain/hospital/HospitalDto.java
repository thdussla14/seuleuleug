package seuleuleug.domain.hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class HospitalDto {
    private  int hno;
    private String hname;
    private String hnum;
    private String haddr;
    private String hurl;
    private String hcertification;

    public HospitalEntity toEntity(){
        return HospitalEntity.builder()
                .hno(this.hno)
                .hname(this.hname)
                .hnum(this.hnum)
                .haddr(this.haddr)
                .hurl(this.hurl)
                .hcertification(this.hcertification)
                .build();
    }


}
