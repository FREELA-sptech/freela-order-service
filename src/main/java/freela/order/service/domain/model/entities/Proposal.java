package freela.order.service.domain.model.entities;

import freela.order.service.domain.model.enums.EStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity(name = "proposal_bff")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @DecimalMin("0.1")
    private Double value;
    private Integer userId;
    private String description;
    private String deadline;
    private EStatus status;
    @ManyToOne
    private Order order;

    public Proposal(Double value, Integer userId, String description, String deadline, Order order) {
        this.value = value;
        this.userId = userId;
        this.description = description;
        this.deadline = deadline;
        this.order = order;
        this.status = EStatus.OPEN;
    }
}
