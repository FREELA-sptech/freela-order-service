package freela.order.service.domain.model.response;

import freela.order.service.domain.model.entities.Order;
import freela.order.service.domain.model.entities.OrderPhotos;
import freela.order.service.domain.model.entities.Proposal;
import freela.order.service.domain.model.entities.SubCategory;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse extends Order {
    private List<ProposalResponse> proposals;
    private List<SubCategory> subCategories;
    private List<OrderPhotos> photos;
    private UserNameDetails user;

    public OrderResponse(Order order, List<ProposalResponse> proposals, List<SubCategory> subCategories, List<OrderPhotos> photos, UserNameDetails user) {
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
        this.user = user;
    }
}
