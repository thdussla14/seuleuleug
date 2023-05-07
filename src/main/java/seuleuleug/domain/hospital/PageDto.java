package seuleuleug.domain.hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PageDto {
    private long totalCount; // 전체 게시물수
    private int totalPage; // 전체 페이지수
    private int page; // 현재 페이지번호
    private List<HospitalDto> hospitalDtoList; // 병원리스트dto
}
