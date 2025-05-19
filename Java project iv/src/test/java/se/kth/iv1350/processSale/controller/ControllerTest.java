package se.kth.iv1350.processSale.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import se.kth.iv1350.processSale.integration.AccountingSystem;
import se.kth.iv1350.processSale.integration.DiscountHandler;
import se.kth.iv1350.processSale.integration.InventorySystem;
import se.kth.iv1350.processSale.integration.ItemNotFoundException;
import se.kth.iv1350.processSale.integration.Printer;
import se.kth.iv1350.processSale.model.RegisteredItem;
import se.kth.iv1350.processSale.util.LogHandler;
import se.kth.iv1350.processSale.dto.ItemDTO;
import se.kth.iv1350.processSale.dto.SaleDTO;

public class ControllerTest{
    private AccountingSystem accountingSystem;
    private InventorySystem inventorySystem;
    private DiscountHandler discountHandler;
    private Printer printer;
    private Controller controller;
    private ItemDTO testingItem;
    private LogHandler dummyLogger;

    @BeforeEach
    public void setUp() throws ItemNotFoundException, ItemRegistrationFailureException, IOException {
        accountingSystem = new AccountingSystem();
        inventorySystem = new InventorySystem();
        discountHandler = new DiscountHandler();
        printer = new Printer();

        dummyLogger = new LogHandler() {
            @Override
            public void logException (Exception e) {
            }
        };
      
        controller = new Controller(accountingSystem, inventorySystem, discountHandler, printer, dummyLogger);
        controller.startSale();
        testingItem = controller.registerNewItem(1234, 2);
    }


    @Test
    public void testIfItemIsNotNullWhenSaleIsStarted() {
        assertNotNull(testingItem, "Item should not be null after sale is started");
    }

    @Test
    public void testIfItemQuantityDefaultsToOneWhenUnspecified() throws ItemNotFoundException, ItemRegistrationFailureException {
       controller.registerNewItem(5678, 0);
       controller.receivePayment(100);
       RegisteredItem itemWithTestingQuantity = controller.getSaleInfo().getListOfItems().get(1);
       assertEquals(1, itemWithTestingQuantity.getQuantity(), "Item quantity should default to 1 when unspecified");
    }

    @Test
    public void testIfRegisterNewItemThrowsItemRegistrationFailureException() {
        assertThrows(ItemRegistrationFailureException.class, () -> {
            try {
                controller.registerNewItem(7891, 1);
            }
            catch (ItemNotFoundException e) {
                fail("Unexpected ItemNotFoundException thrown"); 
            }
        },
        "ItemRegistrationFailureException should be thrown when dtabase failure occurs");
    }

    @Test
    public void testIfTheCorrectItemIsAddedToTheSaleWhenIdentifierIsEntered() {
        assertEquals(1234, testingItem.getItemID(), "Item ID should match the entered identifier");
    }

    @Test
    public void testIfSaleInfoIsNotNullWhenPaymentIsReceived() {
        controller.receivePayment(100);
        assertNotNull(controller.getSaleInfo(), "Sale information should not be null");
    }

    @Test
    public void testIfRunningTotalIsMoreThanZeroWhenItemIsRegistered() {
        assertTrue(controller.getRunningTotal() > 0, "Running total should be greater than 0");
    }

    @Test
    public void testIfTotalVATIsMoreThanZeroWhenItemIsRegistered() {
        assertTrue(controller.getTotalVAT() > 0, "Total VAT should be greater than 0");
    }

    @Test
    public void testIfDiscountIsAppliedWhenDiscountRequestIsMade() {
        assertTrue(controller.discountRequest(1212) < controller.getRunningTotal(), "Discounted total should be less than running total");
    }

    @Test
    public void testIfPaidAmountMatchesTheReceivedPayment() {
        controller.receivePayment(300);
        SaleDTO saleInfo = controller.getSaleInfo();
        assertEquals(300, saleInfo.getPaidAmount(), "Paid amount should match the received payment");
    }
}


