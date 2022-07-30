import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Anthony Peters
 *
 * Holds ProductCatalog and drives sale and alturation methods
 */

public class Register {

    /**
     * ProductCatalog object used to store ProductSpecifications
     */
    ProductCatalog productCatalog;

    /**
     * Sale objects to store values of sold products
     */
    Sale sale;

    ObservableList<ProductSpecification> productSpecificationSaleObservableList,
            productSpecificationDeleteObservableList,
            productSpecificationModifyObservableList;

    /**
     * Preset strings used in Register
     */
    private static final String
            WELCOME_MESSAGE                     = "Welcome to Peter's cash register system!",
            FILENAME_MESSAGE                    = "Input file : ",
            INPUT_ERROR                         = "!!! Invalid input",
            FILE_ERROR                          = "File name is invalid",
            BEGINNING_SALE_MESSAGE              = "Beginning a new sale? (Y/N) ",
            SALE_ERROR                          = "Should be (Y/N)",
            CODE_ERROR                          = "Should be A[###] or B[###], 0000 = item list, -1 = quit",
            QUANTITY_ERROR                      = "Should be [1-100]",
            PRICE_ERROR                         = "Should be greater than 0",

            BREAK_LINE                          = "--------------------",
            PRODUCT_CODE_RECEIPT_MESSAGE        = "Product Code : ",
            PRODUCT_NAME_RECEIPT_MESSAGE        = "Product Name : ",
            PRODUCT_QUANTITY_RECEIPT_MESSAGE    = "Product Quantity : ",
            PRODUCT_TOTAL_RECEIPT_MESSAGE       = "Product Total : $",

            ENTER_CODE_MESSAGE                  = "Enter product code : ",
            ITEM_NAME_MESSAGE                   = "item name : ",
            ENTER_QUANTITY_MESSAGE              = "Enter quantity : ",
            ITEM_TOTAL_MESSAGE                  = "item total : $",
            RECEIPT_LINE                        = "----------------------------",
            TENDER_MESSAGE                      = "Tendered amount      $   ",
            TENDER_AMOUNT_WRONG                 = "Amount entered is invalid",
            TENDER_AMOUNT_TOO_SMALL             = "Amount entered is too small",
            CHANGE_AMOUNT                       = "Change",
            EOD_MESSAGE                         = "\nThe total sale for the day is  $",
            UPDATE_PROMPT_MESSAGE               = "Do you want to update the items data? (A/D/M/Q): ",
            UPDATE_ERROR                        = "Should be (A/D/M/Q)",
            UPDATE_CODE_PROMPT                  = "item code  : ",
            UPDATE_NAME_PROMPT                  = "item name  : ",
            UPDATE_PRICE_PROMPT                 = "item price : ",
            ERROR_ITEM_EXIST                    = "!!! item already created",
            UPDATE_ITEM_NOT_FOUND               = "!!! item not found",
            UPDATE_CODE_ERROR                   = "Should be A[###] or B[###]",
            UPDATE_ADD_SUCCESSFUL               = "Item add successful!",
            UPDATE_DELETE_SUCCESSFUL            = "Item delete successful!",
            UPDATE_MODIFY_SUCCESSFUL            = "Item modify successful!",
            THANK_YOU                           = "Thanks for using POST system. Goodbye.";

    /**
     * String formats used in Register
     */
    private static final String outputSingleFormat = "%-13s", outputSingleNewLineAfterFormat = "%-21s%n",
            outputSingleNewLineBeforeFormat = "%n%21s",
            outputTenderSingleFormat = "%-17s",
            outputDoubleFormat = "%21s%s",
            outputErrorDoubleNewLineFormat = "%-21s%n%s%n", outputEODFormat = "%n%s%s",
            changeFormat = "%-21s$%7s%n%s%n", outputItemTotalFormat = "%22s%7s%n",
        recipeStringFormat = "%n%22s%s%n%21s%s%n%21s%s%n%24s%7s%n";

    /**
     * Format for currency
     */
    private static DecimalFormat currencyFormat;

    /**
     * Aug. Constructor
     */
    Register(DecimalFormat moneyFormat, String fileName) {
        // Create Sale
        sale = new Sale();

        // Set currency format
        currencyFormat = moneyFormat;

        // Initialize ProductCatalog
        productCatalog = new ProductCatalog(fileName,currencyFormat);

        // Create observable lists
        productSpecificationSaleObservableList = FXCollections.observableList(productCatalog.getListOfSpecification());
        productSpecificationDeleteObservableList = FXCollections.observableList(productCatalog.getListOfSpecification());
        productSpecificationModifyObservableList = FXCollections.observableList(productCatalog.getListOfSpecification());
    }

    // Add product to sale, returns String for receiptTextArea
    public String addProductToSale (String codeInput, int quantityInput) {
        // Create return string
        String returnString = "";

        // Check if sale already created
        if (sale.checkIfEmpty()) {
            // Add line break
            returnString = "\n" + BREAK_LINE;
        }

        // Get product from code
        ProductSpecification addedSpecification = productCatalog.getProductSpecification(codeInput);

        // Find price amount * quantity
        BigDecimal productPriceTotal = (addedSpecification.getProductPrice().multiply(BigDecimal.valueOf(quantityInput)));

        // Add product to sale
        sale.addSalesLineItem(addedSpecification,quantityInput,productPriceTotal);

        // Concat returnString including line break, product code, product name, product quantity, and product total
        returnString = returnString.concat(String.format(recipeStringFormat,
                PRODUCT_CODE_RECEIPT_MESSAGE, addedSpecification.getProductCode(),
                PRODUCT_NAME_RECEIPT_MESSAGE, addedSpecification.getProductName(),
                PRODUCT_QUANTITY_RECEIPT_MESSAGE, quantityInput,
                PRODUCT_TOTAL_RECEIPT_MESSAGE, currencyFormat.format(productPriceTotal)));

        return returnString;
    }

    /**
     * Add product to Catalog
     */
    public void addProductCatalogProduct(String codeInput, String nameInput, Double priceInput) {
        // Add productSpecification to productCatalog
        productCatalog.addProductSpecification(codeInput,nameInput,BigDecimal.valueOf(priceInput));
    }

    /**
     * Delete product from Catalog
     */
    public void deleteProductCatalogProduct(String codeInput) {
        // Delete item from item list
        productCatalog.deleteProductSpecification(codeInput);
    }

    /**
     * Modify product from Catalog
     */
    public void modifyProductCatalogProduct(String codeInput, String nameInput, Double priceInput) {
        // Remove old productSpecification
        productCatalog.deleteProductSpecification(codeInput);

        // Add new productSpecification
        productCatalog.addProductSpecification(codeInput,nameInput,BigDecimal.valueOf(priceInput));
    }

    // Checks if code == key in hashmap, returns true if created, false if not
    public boolean checkIfCreate(String codeInput) {
        return productCatalog.getProductSpecification(codeInput) != null;
    }

    /**
     * Gets list of items from ProductCatalog
     *
     * @return String, list of products name, code, and price
     */
    public String listItems() {
        return productCatalog.getProductsStrings(currencyFormat);
    }

    public ObservableList<ProductSpecification> getProductSpecificationSaleObservableList() {
        return productSpecificationSaleObservableList;
    }

    public ObservableList<ProductSpecification> getProductSpecificationDeleteObservableList() {
        return productSpecificationDeleteObservableList;
    }

    public ObservableList<ProductSpecification> getProductSpecificationModifyObservableList() {
        return productSpecificationModifyObservableList;
    }
}