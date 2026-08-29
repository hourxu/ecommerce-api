package ecommerce.project.service;

import ecommerce.project.dto.inventory.InventoryRequest;
import ecommerce.project.dto.inventory.InventoryResponse;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    InventoryResponse create (InventoryRequest request);
    List<InventoryResponse> getAll();
    InventoryResponse update(UUID id,InventoryRequest request);
    InventoryResponse deleted(UUID id);
}
