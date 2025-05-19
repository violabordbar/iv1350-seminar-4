package se.kth.iv1350.processSale.integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventorySystemTest {
    private InventorySystem inventorySystem = new InventorySystem();

    private static final int INVALID_ITEM_ID = 1111;
    private static final int EXCEPTION_THROWING_ITEM_ID = 7891;
    
    @Test
    public void testIfValidateIdentifierThrowsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> {
            inventorySystem.validateIdentifier(INVALID_ITEM_ID);
        });
    }

    @Test
    public void testIfValidateIdentifierThrowsDataBaseFailureException() {
        assertThrows(DataBaseFailureException.class, () -> {
            inventorySystem.validateIdentifier(EXCEPTION_THROWING_ITEM_ID);
        });
    }
}
