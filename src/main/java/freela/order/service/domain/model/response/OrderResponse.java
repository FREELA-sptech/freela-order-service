package freela.order.service.domain.model.response;

import freela.order.service.domain.model.entities.Order;
import freela.order.service.domain.model.entities.Proposal;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse extends Order {
    private List<Proposal> proposals;

    public OrderResponse(Order order, List<Proposal> proposals) {
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
    }
}
