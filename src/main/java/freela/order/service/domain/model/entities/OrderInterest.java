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
public class OrderInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Order order;
    @ManyToOne
    private SubCategory subCategory;

    public OrderInterest(Order order, SubCategory subCategory) {
        this.order = order;
        this.subCategory = subCategory;
    }
}
