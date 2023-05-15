package seuleuleug.domain.challenges;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChallengeResultsDto {
    private int sno;
    private MultipartFile simg;
    private int sstate;
    private String memail;
    private int mno;
    private int chno;

    private String uuidFile;
    private String originalFilename;

    private String cdate;
    private String udate;

    public ChallengeResultsEntity toEntity(){
        return ChallengeResultsEntity.builder()
                .uuidFile(this.uuidFile)
                .originalFilename(this.originalFilename)
                .build();
    }

}
