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
    private int mno;
    private int chno;

    private String uuidFile;
    private String originalFilename;

    public ChallengeResultsEntity toEntity(){
        return ChallengeResultsEntity.builder()
                .simg(this.simg.toString())
                .uuidFile(this.uuidFile)
                .originalFilename(this.originalFilename)
                .build();
    }

}
