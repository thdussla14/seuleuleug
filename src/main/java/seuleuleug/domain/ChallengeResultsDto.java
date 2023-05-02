package seuleuleug.domain;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChallengeResultsDto {
    private int sno;
    private String simg;
    private int sstate;
    private int mno;
    private int chno;


}
