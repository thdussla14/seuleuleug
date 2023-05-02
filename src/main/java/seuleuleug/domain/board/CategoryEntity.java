package seuleuleug.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int         cno;           //카테고리 번호
    @Column
    private String      cname;         //카테고리 이름

    public CategoryDto toCategoryDto() {
        return CategoryDto.builder()
                .cno(this.cno)
                .cname(this.cname)
                .build();
    }
}
