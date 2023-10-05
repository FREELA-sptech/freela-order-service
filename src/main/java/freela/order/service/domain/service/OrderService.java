package freela.order.service.domain.service;

import freela.order.service.domain.model.entities.*;
import freela.order.service.domain.model.request.CreateOrderRequest;
import freela.order.service.domain.model.request.UpdateOrderRequest;
import freela.order.service.domain.service.interfaces.IOrderService;
import freela.order.service.infra.repository.*;
import freela.order.service.web.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderInterestService orderInterestService;
    private final OrderInterestRepository orderInterestRepository;
    private final OrderPhotoRepository orderPhotoRepository;
    private final ProposalRepository proposalRepository;
    private final SubCategoryRepository subCategoryRepository;

    public OrderService(OrderRepository orderRepository, OrderInterestService orderInterestService, OrderInterestRepository orderInterestRepository, OrderPhotoRepository orderPhotoRepository, ProposalRepository proposalRepository, SubCategoryRepository subCategoryRepository) {
        this.orderRepository = orderRepository;
        this.orderInterestService = orderInterestService;
        this.orderInterestRepository = orderInterestRepository;
        this.orderPhotoRepository = orderPhotoRepository;
        this.proposalRepository = proposalRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public Order create(CreateOrderRequest createOrderRequest) {
        ArrayList<Integer> subCategoriesIds = createOrderRequest.getSubCategoriesIds();

        Order order = orderRepository.save(
                new Order(
                        createOrderRequest.getDescription(),
                        createOrderRequest.getTitle(),
                        createOrderRequest.getValue(),
                        createOrderRequest.getDeadline(),
                        createOrderRequest.getUserId()
                )
        );

        this.orderInterestService.create(subCategoriesIds, order);

        return order;
    }

    @Override
    public Order update(Integer orderId, UpdateOrderRequest updateOrderRequest) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Ordem nao encontrada!")
        );

        order.setDeadline(updateOrderRequest.getDeadline());
        order.setDescription(updateOrderRequest.getDescription());
        order.setTitle(updateOrderRequest.getTitle());
        order.setValue(updateOrderRequest.getValue());

        this.orderInterestService.updateOrderInterest(updateOrderRequest.getSubCategoriesIds(), order);

        orderRepository.save(order);

        return order;
    }

    @Override
    public Boolean delete(Integer orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Ordem nao encontrada!")
        );

        List<OrderPhotos> photos = this.orderPhotoRepository.findAllByOrder(order);
        List<Proposal> proposals = this.proposalRepository.findAllByOrderId(order.getId());
        List<OrderInterest> orderInterests = this.orderInterestRepository.findAllByOrder(order);

        this.proposalRepository.deleteAll(proposals);
        this.orderPhotoRepository.deleteAll(photos);
        this.orderInterestRepository.deleteAll(orderInterests);
        this.orderRepository.delete(order);
        return true;
    }

    @Override
    public Order getById(Integer orderId) {
        return this.orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Ordem nao encontrada!")
        );
    }

    @Override
    public List<Order> getAllOrdersBySubCategories(List<Integer> subCategoriesIds, String orderType) {
        List<SubCategory> subCategories = subCategoriesIds.stream()
                .map(subCategoryRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        List<Order> ordersTotal = orderRepository.findAllByIsAcceptedFalse();

        return ordersTotal.stream()
                .filter(order -> orderContainsSubCategories(order, subCategories))
                .collect(Collectors.toList());
    }

    private boolean orderContainsSubCategories(Order order, List<SubCategory> subCategories) {
        List<SubCategory> orderSubCategories = this.orderInterestService.getAllSubCategoriesByOrder(order);

        return !Collections.disjoint(orderSubCategories, subCategories);
    }
}