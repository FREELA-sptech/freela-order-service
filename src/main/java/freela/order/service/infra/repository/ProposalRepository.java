package freela.order.service.infra.repository;

import freela.order.service.domain.model.entities.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Integer> {
    List<Proposal> findAllByOrderId(Integer orderId);
    List<Proposal> findAllByUserId(Integer userId);
}
