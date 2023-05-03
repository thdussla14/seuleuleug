package seuleuleug.domain.board;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor@Builder
public class CategoryDto {

    private int         cno;           //카테고리 번호
    private String      cname;         //카테고리 이름

    public CategoryEntity toCategoryEntity(){
        return CategoryEntity.builder()
                .cno(this.cno)
                .cname(this.cname)
                .build();
    }
}
