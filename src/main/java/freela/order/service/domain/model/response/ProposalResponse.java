package freela.order.service.domain.model.response;

import freela.order.service.domain.model.entities.Proposal;
import lombok.Data;

@Data
public class ProposalResponse extends Proposal {
    private UserNameDetails user;

    public ProposalResponse(Proposal proposal, UserNameDetails user) {
        super(
                proposal.getId(),
                proposal.getValue(),
                proposal.getUserId(),
                proposal.getDescription(),
                proposal.getDeadline(),
                proposal.getStatus(),
                proposal.getOrder()
        );
        this.user = user;
    }
}
