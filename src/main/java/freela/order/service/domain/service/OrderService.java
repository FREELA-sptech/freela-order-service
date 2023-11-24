package freela.order.service.domain.service;

import freela.order.service.domain.model.entities.*;
import freela.order.service.domain.model.enums.EStatus;
import freela.order.service.domain.model.request.CreateOrderRequest;
import freela.order.service.domain.model.request.UpdateOrderRequest;
import freela.order.service.domain.model.response.OrderResponse;
import freela.order.service.domain.model.response.ProposalResponse;
import freela.order.service.domain.model.response.UserNameDetails;
import freela.order.service.domain.service.interfaces.IOrderService;
import freela.order.service.infra.repository.*;
import freela.order.service.web.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderInterestService orderInterestService;
    private final OrderInterestRepository orderInterestRepository;
    private final OrderPhotoRepository orderPhotoRepository;
    private final OrderPhotoService orderPhotoService;
    private final ProposalRepository proposalRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, OrderInterestService orderInterestService, OrderInterestRepository orderInterestRepository, OrderPhotoRepository orderPhotoRepository, OrderPhotoService orderPhotoService, ProposalRepository proposalRepository, SubCategoryRepository subCategoryRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderInterestService = orderInterestService;
        this.orderInterestRepository = orderInterestRepository;
        this.orderPhotoRepository = orderPhotoRepository;
        this.orderPhotoService = orderPhotoService;
        this.proposalRepository = proposalRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order create(CreateOrderRequest createOrderRequest, ArrayList<MultipartFile> photos) {
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
        if (photos != null) {
            this.orderPhotoService.create(photos, order);
        }

        return order;
    }

    @Override
    public OrderResponse update(Integer orderId, UpdateOrderRequest updateOrderRequest, ArrayList<MultipartFile> newPhotos) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Ordem nao encontrada!")
        );

        order.setDeadline(updateOrderRequest.getDeadline());
        order.setDescription(updateOrderRequest.getDescription());
        order.setTitle(updateOrderRequest.getTitle());
        order.setValue(updateOrderRequest.getValue());

        this.orderPhotoService.delete(updateOrderRequest.getDeletedPhotos());

        if (newPhotos != null) {
            this.orderPhotoService.create(newPhotos, order);
        }

        this.orderInterestService.updateOrderInterest(updateOrderRequest.getSubCategoriesIds(), order);

        orderRepository.save(order);

        List<Proposal> proposalsForOrder = this.proposalRepository.findAllByOrderId(order.getId());
        List<ProposalResponse> proposalResponse = new ArrayList<>();
        for (Proposal proposal : proposalsForOrder) {
            User userProposal = this.userRepository.findById(proposal.getUserId()).orElseThrow(
                    () -> new NotFoundException("Usuário não encontrado!")
            );

            UserNameDetails userNameDetailsProposals = new UserNameDetails(userProposal);

            proposalResponse.add(new ProposalResponse(proposal, userNameDetailsProposals));
        }
        List<SubCategory> subCategoriesForOrder = this.orderInterestService.getAllSubCategoriesByOrder(order);
        List<OrderPhotos> photosForOrder = this.orderPhotoRepository.findAllByOrder(order);
        User user = this.userRepository.findById(order.getUserId()).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );

        UserNameDetails userNameDetails = new UserNameDetails(user);

        return new OrderResponse(order, proposalResponse, subCategoriesForOrder, photosForOrder, userNameDetails);
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
    public OrderResponse getById(Integer orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Ordem nao encontrada!")
        );

        List<Proposal> proposalsForOrder = this.proposalRepository.findAllByOrderId(order.getId());
        List<ProposalResponse> proposalResponse = new ArrayList<>();
        for (Proposal proposal : proposalsForOrder) {
            User userProposal = this.userRepository.findById(proposal.getUserId()).orElseThrow(
                    () -> new NotFoundException("Usuário não encontrado!")
            );

            UserNameDetails userNameDetailsProposals = new UserNameDetails(userProposal);

            proposalResponse.add(new ProposalResponse(proposal, userNameDetailsProposals));
        }
        List<SubCategory> subCategoriesForOrder = this.orderInterestService.getAllSubCategoriesByOrder(order);
        List<OrderPhotos> photosForOrder = this.orderPhotoRepository.findAllByOrder(order);
        User user = this.userRepository.findById(order.getUserId()).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );

        UserNameDetails userNameDetails = new UserNameDetails(user);

        return new OrderResponse(order, proposalResponse, subCategoriesForOrder, photosForOrder, userNameDetails);
    }

    @Override
    public List<OrderResponse> getAllOrdersBySubCategories(List<Integer> subCategoriesIds, String orderType) {
        List<SubCategory> subCategories = subCategoryRepository.findAllByIdIn(subCategoriesIds);

        List<Order> ordersTotal = orderRepository.findAllByStatus(EStatus.OPEN);
        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Order order : ordersTotal) {
            List<Proposal> proposalsForOrder = proposalRepository.findAllByOrderId(order.getId());
            List<ProposalResponse> proposalResponse = new ArrayList<>();
            for (Proposal proposal : proposalsForOrder) {
                User userProposal = this.userRepository.findById(proposal.getUserId()).orElseThrow(
                        () -> new NotFoundException("Usuário não encontrado!")
                );

                UserNameDetails userNameDetailsProposals = new UserNameDetails(userProposal);

                proposalResponse.add(new ProposalResponse(proposal, userNameDetailsProposals));
            }
            List<SubCategory> subCategoriesForOrder = this.orderInterestService.getAllSubCategoriesByOrder(order);
            List<OrderPhotos> photosForOrder = this.orderPhotoRepository.findAllByOrder(order);
            User user = this.userRepository.findById(order.getUserId()).orElseThrow(
                    () -> new NotFoundException("Usuário não encontrado!")
            );

            UserNameDetails userNameDetails = new UserNameDetails(user);

            if (orderContainsSubCategories(order, subCategories)) {
                OrderResponse orderResponse = new OrderResponse(order, proposalResponse, subCategoriesForOrder, photosForOrder, userNameDetails);
                orderResponses.add(orderResponse);
            }
        }

        return orderResponses;
    }

    @Override
    public List<OrderResponse> getByUserId(Integer userId) {
        List<Order> ordersTotal = orderRepository.findAllByUserId(userId);
        List<OrderResponse> orderResponses = new ArrayList<>();
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );

        UserNameDetails userNameDetails = new UserNameDetails(user);

        for (Order order : ordersTotal) {
            List<Proposal> proposalsForOrder = proposalRepository.findAllByOrderId(order.getId());
            List<ProposalResponse> proposalResponse = new ArrayList<>();
            for (Proposal proposal : proposalsForOrder) {
                User userProposal = this.userRepository.findById(proposal.getUserId()).orElseThrow(
                        () -> new NotFoundException("Usuário não encontrado!")
                );

                UserNameDetails userNameDetailsProposals = new UserNameDetails(userProposal);

                proposalResponse.add(new ProposalResponse(proposal, userNameDetailsProposals));
            }

            List<SubCategory> subCategoriesForOrder = this.orderInterestService.getAllSubCategoriesByOrder(order);
            List<OrderPhotos> photosForOrder = this.orderPhotoRepository.findAllByOrder(order);


            OrderResponse orderResponse = new OrderResponse(order, proposalResponse, subCategoriesForOrder, photosForOrder, userNameDetails);
            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }

    private boolean orderContainsSubCategories(Order order, List<SubCategory> subCategories) {
        List<SubCategory> orderSubCategories = this.orderInterestService.getAllSubCategoriesByOrder(order);

        return !Collections.disjoint(orderSubCategories, subCategories);
    }
}
