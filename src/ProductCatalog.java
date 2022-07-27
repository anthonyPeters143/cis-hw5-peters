import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
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

    private RandomAccessFile randomAccessProductFile;

    /**
     * Aug. Constructor
     * Initialize productCatalogHashMap and add productSpecifications from file
     *
     * @param fileName String, file name
     */
    ProductCatalog(String fileName) {

        // Initialize Product HashMap
        productCatalogHashMap = new HashMap<>();

        try {
            // Create randomAccessFile
            randomAccessProductFile = new RandomAccessFile("src/" + fileName, "rw");

            // Input data from file
            updateDataFromFile();

        } catch (IOException ioException) {
            // File input failed
            ioException.printStackTrace();
        }




    }

    // Updates data to productCatalogHashMap from random access file
    public void updateDataFromFile() {
        try {
            // Set RandomAccessFile to beginning
            randomAccessProductFile.seek(0);

            String randomAccessFileString = randomAccessProductFile.readLine();
            String[] randomAccessFileStringSplit = randomAccessFileString.split(Pattern.quote(","));

            // While loop using index and input file data into itemArray
            int index = 0;

            while (index < randomAccessFileStringSplit.length) {
                productCatalogHashMap.put(randomAccessFileStringSplit[index],
                        new ProductSpecification(randomAccessFileStringSplit[index],
                                randomAccessFileStringSplit[index + 1],
                                BigDecimal.valueOf(Double.parseDouble(randomAccessFileStringSplit[index+2])))
                );

                // Increasing index counter
                index+=3;
            }

        } catch (IOException ioException) {
            // File input failed
            ioException.printStackTrace();
        }
    }

    public void updateFileFromData() {
        try {
            // Set RandomAccessFile to beginning
            randomAccessProductFile.seek(0);

            Set<Map.Entry<String, ProductSpecification>> randomAccessProductArray = productCatalogHashMap.entrySet();

            int index = 0;
            String productString = "";
            // Set up and pass data to file
            while (index < randomAccessProductArray.size()) {
                productString = randomAccessProductArray[index] + "," + randomAccessProductArray[index + 1] + "," + randomAccessProductArray[index+2];
            }

        } catch (IOException ioException) {
            // File input failed
            ioException.printStackTrace();
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