package freela.order.service.web.controller;

import com.google.gson.Gson;
import freela.order.service.domain.model.entities.Order;
import freela.order.service.domain.model.request.CreateOrderRequest;
import freela.order.service.domain.model.request.UpdateOrderRequest;
import freela.order.service.domain.model.response.OrderResponse;
import freela.order.service.domain.service.OrderService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class OrderController {
    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Parâmetros incorretos.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "201", description = "Ordem Criada.")
    })
    @PostMapping
    public ResponseEntity<Order> post(
            String createOrderRequest,
            ArrayList<MultipartFile> photos
    ) {
        Gson gson = new Gson();

        CreateOrderRequest request = gson.fromJson(createOrderRequest, CreateOrderRequest.class);

        return ResponseEntity.ok(orderService.create(request, photos));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "Lista não encontrada.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Lista completa.")
    })
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll(@RequestParam(required = true, name = "subCategoriesIds") List<Integer> subCategoriesIds, @RequestParam(required = false, name = "orderType") String orderType) {
        return ResponseEntity.status(200).body(orderService.getAllOrdersBySubCategories(subCategoriesIds, orderType));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description =
                    "Lista não encontrada.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Lista completa.")
    })
    @GetMapping("user/{userId}")
    public ResponseEntity<List<OrderResponse>> getByUserId(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(orderService.getByUserId(userId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description =
                    "Ordem não encontrada.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Ordem Encontrada.")
    })
    @GetMapping("{orderId}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Integer orderId) {
        return ResponseEntity.status(200).body(orderService.getById(orderId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Ordem não encontrada."),
            @ApiResponse(responseCode = "200", description = "Ordem atualizada com sucesso.")
    })
    @PatchMapping("/{orderId}")
    public ResponseEntity<Object> update(
            String updateOrderRequest,
            ArrayList<MultipartFile> newPhotos,
            @PathVariable Integer orderId
    ) {
        Gson gson = new Gson();

        UpdateOrderRequest request = gson.fromJson(updateOrderRequest, UpdateOrderRequest.class);

        return ResponseEntity.ok(orderService.update(orderId, request, newPhotos));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Ordem não encontrada."),
            @ApiResponse(responseCode = "200", description = "Ordem deletada com sucesso.")
    })
    @DeleteMapping("{orderId}")
    public ResponseEntity<Object> delete(@PathVariable Integer orderId){
        return ResponseEntity.ok(orderService.delete(orderId));
    }
}
