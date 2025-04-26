package pro_sky.accounting_for_socks.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro_sky.accounting_for_socks.model.dto.SockRequest;
import pro_sky.accounting_for_socks.service.SockService;

@RestController
@RequestMapping("/api/socks")
public class SockController {

    private final SockService sockService;


    public SockController(SockService sockService) {
        this.sockService = sockService;
    }

    @PostMapping("/income")
    public ResponseEntity<Void> income(@RequestBody SockRequest request) {
        sockService.income(request.getColor(), request.getCottonPart(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/outcome")
    public ResponseEntity<Void> outcome(@RequestBody SockRequest request) {
        sockService.outcome(request.getColor(), request.getCottonPart(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<String> getSocks(@RequestParam String color,
                                           @RequestParam String operation,
                                           @RequestParam Integer cottonPart) {
        Integer quantity = sockService.getSocks(color, operation, cottonPart);
        return ResponseEntity.ok(String.valueOf(quantity == null ? 0 : quantity));
    }
}
