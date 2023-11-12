package freela.order.service.domain.model.response;

import freela.order.service.domain.model.entities.Order;
import freela.order.service.domain.model.entities.OrderPhotos;
import freela.order.service.domain.model.entities.Proposal;
import freela.order.service.domain.model.entities.SubCategory;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse extends Order {
    private List<Proposal> proposals;
    private List<SubCategory> subCategories;
    private List<OrderPhotos> photos;

    public OrderResponse(Order order, List<Proposal> proposals, List<SubCategory> subCategories, List<OrderPhotos> photos) {
        super(
                order.getId(),
                order.getDescription(),
                order.getTitle(),
                order.getValue(),
                order.getDeadline(),
                order.getUserId(),
                order.getProposalAccepted(),
                order.getStatus()
        );
        this.proposals = proposals;
        this.subCategories = subCategories;
        this.photos = photos;
    }
}
