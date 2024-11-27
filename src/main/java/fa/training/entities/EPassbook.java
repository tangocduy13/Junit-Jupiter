package fa.training.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "E-PASSBOOK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class EPassbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Column(name = "deposited_amount", columnDefinition = "MONEY", updatable = false)
    private BigDecimal depositedAmount;

    @NotNull
    @Column(name = "start_date", updatable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "term", updatable = false)
    @Check(constraints = "term IN (1,2,3,6,9,12,18,24,36)")
    private Integer term;

    @NotNull
    @Column(name = "maturity_date", updatable = false)
    private LocalDate maturityDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "ID", nullable = false)
    private Account account;
}
