package seuleuleug.domain.crawling;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrawlingDto {
    private String card_tag;
    private String card_head;
    private String card_head_href;
    private String card_head_info;

    private String card_body1;
    private String card_body2;
    private String card_body3;
    private String card_body4;
    private String card_body5;
}
