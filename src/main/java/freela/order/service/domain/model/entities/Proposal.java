package freela.order.service.domain.model.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
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
    private Integer orderId;
    private Boolean isAccepted;
    private Boolean isRefused;

    public Proposal(Double value, Integer userId, String description, String deadline, Integer orderId, Boolean isAccepted, Boolean isRefused) {
        this.value = value;
        this.userId = userId;
        this.description = description;
        this.deadline = deadline;
        this.orderId = orderId;
        this.isAccepted = isAccepted;
        this.isRefused = isRefused;
    }
}
