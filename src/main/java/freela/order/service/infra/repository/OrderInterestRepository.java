package freela.order.service.infra.repository;

import freela.order.service.domain.model.entities.Order;
import freela.order.service.domain.model.entities.OrderInterest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderInterestRepository extends JpaRepository<OrderInterest, Integer> {
    List<OrderInterest> findAllByOrder(Order order);
}
