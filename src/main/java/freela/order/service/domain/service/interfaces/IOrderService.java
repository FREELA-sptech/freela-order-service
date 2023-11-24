package freela.order.service.domain.service.interfaces;

import freela.order.service.domain.model.entities.Order;
import freela.order.service.domain.model.request.CreateOrderRequest;
import freela.order.service.domain.model.request.UpdateOrderRequest;
import freela.order.service.domain.model.response.OrderResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IOrderService {
    Order create(CreateOrderRequest createOrderRequest, ArrayList<MultipartFile> photos);
    OrderResponse update(Integer orderId, UpdateOrderRequest updateOrderRequest, ArrayList<MultipartFile> newPhotos);
    Boolean delete(Integer order);
    OrderResponse getById(Integer orderId);
    List<OrderResponse> getAllOrdersBySubCategories(List<Integer> subCategoriesIds, String orderType);
    List<OrderResponse> getByUserId(Integer userId);
}
