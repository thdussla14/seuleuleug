package seuleuleug.domain.fortune;

import lombok.*;
import seuleuleug.domain.board.CategoryEntity;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor@Builder
public class WordDto {

    private int         wno;
    private String      wcontent;

    public WordEntity toWordEntity(){
        return WordEntity.builder()
                .wno(this.wno)
                .wcontent(this.wcontent)
                .build();
    }

}
