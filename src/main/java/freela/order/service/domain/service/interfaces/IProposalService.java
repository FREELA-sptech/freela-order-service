package freela.order.service.domain.service.interfaces;

import freela.order.service.domain.model.entities.Proposal;
import freela.order.service.domain.model.request.CreateProposalRequest;
import freela.order.service.domain.model.request.UpdateProposalRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IProposalService {
    Proposal create(Integer userId, CreateProposalRequest createProposalRequest, Integer orderId);
    List<Proposal> findProposalsByUserId(Integer userId);
    List<Proposal> findAllProposalsByOrderId(Integer orderId);
    Boolean delete(Integer proposalId);
    Boolean refuse(Integer proposalId);
    Proposal update(Integer proposalId, UpdateProposalRequest updateProposalRequest);
}
