import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Anthony Peters
 *
 * Holds and drives ProductCatalog and Sale object from GUI driver. Allows for adding, deleting, and modifying
 * ProductSpecifications productCatalog as well as saving productCatalog to file, adding products, accesing subtotal
 * fields, checking size, and creating recipt strings from sale object.
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

    /**
     * Observable list of productCatalog
     */
    ObservableList<ProductSpecification> productSpecificationObservableList;

    /**
     * Preset strings used in register strings
     */
    private static final String
            BREAK_LINE                          = "--------------------",
            PRODUCT_CODE_RECEIPT_MESSAGE        = "Product Code : ",
            PRODUCT_NAME_RECEIPT_MESSAGE        = "Product Name : ",
            PRODUCT_QUANTITY_RECEIPT_MESSAGE    = "Product Quantity : ",
            PRODUCT_TOTAL_RECEIPT_MESSAGE       = "Product Total : $",
            RECEIPT_LINE                        = "----------------------------",
            CHANGE_AMOUNT                       = "Change";

    /**
     * String formats used in Register
     */
    private static final String recipeStringFormat = "%n%22s%s%n%21s%s%n%21s%s%n%24s%7s%n";

    /**
     * Format for currency pass to Register from GUI driver
     */
    private static DecimalFormat currencyFormat;

    /**
     * Aug. Constructor.
     * Initialize sale and productCatalog objects, sets currency format to input, updates observable list from
     * productCatalog
     *
     * @param moneyFormat DecimalFormat, currency format to be used in strings
     * @param fileName String, fileName of productCatalog
     */
    Register(DecimalFormat moneyFormat, String fileName) {
        // Create Sale
        sale = new Sale();

        // Set currency format
        currencyFormat = moneyFormat;

        // Initialize ProductCatalog
        productCatalog = new ProductCatalog(fileName,currencyFormat);

        // Update observable lists
        updateList();
    }

    /**
     * Takes inputs from GUI driver to add productSpecification, product total, and quantity to sale object. Returns
     * string to GUI of code, name, quantity, and total of productSpecification
     *
     * @param codeInput String, code input
     * @param quantityInput int, quantity input
     * @return String, code, name, quantity, and total of productSpecification added to sale
     */
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
     * Updates productSpecification observable list from productCatalog
     */
    public void updateList() {
        productSpecificationObservableList = FXCollections.observableList(productCatalog.getListOfSpecification());
    }

    /**
     * Returns size of salesLineItem list from sale object
     *
     * @return int, size of salesLineItem
     */
    public int checkSaleAmount() {
        return sale.checkSize();
    }

    /**
     * Resets sale object fields
     */
    public void resetSale() {
        sale.resetSale();
    }

    /**
     * Saves productCatalog from productCatalog
     */
    public void saveCatalog() {
        productCatalog.updateFileFromData();
    }

    /**
     * Gets receipt strings from sale object, formats and inputs change amount into string. Then returns receipt string
     *
     * @param changeAmountInput, Double change amount input
     * @return String, receipt string
     */
    public String checkOutReceiptString(Double changeAmountInput) {
        String topReceiptString = sale.createReceipt(currencyFormat);

        String bottomReceiptString = String.format("%n%-25s$%7s%n%s%n",
                CHANGE_AMOUNT,
                currencyFormat.format(changeAmountInput),
                RECEIPT_LINE);

        return topReceiptString + bottomReceiptString;
    }

    // Check if tendered amount is bigger than subtotalTax, If so

    /**
     * Compares tender input against sale checkout total. Returns boolean true if input value is equal or bigger than
     * checkout total, false if not
     *
     * @param tenderInput Double, tender input
     * @return boolean, true = input is bigger or equal to checkout total / false = not
     */
    public boolean checkOut (Double tenderInput) {
        // Convert tender input to BigDecimal and check if value is bigger or equal to subtotalTax field of Sale
        return sale.checkCheckoutTotal(BigDecimal.valueOf(tenderInput));
    }

    /**
     * Adds code, name, and price inputs as a productSpecification to productCatalog then updates observable list
     *
     * @param codeInput String, code input
     * @param nameInput String, name input
     * @param priceInput Double, price input
     */
    public void addProductCatalogProduct(String codeInput, String nameInput, Double priceInput) {
        // Add productSpecification to productCatalog
        productCatalog.addProductSpecification(codeInput,nameInput,BigDecimal.valueOf(priceInput));

        // Update observable lists
        updateList();
    }

    /**
     * Removes code input productSpecification from productCatalog then updates observable list
     *
     * @param codeInput String, code input
     */
    public void deleteProductCatalogProduct(String codeInput) {
        // Delete item from item list
        productCatalog.deleteProductSpecification(codeInput);

        // Update observable lists
        updateList();
    }

    /**
     * Removes old productSpecification from productCatalog then inputs code, name, and price as new
     * productSpecification to productCatalog then updates observable list
     *
     * @param codeInput String, code input
     * @param nameInput String, name input
     * @param priceInput Double, price input
     */
    public void modifyProductCatalogProduct(String codeInput, String nameInput, Double priceInput) {
        // Remove old productSpecification
        productCatalog.deleteProductSpecification(codeInput);

        // Add new productSpecification
        productCatalog.addProductSpecification(codeInput,nameInput,BigDecimal.valueOf(priceInput));

        // Update observable lists
        updateList();
    }

    /**
     * Checks if code input has been used before in productCatalog. Returns true if created before, false if not
     *
     * @param codeInput String, code input
     * @return boolean, true = created before / false = not
     */
    public boolean checkIfCreate(String codeInput) {
        return productCatalog.getProductSpecification(codeInput) != null;
    }

    /**
     * Returns observableList of productSpecifications
     *
     * @return ObservableList<ProductSpecification>, observableList of productSpecifications
     */
    public ObservableList<ProductSpecification> getProductSpecificationObservableList() {
        return productSpecificationObservableList;
    }

    /**
     * Returns subtotal from sale object
     *
     * @return BigDecimal, sale subtotal
     */
    public BigDecimal getSubtotal() {
        return sale.getSubtotal();
    }

    /**
     * Returns subtotalTax from sale object
     *
     * @return BigDecimal, sale subtotalTax
     */
    public BigDecimal getSubtotalTax() {
        return sale.getSubtotalTax();
    }

    /**
     * Returns end of day total from sale object
     *
     * @return BigDecimal, sale end of day total
     */
    public BigDecimal getEOD() {
        return sale.getEodTotal();
    }
}