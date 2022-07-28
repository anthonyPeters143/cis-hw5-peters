import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Anthony Peters
 *
 * Holds code, name, price, and taxable state for products
 */

public class ProductSpecification {

    /**
     * Global variables
     */
    private String productCode, productName;
    private BigDecimal productPrice;
    private boolean productTaxable;

    /**
     * Non-Aug. Constructor
     * Sets code and name to empty strings, price to 0, and taxable is set to false
     */
    ProductSpecification() {
        productCode = "";
        productName = "";
        productPrice = BigDecimal.valueOf(0.00);
        productTaxable = false;
    }

    /**
     * Aug. Constructor
     * Creates new ProductSpecification using inputted code, name, and price
     *
     * @param code String, code input
     * @param name String, name input
     * @param price BigDecimal, price input
     */
    ProductSpecification(String code, String name, BigDecimal price) {
        productCode = code;
        productName = name;
        productPrice = price;
        productTaxable = (code.charAt(0) == 'A');
    }

    /**
     * Get product code
     *
     * @return String, productCode
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Get product name
     *
     * @return String, productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Get product price
     *
     * @return BigDecimal, productPrice
     */
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * Get taxable value
     *
     * @return boolean, taxable state (true = taxable) (false = nonTaxable)
     */
    public boolean getProductTaxable() {
        return productTaxable;
    }

    /**
     * Sets product code to input
     *
     * @param productCode String, productCode
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * Sets product name to input
     *
     * @param productName String, productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Sets product price to input
     *
     * @param productPrice BigDecimal, productPrice
     */
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

}