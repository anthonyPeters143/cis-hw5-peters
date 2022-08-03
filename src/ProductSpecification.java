import java.math.BigDecimal;

/**
 * @author Anthony Peters
 *
 * Holds code, name, price, and taxable state for products
 */

public class ProductSpecification implements Comparable<ProductSpecification> {

    /**
     * String of product code and name
     */
    private String productCode, productName;

    /**
     * BigDecimal value of productPrice
     */
    private BigDecimal productPrice;

    /**
     * Boolean taxable value
     */
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

    /**
     * Overrides toString method to create string of productSpecification
     *
     * @return String value of productName,productCode
     */
    @Override
    public String toString() {
        return productName + "," + productCode;
    }

    /**
     * Overrides compareTo method to compare product codes
     *
     * @param o the object to be compared.
     * @return int, greater = greater than 0, equal = 0, lesser = less than 0
     */
    @Override
    public int compareTo(ProductSpecification o) {
        return (this.getProductCode().compareTo(o.getProductCode()));
    }

}