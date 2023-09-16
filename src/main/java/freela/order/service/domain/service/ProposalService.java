package freela.order.service.domain.service;

import freela.order.service.domain.model.entities.Proposal;
import freela.order.service.domain.model.request.CreateProposalRequest;
import freela.order.service.domain.model.request.UpdateProposalRequest;
import freela.order.service.domain.service.interfaces.IProposalService;
import freela.order.service.infra.repository.ProposalRepository;
import freela.order.service.web.exceptions.NotFoundException;
import org.aspectj.weaver.patterns.IfPointcut;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalService implements IProposalService {
    private final ProposalRepository proposalRepository;

    public ProposalService(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    @Override
    public Proposal create(Integer userId, CreateProposalRequest createProposalRequest, Integer orderId) {
        return proposalRepository.save(
                new Proposal(
                        createProposalRequest.getValue(),
                        userId,
                        createProposalRequest.getDescription(),
                        createProposalRequest.getDeadline(),
                        orderId,
                        false,
                        false
                        ));
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
    public Boolean refuse(Integer proposalId) {
        Proposal proposal = this.proposalRepository.findById(proposalId).orElseThrow(
                () -> new NotFoundException("Proposta nao encontrada!")
        );

        proposal.setIsAccepted(false);
        proposal.setIsRefused(true);
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
