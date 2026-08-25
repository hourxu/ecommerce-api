package ecommerce.project.service.impl;

import ecommerce.project.dto.inventory.InventoryRequest;
import ecommerce.project.dto.inventory.InventoryResponse;
import ecommerce.project.entity.Inventory;
import ecommerce.project.entity.Product;
import ecommerce.project.exception.DeleteSuccessException;
import ecommerce.project.exception.InventoryNotFoundException;
import ecommerce.project.exception.ProductNotFoundException;
import ecommerce.project.mapper.InventoryMapper;
import ecommerce.project.respositity.InventoryRepository;
import ecommerce.project.respositity.ProductRepository;
import ecommerce.project.service.InventoryService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private  final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final ProductRepository productRepository;
    @Override
    public InventoryResponse create(InventoryRequest request) {
        Product product= productRepository.findById(request.productId()).orElseThrow(
                ProductNotFoundException::new
        );
        String combinedSize= request.size().trim().toUpperCase();
        Optional<Inventory> existing = inventoryRepository
                .findByProductIdAndSize(request.productId(), combinedSize);

        Inventory saved;
        ///check data in db if have value same
        if(existing.isPresent()){
            //get old value
            Inventory inventory=existing.get();
            //increase value by 1
            inventory.setQuantity(inventory.getQuantity()+1);
            // save data into db
            saved=inventoryRepository.save(inventory);
        }else{
            /// if does not exist convert entity to mapper
            Inventory inventory = inventoryMapper.toInventory(request,product);
            ///  save into db
            saved=inventoryRepository.save(inventory);
        }
        return inventoryMapper.toInventoryResponse(saved);
    }

    @Override
    public List<InventoryResponse> getall() {
        List<Inventory>inventories=inventoryRepository.findAll();
        return inventories.stream().map(inventoryMapper::toInventoryResponse).toList();
    }

    @Override
    @Transactional
    public InventoryResponse update(UUID id, InventoryRequest request) {
        Inventory inventory = inventoryRepository.findById(id).
                orElseThrow(InventoryNotFoundException::new);
        //after find id then set data to db
        inventory.setSize(request.size());
        inventory.setQuantity(request.quantity());
        return inventoryMapper.toInventoryResponse(inventory);
    }
    @Override
    public InventoryResponse deleted(UUID id){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(InventoryNotFoundException::new);
        inventoryRepository.deleteById(id);
        throw  new DeleteSuccessException();
    }
}
