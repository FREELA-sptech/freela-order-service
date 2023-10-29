package freela.order.service.domain.service;

import freela.order.service.domain.model.entities.Order;
import freela.order.service.domain.model.entities.Proposal;
import freela.order.service.domain.model.enums.EStatus;
import freela.order.service.domain.model.request.CreateProposalRequest;
import freela.order.service.domain.model.request.UpdateProposalRequest;
import freela.order.service.domain.service.interfaces.IProposalService;
import freela.order.service.infra.repository.OrderRepository;
import freela.order.service.infra.repository.ProposalRepository;
import freela.order.service.web.exceptions.NotFoundException;
import org.aspectj.weaver.patterns.IfPointcut;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalService implements IProposalService {
    private final ProposalRepository proposalRepository;
    private final OrderRepository orderRepository;

    public ProposalService(ProposalRepository proposalRepository, OrderRepository orderRepository) {
        this.proposalRepository = proposalRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Proposal create(Integer userId, CreateProposalRequest createProposalRequest, Integer orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Ordem nao encontrada!")
        );

        Proposal proposal = proposalRepository.save(
                new Proposal(
                        createProposalRequest.getValue(),
                        userId,
                        createProposalRequest.getDescription(),
                        createProposalRequest.getDeadline(),
                        order
                ));

        return proposal;
    }

    @Override
    public List<Proposal> findProposalsByUserId(Integer userId) {
        return this.proposalRepository.findAllByUserId(userId);
    }

    @Override
    public List<Proposal> findAllProposalsByOrderId(Integer orderId) {
        return this.proposalRepository.findAllByOrderId(orderId);
    }

    @Override
    public Boolean delete(Integer proposalId) {
        Proposal proposal = this.proposalRepository.findById(proposalId).orElseThrow(
                () -> new NotFoundException("Proposta nao encontrada!")
        );

        this.proposalRepository.delete(proposal);
        return true;
    }

    @Override
    public Boolean changeStatus(Integer proposalId, EStatus status) {
        Proposal proposal = this.proposalRepository.findById(proposalId).orElseThrow(
                () -> new NotFoundException("Proposta nao encontrada!")
        );

        proposal.setStatus(status);

        this.proposalRepository.save(proposal);
        return true;
    }

    @Override
    public Proposal update(Integer proposalId, UpdateProposalRequest updateProposalRequest) {
        Proposal proposal = this.proposalRepository.findById(proposalId).orElseThrow(
                () -> new NotFoundException("Proposta nao encontrada!")
        );

        proposal.setValue(updateProposalRequest.getValue());
        proposal.setDescription(updateProposalRequest.getDescription());
        proposal.setDeadline(updateProposalRequest.getDeadline());

        this.proposalRepository.save(proposal);

        return proposal;
    }
}
