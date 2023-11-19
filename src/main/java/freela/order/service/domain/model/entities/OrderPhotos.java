package freela.order.service.domain.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity(name = "order_photos_bff")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderPhotos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Order order;
    @Lob
    private byte[] photo;

    public OrderPhotos(Order order, byte[] photo) {
        this.order = order;
        this.photo = photo;
    }
}
