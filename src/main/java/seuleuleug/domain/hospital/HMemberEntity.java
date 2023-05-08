package seuleuleug.domain.hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Table(name="HMember")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class HMemberEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int hmno;               // 회원 번호
    @Column( nullable = false ) private String hmemail;             // 의사 이메일
    @Column( nullable = false ) private String hpassword;           // 의사 비밀번호
    @Column( nullable = false ) private String hmname;              // 의사 이름
    @Column( nullable = false ) private String hmphone;             // 의사 전화번호
    @Column( nullable = false ) private String hmcertification;     // 의사 의사증명서 pdf 파일
    @Column( nullable = false ) private int hmstate;                // 의사 인증( 0 : 미승인 , 1 : 승인 )

    public HMemberDto toDto() {
        return HMemberDto.builder()
              .hmno( hmno )
              .hmemail( hmemail )
              .hpassword( hpassword )
              .hmname( hmname )
              .hmphone( hmphone )
              .hmcertification( hmcertification )
              .hmstate( hmstate )
              .build();
    }
}
