package seuleuleug.domain.challenges;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    private String cdate;
    private String udate;

    private List<MultipartFile> chimg ;
    private List<FileDto> chfiles ;
    private List<ChallengeResultsDto> challengeResultsDto;

    // toEntity
    public ChallengesEntity toEntity() {
        return ChallengesEntity.builder()
            .chno(this.chno)
            .chname(this.chname)
            .chcontent(this.chcontent)
            .build();
    }

}
