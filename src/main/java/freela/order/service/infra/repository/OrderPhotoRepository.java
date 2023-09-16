package freela.order.service.infra.repository;

import freela.order.service.domain.model.entities.Order;
import freela.order.service.domain.model.entities.OrderPhotos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderPhotoRepository extends JpaRepository<OrderPhotos, Integer> {
    List<OrderPhotos> findAllByOrder(Order order);
    void deleteById(Integer id);
}
