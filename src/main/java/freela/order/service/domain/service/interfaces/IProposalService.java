package freela.order.service.domain.service.interfaces;

import freela.order.service.domain.model.entities.Proposal;
import freela.order.service.domain.model.enums.EStatus;
import freela.order.service.domain.model.request.CreateProposalRequest;
import freela.order.service.domain.model.request.UpdateProposalRequest;
import freela.order.service.domain.model.response.ProposalResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IProposalService {
    ProposalResponse create(Integer userId, CreateProposalRequest createProposalRequest, Integer orderId);
    List<ProposalResponse> findProposalsByUserId(Integer userId);
    List<Proposal> findAllProposalsByOrderId(Integer orderId);
    Boolean delete(Integer proposalId);
    Boolean changeStatus(Integer proposalId, EStatus status);
    Proposal update(Integer proposalId, UpdateProposalRequest updateProposalRequest);
}
