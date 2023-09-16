package freela.order.service.domain.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Order {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private String title;
    private Double value;
    private String deadline;
    private Integer userId;
    @OneToOne
    private Proposal proposalAccepted;
    private boolean isAccepted;

    public Order(String description, String title, Double value, String deadline, Integer userId) {
        this.description = description;
        this.title = title;
        this.value = value;
        this.deadline = deadline;
        this.userId = userId;
    }
}
