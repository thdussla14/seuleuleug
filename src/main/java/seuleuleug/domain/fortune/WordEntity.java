package seuleuleug.domain.fortune;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seuleuleug.domain.board.CategoryDto;

import javax.persistence.*;

@Entity
@Table(name = "fortune")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int         wno;
    @Column(columnDefinition = "longtext")
    private String      wcontent;

    public WordDto toWordDto() {
        return WordDto.builder()
                .wno(this.wno)
                .wcontent(this.wcontent)
                .build();
    }

}
