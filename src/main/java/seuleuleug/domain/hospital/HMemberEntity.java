package seuleuleug.domain.hospital;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import seuleuleug.domain.board.CommentEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @Column( nullable = false ) private String hmpimg;              // 의사 프로필 img 파일
    @Column( nullable = false ) private String hmcertification;     // 의사 의사증명서 pdf 파일
    @Column( nullable = false ) private String hrole;               // 의사 등급
    @ColumnDefault("0")
    @Column( nullable = false ) private int    hmstate;             // 의사 인증 ( 0 : 미승인 , 1 : 승인 )

    //제휴병원 번호
    @ManyToOne
    @JoinColumn(name = "hno")
    @ToString.Exclude
    private HospitalEntity hospitalEntity;

    // 댓글목록
    @OneToMany(mappedBy = "hMemberEntity", cascade=CascadeType.ALL)
    @Builder.Default
    private List<CommentEntity> commentEntityList = new ArrayList<>();
    // 
    public HMemberDto toDto() {
        return HMemberDto.builder()
              .hmno( this.hmno )
              .hmemail( this.hmemail )
              .hpassword( this.hpassword )
              .hmname(this. hmname )
              .hmphone(this. hmphone )
              .hmpimg( this. hmpimg )
              .hmcertification( this. hmcertification )
              .hmstate(this. hmstate )
              .build();
    }
    // 답변 작성용 의사 정보 출력
    public HMemberDto toCommentDto() {
        return HMemberDto.builder()
                .hmno( this.hmno )
                .hmname(this. hmname )
                .hmpimg( this. hmpimg )
                .hname(this.getHospitalEntity().getHname())
                .build();
    }

}
