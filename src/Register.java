import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Scanner;

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
            THANK_YOU                           = "Thanks for using POST system. Goodbye.",
            FILE_NAME_KEY                       = "item.txt";

    /**
     * String formats used in Register
     */
    private static final String outputSingleFormat = "%-13s", outputSingleNewLineAfterFormat = "%-21s%n",
            outputSingleShortNewLineBeforeFormat = "%n%-13s",
            outputSingleNewLineBeforeFormat = "%n%21s",
            outputTenderSingleFormat = "%-17s",
            outputDoubleFormat = "%21s%s",
            outputErrorDoubleNewLineFormat = "%-21s%n%s%n", outputEODFormat = "%n%s%s",
            changeFormat = "%-21s$%7s%n%s%n", outputItemTotalFormat = "%22s%7s%n";

    /**
     * Format for currency
     */
    private static final DecimalFormat currencyFormat = new DecimalFormat("#,###.00");

    /**
     * Non-Aug. Constructor
     */
    Register() {

    }

    /**
     * Register driver class, prompts for file name, then runs sale loop, then prompts for data alteration
     */
    void run() {
        String fileName = "";
        boolean saleQuitFlag = false, alterQuitFlag = false, fileNameFlag = false;

        // Create systemIn Scanner
        Scanner systemInScanner = new Scanner(System.in);

        // Welcome message
        System.out.printf(outputSingleShortNewLineBeforeFormat,
                WELCOME_MESSAGE);

        // Get file name loop
        do {
            // Prompt for name
            System.out.printf(outputSingleShortNewLineBeforeFormat,
                    FILENAME_MESSAGE);
            fileName = systemInScanner.next();

            // Check if equal to FILE_NAME_KEY
            if (Objects.equals(fileName, FILE_NAME_KEY)) {
                // File name is correct
                fileNameFlag = true;

                // Initialize ProductCatalog
                productCatalog = new ProductCatalog(fileName);

            } else {
                // File name is incorrect
                System.out.printf(outputErrorDoubleNewLineFormat,
                        INPUT_ERROR,
                        FILE_ERROR);
            }

        } while (!fileNameFlag);

        // Sale loop
        do {
            // Initialize Sale
            sale = new Sale();

            // Prompt for sale
            if (checkOut(systemInScanner) == 1) {
                // Quit sale
                saleQuitFlag = true;
            }

        } while (!saleQuitFlag);

        // Alter loop
        do {
            // Prompt for altering product catalog
            alterQuitFlag = alterCatalog(systemInScanner);

        } while (!alterQuitFlag);

        // Output thank you message
        System.out.printf(outputSingleNewLineBeforeFormat,
                THANK_YOU);

        // Close scanner
        systemInScanner.close();
    }

    /**
     * Prompts and checks if user wanted to create a sale, then run findCode method if Y, prints end of day total if N
     *
     * @param systemInScanner Scanner, input from system.in
     * @return returnNum, used in driver class to determine if loop should rerun
     */
    public int checkOut(Scanner systemInScanner) {
        // Declare and Initialization
        String eodFormat = "%1$8s";
        String userInput = "";
        int returnInt;

        // Prompt and check input for 'Y'/'y' or 'N'/'n'
        try {
            // Reset quit flag and return int
            returnInt = 0;

            // Loop input and check till correct
            do {
                // Output Beginning Message
                System.out.printf(outputSingleNewLineBeforeFormat,
                        BEGINNING_SALE_MESSAGE);

                // User input
                userInput = systemInScanner.next();

                // Test if input is 1 char long and == Y or y
                if (userInput.matches("[Yy]{1}")) {

                    // Input Correct, user = yes
                    returnInt = 2;

                    // Print text break
                    System.out.print(BREAK_LINE);

                    // New sale
                    findCode(systemInScanner);
                }
                // Test if input is 1 char long and == N or n
                else if (userInput.matches("[Nn]{1}")){

                    // Input Correct, user = no
                    returnInt = 1;

                    // EOD earnings
                    if (sale == null) {
                        System.out.print(EOD_MESSAGE + "0.00");
                    } else {
                        System.out.print(EOD_MESSAGE + String.format(eodFormat, currencyFormat.format(sale.getEodTotal())));
                    }

                }
                else {
                    // Input incorrect
                    // Print incorrect message
                    System.out.printf(outputErrorDoubleNewLineFormat,
                            INPUT_ERROR,
                            SALE_ERROR);
                }

            } while (returnInt == 0 || returnInt == 2);

            // Return returnInt
            return returnInt;

        } catch (Exception e) {
            // Input incorrect
            e.printStackTrace();

            return 0;
        }
    }

    /**
     * Prompts user for code input, then checks if valid. If valid then runs findQuantity, if -1 then prints receipt and
     * prompts for change, if 0000 then prints list of products in ProductCatalog.
     *
     * @param systemInScanner Scanner, input from system.in
     */
    public void findCode(Scanner systemInScanner) {
        // Declare and Initialization
        ProductSpecification specification;
        String userInput;
        boolean codeInputFlag, quitFlag = false, tenderCorrectFlag = false;

        // Loop till code input is valid
        do {
            // Reset code flag
            codeInputFlag = false;

            // Prompt for code input
            System.out.printf(outputSingleNewLineBeforeFormat,
                    ENTER_CODE_MESSAGE);

            // User input
            userInput = systemInScanner.next();

            // Check if code input is A or B + [###]
            if (userInput.matches("[AB]\\d\\d\\d")) {
                // Find item
                specification = productCatalog.getProductSpecification(userInput);

                if (specification == null) {
                    // Item not found
                    // Output incorrect code message
                    System.out.printf(outputErrorDoubleNewLineFormat,
                            INPUT_ERROR,
                            CODE_ERROR);

                } else {
                    // Code input valid
                    // Set input flag to true
                    codeInputFlag = true;

                    // Item found
                    System.out.printf(outputDoubleFormat,
                            ITEM_NAME_MESSAGE,
                            specification.getProductName()
                    );

                    // Run findQuantity
                    findQuantity(systemInScanner, specification);
                }
            } else if (userInput.equals("-1")) {
                // Change flags for quiting and code input to true
                quitFlag = true;
                codeInputFlag = true;

                // Print receipt top
                System.out.println(RECEIPT_LINE);
                System.out.println(sale.createReceipt(currencyFormat));

                // Loop till tendered amount is larger than total with tax
                do {
                    try {
                        // Prompt for tender amount
                        System.out.printf(outputTenderSingleFormat,
                                TENDER_MESSAGE);

                        // User input
                        BigDecimal tenderAmount = BigDecimal.valueOf(Double.parseDouble(systemInScanner.next()));
                        BigDecimal subtotalTax = sale.getSubtotalTax();

                        // Tender amount is correct
                        if (tenderAmount.compareTo(subtotalTax) >= 0) {
                            tenderCorrectFlag = true;

                            // Change
                            // Find change by subtracting tenderAmount by Total with tax
                            System.out.printf(changeFormat,
                                    CHANGE_AMOUNT,
                                    currencyFormat.format(tenderAmount.subtract(subtotalTax)),
                                    RECEIPT_LINE
                            );

                            // Reset sale
                            sale.resetSale();
                        }
                        else {
                            // Tender is wrong
                            System.out.printf(outputSingleNewLineAfterFormat,
                                    TENDER_AMOUNT_TOO_SMALL);
                        }
                    } catch (Exception e) {
                        // Tender is wrong
                        System.out.printf(outputSingleNewLineAfterFormat,
                                TENDER_AMOUNT_WRONG);
                    }

                } while (!tenderCorrectFlag);

            } else if (userInput.equals("0000")) {
                // List products
                System.out.println(listItems());

            } else {
                // Output incorrect code message
                System.out.printf(outputErrorDoubleNewLineFormat,
                        INPUT_ERROR,
                        CODE_ERROR);
            }

        } while (!codeInputFlag || !quitFlag);
    }

    /**
     * Prompts user for quantity input, then checks if valid. If valid then calculate price and add product to sale.
     *
     * @param systemInScanner Scanner, input from system.in
     * @param specification ProductSpecification, product from code in findCode method
     */
    public void findQuantity(Scanner systemInScanner, ProductSpecification specification) {
        // Declare and Initialization
        int productQuantity;
        BigDecimal productPrice;
        boolean quantityInputFlag = false;

        do {
            try {
                // Prompt for item quantity
                System.out.printf(outputSingleNewLineBeforeFormat,
                        ENTER_QUANTITY_MESSAGE);

                // User input
                productQuantity = Integer.parseInt(systemInScanner.next());

                // Check if quantity is [1-100]
                if (productQuantity > 0 && productQuantity <= 100) {
                    // Quantity input valid
                    quantityInputFlag = true;

                    // Calc price
                    productPrice = (specification.getProductPrice()).multiply(BigDecimal.valueOf(productQuantity));

                    // Add Item price to Sale object
                    sale.addSalesLineItem(specification,productQuantity,productPrice);

                    // Output item total
                    System.out.printf(outputItemTotalFormat,
                            ITEM_TOTAL_MESSAGE,
                            currencyFormat.format(productPrice));
                }
                else {
                    // Quantity Input Incorrect
                    // Print incorrect message
                    System.out.printf(outputErrorDoubleNewLineFormat,
                            INPUT_ERROR,
                            QUANTITY_ERROR);
                }

            } catch (Exception e) {
                // Quantity input is incorrect
                System.out.printf(outputErrorDoubleNewLineFormat,
                        INPUT_ERROR,
                        QUANTITY_ERROR);
            }
        } while (!quantityInputFlag);
    }

    /**
     * Prompts user to select between Add, Delete, Modify, or Quit using input, checks if valid.
     *
     * @param systemInScanner Scanner, input from system.in
     * @return boolean, returns true if finished else false
     */
    public boolean alterCatalog(Scanner systemInScanner) {

        // Prompt for Add, Delete, Modify, or Quit Items
        System.out.printf(outputSingleNewLineBeforeFormat,
                UPDATE_PROMPT_MESSAGE);

        // User input
        String userInput = systemInScanner.next();

        switch (userInput) {
            case "A" -> {
                // Add
                addProductCatalogItem(systemInScanner);
            }
            case "D" -> {
                // Delete
                deleteProductCatalogItem(systemInScanner);
            }
            case "M" -> {
                // Modify
                modifyProductCatalogItem(systemInScanner);
            }
            case "Q" -> {
                // List products
                System.out.println(listItems());

                // Quit
                return true;
            }
            default -> {
                // Input invalid
                System.out.printf(outputErrorDoubleNewLineFormat,
                        INPUT_ERROR,
                        UPDATE_ERROR);
            }
        }

        return false;
    }

    /**
     * Prompts user for code, name, and price input, then checks if valid. If valid then add product to ProductCatalog.
     *
     * @param systemInScanner Scanner, input from system.in
     */
    private void addProductCatalogItem(Scanner systemInScanner) {
        // Declare and Initialization
        BigDecimal priceInput = BigDecimal.valueOf(0);
        String userInput, codeInput = "", nameInput = "";
        boolean codeInputFlag = false, nameInputFlag = false, priceInputFlag = false;

        // Prompt for code, name, price
        // Find code
        do {
            // Prompt for code input
            System.out.printf(outputSingleFormat,
                    UPDATE_CODE_PROMPT);

            // User input
            userInput = systemInScanner.next();

            // Check if code input is A or B + [###]
            if (userInput.matches("[AB]\\d\\d\\d")) {
                // Code input valid, check if item already created
                if (productCatalog.getProductSpecification(userInput) == null) {
                    // Not created before
                    codeInputFlag = true;
                    codeInput = userInput;

                } else {
                    // Created before
                    System.out.printf(outputSingleNewLineAfterFormat,
                            ERROR_ITEM_EXIST);
                }
            } else {
                // Code input invalid
                System.out.printf(outputErrorDoubleNewLineFormat,
                        INPUT_ERROR,
                        UPDATE_CODE_ERROR);
            }

        } while (!codeInputFlag);

        // Find name
        do {
            // Prompt for name input
            System.out.printf(outputSingleFormat,
                    UPDATE_NAME_PROMPT);

            // Clear scanner buffer
            systemInScanner.nextLine();

            // User input
            userInput = systemInScanner.nextLine();

            // Check if name is input
            if (!userInput.equals("")) {
                // Item name not created before
                nameInputFlag = true;
                nameInput = userInput;
            }
        } while (!nameInputFlag);

        // Find price
        do {
            // Prompt for code input
            System.out.printf(outputSingleFormat,
                    UPDATE_PRICE_PROMPT);

            // User input
            userInput = systemInScanner.next();

            try {
                if (Double.parseDouble(userInput) > 0) {
                    // Input valid
                    priceInputFlag = true;
                    priceInput = BigDecimal.valueOf(Double.parseDouble(userInput));

                } else {
                    // Input invalid
                    System.out.printf(outputErrorDoubleNewLineFormat,
                            INPUT_ERROR,
                            PRICE_ERROR);

                }
            } catch (Exception e) {
                // Price input invalid
                System.out.printf(outputErrorDoubleNewLineFormat,
                        INPUT_ERROR,
                        PRICE_ERROR);
            }
        } while (!priceInputFlag);

        // Add product specification to product catalog
        productCatalog.addProductSpecification(codeInput,nameInput,priceInput);

        // Output success message
        System.out.printf(outputSingleNewLineAfterFormat,
                UPDATE_ADD_SUCCESSFUL);
    }

    /**
     * Prompt user for code input, then check if valid. If valid then remove product matching code from productCatalog
     *
     * @param systemInScanner Scanner, input from system.in
     */
    private void deleteProductCatalogItem(Scanner systemInScanner) {
        // Declare and Initialization
        String userInput, codeInput = "";
        boolean codeInputFlag = false;

        // prompt for code, name, price
        // Find code
        do {
            // Prompt for code input
            System.out.printf(outputSingleFormat,
                    UPDATE_CODE_PROMPT);

            // User input
            userInput = systemInScanner.next();

            // Check if code input is A or B + [###]
            if (userInput.matches("[AB]\\d\\d\\d")) {
                // Code input valid, check if item already created
                if (productCatalog.getProductSpecification(userInput) == null) {
                    // Not created before
                    System.out.printf(outputSingleNewLineAfterFormat,
                            UPDATE_ITEM_NOT_FOUND);

                } else {
                    // Created before
                    codeInputFlag = true;
                    codeInput = userInput;
                }
            } else {
                // Code input invalid
                System.out.printf(outputErrorDoubleNewLineFormat,
                        INPUT_ERROR,
                        UPDATE_CODE_ERROR);
            }

        } while (!codeInputFlag);

        // Delete item from item list
        productCatalog.deleteProductSpecification(codeInput);

        // Output success message
        System.out.printf(outputSingleNewLineAfterFormat,
                UPDATE_DELETE_SUCCESSFUL);
    }

    /**
     * Prompts user for code, name, and price input, then checks if valid. If valid then remove old product from
     * ProductCatalog and add new product to ProductCatalog.
     *
     * @param systemInScanner Scanner, input from system.in
     */
    private void modifyProductCatalogItem(Scanner systemInScanner) {
        // Declare and Initialization
        BigDecimal priceInput = BigDecimal.valueOf(0);
        String userInput, codeInput = "", nameInput = "";
        boolean codeInputFlag = false, nameInputFlag = false, priceInputFlag = false;

        // Prompt for code, name, price
        // Find code
        do {
            // Prompt for code input
            System.out.printf(outputSingleFormat,
                    UPDATE_CODE_PROMPT);

            // User input
            userInput = systemInScanner.next();

            // Check if code input is A or B + [###]
            if (userInput.matches("[AB]\\d\\d\\d")) {
                // Code input valid, check if item already created
                if (productCatalog.getProductSpecification(userInput) == null) {
                    // Not created before
                    System.out.printf(outputSingleNewLineAfterFormat,
                            UPDATE_ITEM_NOT_FOUND);

                } else {
                    // Created before
                    codeInputFlag = true;
                    codeInput = userInput;

                }
            } else {
                // Code input invalid
                System.out.printf(outputErrorDoubleNewLineFormat,
                        INPUT_ERROR,
                        UPDATE_CODE_ERROR);
            }

        } while (!codeInputFlag);

        // Find name
        do {
            // Prompt for code input
            System.out.printf(outputSingleFormat,
                    UPDATE_NAME_PROMPT);

            // Clear scanner buffer
            systemInScanner.nextLine();

            // User input
            userInput = systemInScanner.nextLine();

            // Check name if created before
            if (!userInput.equals("")) {
                // Item name not created before
                nameInputFlag = true;
                nameInput = userInput;
            }
        } while (!nameInputFlag);

        // Find price
        do {
            // Prompt for code input
            System.out.printf(outputSingleFormat,
                    UPDATE_PRICE_PROMPT);

            // User input
            userInput = systemInScanner.next();

            try {
                if (Double.parseDouble(userInput) > 0) {
                    // Input valid
                    priceInputFlag = true;
                    priceInput = BigDecimal.valueOf(Double.parseDouble(userInput));

                } else {
                    // Input invalid
                    System.out.printf(outputErrorDoubleNewLineFormat,
                            INPUT_ERROR,
                            PRICE_ERROR);

                }
            } catch (Exception e) {
                // Price input invalid
                System.out.printf(outputErrorDoubleNewLineFormat,
                        INPUT_ERROR,
                        PRICE_ERROR);
            }
        } while (!priceInputFlag);

        // Remove old productSpecification
        productCatalog.deleteProductSpecification(codeInput);

        // Add new productSpecification
        productCatalog.addProductSpecification(codeInput,nameInput,priceInput);

        // Output success message
        System.out.printf(outputSingleNewLineAfterFormat,
                UPDATE_MODIFY_SUCCESSFUL);
    }

    /**
     * Gets list of items from ProductCatalog
     *
     * @return String, list of products name, code, and price
     */
    private String listItems() {
        return productCatalog.getProductsStrings(currencyFormat);
    }

}