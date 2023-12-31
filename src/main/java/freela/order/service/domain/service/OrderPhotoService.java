package freela.order.service.domain.service;

import freela.order.service.domain.model.entities.Order;
import freela.order.service.domain.model.entities.OrderPhotos;
import freela.order.service.domain.service.interfaces.IOrderPhotoService;
import freela.order.service.infra.repository.OrderInterestRepository;
import freela.order.service.infra.repository.OrderPhotoRepository;
import freela.order.service.web.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderPhotoService implements IOrderPhotoService {
    private final OrderPhotoRepository orderPhotoRepository;

    public OrderPhotoService(OrderPhotoRepository orderPhotoRepository) {
        this.orderPhotoRepository = orderPhotoRepository;
    }

    @Override
    public void create(ArrayList<MultipartFile> files, Order order) {
        try {
            for (MultipartFile file : files) {
                this.orderPhotoRepository.save(
                        new OrderPhotos(
                                order,
                                file.getBytes()
                        )
                );
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void delete(List<Integer> ids) {
        for (Integer id : ids) {
            OrderPhotos photo = this.orderPhotoRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Foto nao encontrada pelo id!")
            );

            this.orderPhotoRepository.delete(photo);
        }
    }
}
