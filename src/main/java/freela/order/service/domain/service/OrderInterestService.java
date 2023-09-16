package freela.order.service.domain.service;

import freela.order.service.domain.model.entities.Order;
import freela.order.service.domain.model.entities.OrderInterest;
import freela.order.service.domain.model.entities.SubCategory;
import freela.order.service.domain.service.interfaces.IOrderInterestService;
import freela.order.service.infra.repository.OrderInterestRepository;
import freela.order.service.infra.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderInterestService implements IOrderInterestService {
    private final OrderInterestRepository orderInterestRepository;
    private final SubCategoryRepository subCategoryRepository;

    public OrderInterestService(OrderInterestRepository orderInterestRepository, SubCategoryRepository subCategoryRepository) {
        this.orderInterestRepository = orderInterestRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public void create(ArrayList<Integer> subCategories, Order order) {
        subCategories.stream()
                .map(subCategoryRepository::findById)
                .flatMap(Optional::stream)
                .forEach(subCategory -> orderInterestRepository.save(new OrderInterest(order, subCategory)));
    }

    @Override
    public List<SubCategory> getAllSubCategoriesByOrder(Order order) {
        List<OrderInterest> interests = orderInterestRepository.findAllByOrder(order);

        List<SubCategory> subCategories = new ArrayList<>();

        interests.stream()
                .map(OrderInterest::getSubCategory)
                .forEach(subCategories::add);

        return subCategories;
    }

    @Override
    public void updateOrderInterest(List<Integer> subCategories, Order order) {
        List<OrderInterest> existingInterests = this.orderInterestRepository.findAllByOrder(order);

        existingInterests.forEach(userInterest -> {
            if (!subCategories.contains(userInterest.getSubCategory().getId())) {
                this.orderInterestRepository.delete(userInterest);
            }
        });

        for (Integer subCategoryId : subCategories) {
            boolean interestExists = existingInterests.stream()
                    .anyMatch(userInterest -> userInterest.getSubCategory().getId().equals(subCategoryId));

            if (!interestExists) {
                Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(subCategoryId);
                subCategoryOptional.ifPresent(subCategory -> this.orderInterestRepository.save(
                        new OrderInterest(
                                order,
                                subCategory
                        )
                ));
            }
        }
    }
}
