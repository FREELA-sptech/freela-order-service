package freela.order.service.domain.service.interfaces;

import freela.order.service.domain.model.entities.Order;
import freela.order.service.domain.model.request.CreateOrderRequest;
import freela.order.service.domain.model.request.UpdateOrderRequest;
import freela.order.service.domain.model.response.OrderResponse;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.List;

public interface IOrderService {
    Order create(CreateOrderRequest createOrderRequest);
    Order update(Integer orderId, UpdateOrderRequest updateOrderRequest);
    Boolean delete(Integer order);
    OrderResponse getById(Integer orderId);
    List<OrderResponse> getAllOrdersBySubCategories(List<Integer> subCategoriesIds, String orderType);
}
