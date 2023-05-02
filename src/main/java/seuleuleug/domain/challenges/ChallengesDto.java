package seuleuleug.domain.challenges;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChallengesDto {
    private int chno;
    private String chname;
    private String chcontent;
    private String chimg;

    // toEntity
    public ChallengesEntity toEntity() {
        return ChallengesEntity.builder()
            .chno(this.chno)
            .chname(this.chname)
            .chcontent(this.chcontent)
            .chimg(this.chimg)
            .build();
    }

}
