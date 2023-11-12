package freela.order.service.infra.repository;


import freela.order.service.domain.model.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Integer> {
    List<SubCategory> findAllByIdIn(List<Integer> subCategoryIds);

}
