package freela.order.service.domain.service.interfaces;

import freela.order.service.domain.model.entities.Order;
import freela.order.service.domain.model.entities.SubCategory;

import java.util.ArrayList;
import java.util.List;

public interface IOrderInterestService {
    void create(ArrayList<Integer> subCategories, Order order);
    List<SubCategory> getAllSubCategoriesByOrder(Order order);
    void updateOrderInterest(List<Integer> subCategories, Order order);
}
