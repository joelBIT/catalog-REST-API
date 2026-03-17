package api._bit.catalog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class Review extends BaseEntity {

    @ManyToOne
    private Game game;

    @Lob
    private String review;

    private int rating;
    private String heading;
    private String reviewerName;
    private LocalDate date;
}
