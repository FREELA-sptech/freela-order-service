package freela.order.service.domain.service.interfaces;

import freela.order.service.domain.model.entities.Order;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface IOrderPhotoService {
    void create(ArrayList<MultipartFile> files, Order order);
}
