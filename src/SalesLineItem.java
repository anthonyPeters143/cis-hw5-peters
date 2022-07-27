import java.math.BigDecimal;

/**
 * @author Anthony Peters
 *
 * Holds code, name, quantity, taxable state, and total of products sold in Sale object
 */

public class SalesLineItem implements Comparable<SalesLineItem>{

    /**
     * Global variables
     */
    private BigDecimal productTotal;
    private String productCode, productName;
    private int productQuantity;
    private boolean productTaxable;

    /**
     * Non-Aug. Constructor
     * Creates and sets code and name to empty strings, total and quantity to 0, and taxable is set to false
     */
    SalesLineItem() {
        productCode = "";
        productName = "";
        productTotal = BigDecimal.valueOf(0.00);
        productQuantity = 0;
        productTaxable = false;
    }

    /**
     * Aug. Constructor
     * Creates SalesLineItem using inputted specification, quantity, and price
     *
     * @param specification ProductSpecification, product being sold
     * @param quantity int, amount of product
     * @param total BigDecimal, price total
     */
    SalesLineItem(ProductSpecification specification, int quantity, BigDecimal total) {
        productCode = specification.getProductCode();
        productName = specification.getProductName();
        productTotal = total;
        productQuantity = quantity;
        productTaxable = specification.getProductTaxable();
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
     * Get product total
     *
     * @return BigDecimal, productTotal
     */
    public BigDecimal getProductTotal() {
        return productTotal;
    }

    /**
     * Get product quantity
     *
     * @return int, productQuantity
     */
    public int getProductQuantity() {
        return productQuantity;
    }

    /**
     * Gets taxable values
     *
     * @return boolean, taxable values (true = taxable) (false = nontaxable)
     */
    public boolean isProductTaxable() {
        return productTaxable;
    }

    /**
     * Set product code to input
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
     * Sets product total to input
     *
     * @param productTotal BigDecimal, productTotal
     */
    public void setProductTotal(BigDecimal productTotal) {
        this.productTotal = productTotal;
    }

    /**
     * Sets product quantity to input
     *
     * @param productQuantity int, productQuantity
     */
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    /**
     * Overrides compareTo method to compare productNames
     *
     * @param o the object to be compared.
     * @return int, (0 > this) is bigger, (0 = this) is equal, (0 < this) is smaller
     */
    @Override
    public int compareTo(SalesLineItem o) {
        return (this.getProductName().compareTo(o.getProductName()));
    }
}