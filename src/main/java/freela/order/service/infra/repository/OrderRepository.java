package freela.order.service.infra.repository;

import freela.order.service.domain.model.entities.Order;
import freela.order.service.domain.model.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByStatus(EStatus status);
}
