package seuleuleug.domain.crawling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class SimriTestDto {
    private String stitle;
    private String scontent;
    private String surl;
}
