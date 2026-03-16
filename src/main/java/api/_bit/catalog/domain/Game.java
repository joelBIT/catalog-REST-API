package api._bit.catalog.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class Game extends BaseEntity {

    private String title;
    private int players;
    private String publisher;
    private String developer;
    private String cover;
    private String description;

    @Enumerated(EnumType.STRING)
    private GameCategory category;

    private LocalDateTime releaseDate;
}