import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
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

    private RandomAccessProductFile randomAccessProductFile;

    private DecimalFormat currencyFormat;

    /**
     * Aug. Constructor
     * Initialize productCatalogHashMap and add productSpecifications from file
     *
     * @param fileName String, file name
     */
    ProductCatalog(String fileName, DecimalFormat decimalFormat) {

        // Initialize Product HashMap
        productCatalogHashMap = new HashMap<>();

        // Set currency format
        currencyFormat = decimalFormat;

        try {
            // Create randomAccessFile
            randomAccessProductFile = new RandomAccessProductFile("src/" + fileName, "rw");

            // Input data from file
            updateDataFromFile();

            updateFileFromData();

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

            // Create set of Specifications
            ArrayList<ProductSpecification> listOfSpecification = new ArrayList<>(productCatalogHashMap.values());

//            // NEED TO SET UP
//            listOfSpecification.sort();

            int index = 0, length = 0;
            String productString = "", prevString = "";

            while (index < listOfSpecification.size()) {
                productString = listOfSpecification.get(index).getProductCode() + "," +
                        listOfSpecification.get(index).getProductName() + "," +
                        currencyFormat.format(listOfSpecification.get(index).getProductPrice()) + ",";

                randomAccessProductFile.writeProductToRandomAccessProductFile(length,productString);

                prevString = productString;

                length += productString.length();

                // Increase index counter
                index += 1;
            }

            // Remove any extra bytes from end of randomAccessProductFile
            // Check if file length is longer than new file input
            if (length > randomAccessProductFile.length()) {
                StringBuilder newInput = new StringBuilder();

                // Set RandomAccessFile to beginning
                randomAccessProductFile.seek(0);

                // Copy up to new length then reset length of file
                while (randomAccessProductFile.getFilePointer() < length) {
                    // Append string to buffer
                    newInput.append(randomAccessProductFile.readLine());
                }

                // Reset file
                randomAccessProductFile.setLength(0);

                // Set RandomAccessFile pointer to beginning and write string input into file
                randomAccessProductFile.writeProductToRandomAccessProductFile(0,newInput.toString());
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