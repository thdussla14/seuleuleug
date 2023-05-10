package seuleuleug.domain.challenges;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {
    private String originalFilename;
    private String uuidFile;
    private String sizeKb;

}
