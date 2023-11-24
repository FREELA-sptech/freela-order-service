package freela.order.service.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderRequest {
    private String description;
    private String title;
    private Double value;
    private ArrayList<Integer> subCategoriesIds;
    private String deadline;
    private List<Integer> deletedPhotos;
}
