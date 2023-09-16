package freela.order.service.infra.repository;


import freela.order.service.domain.model.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Integer> {

}
