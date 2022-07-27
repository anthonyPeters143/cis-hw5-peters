import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author Anthony Peters
 *
 * Holds HashMap of productCatalog, inputs data from file when created, allows for finding products from keys, adding or
 * deleting of products from HashMap, as well as creating a string of all products.
 */

public class ProductCatalog {

    /**
     * HashMap for storing ProductSpecifications
     */
    private Map<String, ProductSpecification> productCatalogHashMap;

    /**
     * Aug. Constructor
     * Initialize productCatalogHashMap and add productSpecifications from file
     *
     * @param fileName String, file name
     */
    ProductCatalog(String fileName) {

        // Initialize Product HashMap
        productCatalogHashMap = new HashMap<>();

        // Input data from file
        try {
            // Set up scanner from file
            Scanner productCatalogScanner = new Scanner(new File("src/" + fileName));

            // Scan in input and split by new line commas
            String productCatalogLine = productCatalogScanner.nextLine();
            String[] productCatalogSplitImportArray = productCatalogLine.split(Pattern.quote(","));

            // For loop to index and input file data into itemArray
            // Input code, name, price into Items objects in the array, advance index by 1, split index by 4
            for (int index = 0, splitIndex = 0; index < 10; index+=1, splitIndex+=3) {
                // Create ProductSpecification object and place it in productCatalogHashMap
                productCatalogHashMap.put(productCatalogSplitImportArray[splitIndex],
                        new ProductSpecification(productCatalogSplitImportArray[splitIndex],
                                String.valueOf(productCatalogSplitImportArray[splitIndex+1]),
                                BigDecimal.valueOf(Double.parseDouble(productCatalogSplitImportArray[splitIndex+2]))));
            }
            // Close scanner
            productCatalogScanner.close();

        } catch (Exception exception) {
            // File input failed
            exception.printStackTrace();
        }
    }

    /**
     * Adds productSpecification using code, name, and price inputs to productCatalogHashMap
     *
     * @param codeInput String, code input
     * @param nameInput String, name input
     * @param priceInput BigDecimal, price input
     */
    public void addProductSpecification(String codeInput, String nameInput, BigDecimal priceInput) {
        productCatalogHashMap.put(codeInput,new ProductSpecification(codeInput,nameInput,priceInput));
    }

    /**
     * Removes productSpecification from productCatalogHashMap, found using code
     *
     * @param codeInput String, code input used as key to find product
     */
    public void deleteProductSpecification(String codeInput) {
        productCatalogHashMap.remove(codeInput);
    }

    /**
     * Creates and returns a String of all product's codes, names, and prices
     *
     * @param currentFormat DecimalFormat, format for currency
     * @return String, formatted string of header and all products
     */
    public String getProductsStrings(DecimalFormat currentFormat) {
        // Declare format strings
        String codeNamePriceLabelFormat = "%-11s %-15s %-12s%n", codeNamePriceListFormat = "%-11s %-15s %-8s%n";
        String returnString = "";

        // Add top of list labels
        returnString = returnString.concat(
                String.format(codeNamePriceLabelFormat,
                        "item code",
                        "item name",
                        "unit price"));

        // Loop through productCatalog for code, name, and price of each product
        for (Map.Entry<String, ProductSpecification> productCatalogTracker : productCatalogHashMap.entrySet()) {
            returnString = returnString.concat(
                    String.format(codeNamePriceListFormat,
                            productCatalogTracker.getValue().getProductCode(),
                            productCatalogTracker.getValue().getProductName(),
                            currentFormat.format(productCatalogTracker.getValue().getProductPrice())));
        }

        return returnString;
    }

    /**
     * Searches for a ProductSpecification object using a giving key, which is equal to the item's code. If found will
     * return object else will return null
     *
     * @param key String, item's code
     * @return ProductSpecification if found, else null
     */
    public ProductSpecification getProductSpecification(String key) {
        return productCatalogHashMap.getOrDefault(key, null);
    }
}