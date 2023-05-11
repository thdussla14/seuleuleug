package seuleuleug.domain.challenges;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "challengesimg")
public class ChallengesImgEntity {
    @Id
    private String uuidFile;
    @Column
    private String originalFilename;

    @ManyToOne
    @JoinColumn(name = "chno")
    @ToString.Exclude
    private ChallengesEntity challengesEntity;

    public FileDto toDto (){
        return FileDto.builder()
                .uuidFile(uuidFile)
                .originalFilename(originalFilename)
                .build();
    }
}
