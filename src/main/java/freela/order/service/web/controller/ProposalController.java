package freela.order.service.web.controller;

import freela.order.service.domain.model.entities.Proposal;
import freela.order.service.domain.model.enums.EStatus;
import freela.order.service.domain.model.request.CreateProposalRequest;
import freela.order.service.domain.model.request.UpdateProposalRequest;
import freela.order.service.domain.service.ProposalService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/proposal")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ProposalController {

    @Autowired
    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "400", description =
                    "Pedido ja aceito.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "201", description = "Criado!.")
    })
    @PostMapping("/{orderId}/{userId}")
    public ResponseEntity<Proposal> post(
            @PathVariable @NotNull int orderId,
            @PathVariable @NotNull int userId,
            @RequestBody CreateProposalRequest request
    ){
        return ResponseEntity.created(URI.create("/proposals/" + userId))
                .body(proposalService.create(userId, request, orderId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "400", description =
                    "Pedido ja aceito.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "201", description = "Criado!.")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Proposal>> findProposalsByUserid(@PathVariable Integer userId) {
        return ResponseEntity.ok(proposalService.findProposalsByUserId(userId));
    }

    @DeleteMapping("/{proposalId}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer proposalId){
        return ResponseEntity.ok(this.proposalService.delete(proposalId));
    }

    @PutMapping("/status/{proposalId}")
    public ResponseEntity<Boolean> refuseProposal(@PathVariable Integer proposalId, @RequestParam EStatus status){
        return ResponseEntity.ok(this.proposalService.changeStatus(proposalId, status));
    }

    @PutMapping("/{proposalId}")
    public ResponseEntity<Proposal> update(@PathVariable Integer proposalId, @RequestBody UpdateProposalRequest request) {
        return ResponseEntity.ok(this.proposalService.update(proposalId, request));

    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Object> findProposalsByOrder(@PathVariable Integer orderId){
        return ResponseEntity.status(200).body(this.proposalService.findAllProposalsByOrderId(orderId));

    }
}
