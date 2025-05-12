package se.kth.iv1350.processSale.integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventorySystemTest {
    private InventorySystem inventorySystem = new InventorySystem();
    
    @Test
    public void testIfValidateIdentifierThrowsItemNotFoundException() {
        int InvalidItemID = 1111; 
        assertThrows(ItemNotFoundException.class, () -> {
            inventorySystem.validateIdentifier(InvalidItemID);
        });
    }

    @Test
    public void testIfValidateIdentifierThrowsDataBaseFailureException() {
        int failedItemID = 7891; 
        assertThrows(DataBaseFailureException.class, () -> {
            inventorySystem.validateIdentifier(failedItemID);
        });
    }
}
