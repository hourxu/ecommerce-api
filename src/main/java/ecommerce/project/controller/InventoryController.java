package ecommerce.project.controller;

import ecommerce.project.dto.inventory.InventoryRequest;
import ecommerce.project.dto.inventory.InventoryResponse;
import ecommerce.project.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventories")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> create(@RequestBody InventoryRequest request){
        return new ResponseEntity<>(
                inventoryService.create(request),
                HttpStatus.CREATED
            );
    }
    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAll(){
        return ResponseEntity.ok(inventoryService.getAll());
    }
    @PutMapping("{id}")
    public ResponseEntity<InventoryResponse> update(@RequestBody @Valid InventoryRequest request, @PathVariable UUID id){
        return new ResponseEntity<>(
                inventoryService.update(id,request),
                HttpStatus.OK
        );
    }
    @DeleteMapping("{id}")
    public ResponseEntity<InventoryResponse>deleted(@PathVariable UUID id){
        return ResponseEntity.ok(inventoryService.deleted(id));
    }
}
