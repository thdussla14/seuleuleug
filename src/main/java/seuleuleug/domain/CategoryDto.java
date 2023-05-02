package seuleuleug.domain;

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

}
