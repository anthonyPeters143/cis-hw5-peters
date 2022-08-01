// Conformance to the OO Design: xx %
// Support of item change: full/partial/none
// Support of random access file: yes/no
// Javadoc conformed comments on the classes, methods, and attributes: full/partial/no
// Handling wrong input and invalid input: full/partial/no
// Program does not crash with exceptions: crashes/does not crash
// Correct handling of payment and taxes: yes/partial/no
// Overall layout of GUI and ease of use: almost perfect/good enough/not good but works

// !!!!!!!!!!!!NEED TO FILL OUT BEFORE TURN IN

// Driver class for HW5

// VM OPTIONS --module-path "C:\Users\Anthony\SDKs\javafx-sdk-18.0.1\lib" --add-modules javafx.controls,javafx.fxml

import java.math.BigDecimal;
import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author Anthony Peters
 *
 * GUI Driver class used to create a Register object then run it, after it finishes running it will exit the program
 */

public class HW5_peters extends Application {

    private static Register register;

    /**
     * Observation  List
     */
    private static ObservableList<ProductSpecification> productSpecificationObservableList;

    /**
     * Product ComboBox
     */
    private static ComboBox<ProductSpecification> productSpecificationSaleComboBox,productSpecificationDeleteComboBox,
            productSpecificationModifyComboBox;

    /**
     * Scenes
     */
    private Scene addItemScene,deleteItemScene,modifyItemScene,dataAltScene,saleScene,mainScene;

    /**
     * Keys
     */
    private static String FILE_NAME_KEY   = "item.txt";

    /**
     * Listener update fields
     */
    private static String itemAltAddCodeInput,itemAltAddNameInput,itemAltAddPriceInput,
            itemAltModifyCodeInput,itemAltModifyNameInput,itemAltModifyPriceInput,
            itemSaleCodeInput,itemSaleNameInput,
            itemSalePriceInput,itemSaleQuantityInput,subtotalInput,subtotalTaxInput,tenderInput,
            RECEIPT_TEXT = "";
    private double eodTotalPrice = 0;

    /**
     * Formatting vars
     */
    private final Font titleFont = new Font("Lucida Sans Unicode",30),
            bodyFont = new Font("Lucida Sans Unicode",15),
            buttonFont = new Font("Lucida Sans Unicode",15),
            receiptFont = new Font("Lucida Sans Unicode",10);
    private final int sceneSpace = 13;

    /**
     * Format for currency
     */
    private static final DecimalFormat currencyFormat = new DecimalFormat("#,##0.00");

    @Override
    public void start(Stage primaryStage) throws Exception {

        String  TYPE_HERE_DEFAULT   = "Type here...",
                SELECT_COMBO_DEFAULT = "Select from above",

                MAIN_SCENE_TITLE    = "POST Register",
                EOD_TOTAL_TITLE     = "Total sale for the day is: $",

                ADD_BUTTON_TITLE        = "Add",
                DONE_BUTTON_TITLE       = "Done",
                NEW_SALE_BUTTON_TITLE   = "New Sale",
                SALE_BUTTON_TITLE       = "Sale",
                DATA_ALT_BUTTON_TITLE   = "Data Alteration",
                QUIT_BUTTON_TITLE       = "Quit",

                PRODUCT_CODE_TITLE  = "Product Code :",
                PRODUCT_NAME_TITLE  = "Product Name :",
                PRODUCT_PRICE_TITLE = "Product Price :",

                ITEM_NAME_TITLE     = "Item Name :",
                ITEM_PRICE_TITLE    = "Item Price :",
                ITEM_QUANTITY_TITLE = "Quantity :",
                ITEM_TOTAL_TITLE    = "Item total :",

                SUBTOTAL_TITLE          = "Sale Subtotal :",
                SUBTOTAL_TAX_TITLE      = "Sale Tax Subtotal (6%) :",
                TENDER_TITLE            = "Tendered Amount :",
                CHANGE_TITLE            = "Change :",

                TOTAL_FIELDS_DEFAULT = "0.00",

                CHECKOUT_BUTTON_TITLE   = "Checkout",

                DATA_ALT_TITLE          = "Data alteration",
                ITEM_SALE_TITLE         = "Item Sale",
                MAIN_TITLE              = "Welcome to Peter's Store!!!",

                ADD_PRODUCT_BUTTON_TITLE   = "Add Product",
                DELETE_PRODUCT_BUTTON_TITLE= "Delete Product",
                MODIFY_PRODUCT_BUTTON_TITLE   = "Modify Product",

                ADD_PRODUCT_TITLE    = "Add Product",
                DELETE_PRODUCT_TITLE = "Delete Product",
                MODIFY_PRODUCT_TITLE = "Modify Product",

                NEED_QUANTITY_ERROR = "Need Quantity";

        int RECEIPT_WIDTH = 260, RECEIPT_HEIGHT = 100;

        // Initializing itemComBox and file input
        initialize();

        // ************************************************************************************************************

        // Add scene
        // Create dataAlt add title/field VBox node
        Label itemAltAddCodeTitle = new Label(PRODUCT_CODE_TITLE);
        TextField itemAltAddCodeField = new TextField();
        itemAltAddCodeField.setPromptText(TYPE_HERE_DEFAULT);
        HBox itemAltAddCodeHB = new HBox(itemAltAddCodeTitle,itemAltAddCodeField);
        itemAltAddCodeTitle.setFont(bodyFont);
        itemAltAddCodeField.setFont(bodyFont);
        itemAltAddCodeHB.setSpacing(sceneSpace);
        itemAltAddCodeHB.setAlignment(Pos.CENTER);

        Label itemAltAddNameTitle = new Label(PRODUCT_NAME_TITLE);
        TextField itemAltAddNameField = new TextField();
        itemAltAddNameField.setPromptText(TYPE_HERE_DEFAULT);
        HBox itemAltAddNameHB = new HBox(itemAltAddNameTitle,itemAltAddNameField);
        itemAltAddNameTitle.setFont(bodyFont);
        itemAltAddNameField.setFont(bodyFont);
        itemAltAddNameHB.setSpacing(sceneSpace);
        itemAltAddNameHB.setAlignment(Pos.CENTER);

        Label itemAltAddPriceTitle = new Label(PRODUCT_PRICE_TITLE);
        TextField itemAltAddPriceField = new TextField();
        itemAltAddPriceField.setPromptText(TYPE_HERE_DEFAULT);
        HBox itemAltAddPriceHB = new HBox(itemAltAddPriceTitle,itemAltAddPriceField);
        itemAltAddPriceTitle.setFont(bodyFont);
        itemAltAddPriceField.setFont(bodyFont);
        itemAltAddPriceHB.setSpacing(sceneSpace);
        itemAltAddPriceHB.setAlignment(Pos.CENTER);

        // Create add item TF VBox
        VBox itemAltAddTitleFieldVB = new VBox(itemAltAddCodeHB,itemAltAddNameHB,itemAltAddPriceHB);
        itemAltAddTitleFieldVB.setSpacing(sceneSpace);
        itemAltAddTitleFieldVB.setAlignment(Pos.CENTER);

        // Create add item scene title
        Label addItemAltSceneTitle = new Label(ADD_PRODUCT_TITLE);
        addItemAltSceneTitle.setFont(titleFont);

        // Create Add/Done buttons and button HBox
        Button addItemButton = new Button(ADD_PRODUCT_BUTTON_TITLE);
        addItemButton.setOnAction(e -> {
            // Try to add productSpecification to product catalog, if valid then fields will be erased
            if ( checkAddProduct(itemAltAddCodeField.getText(),itemAltAddNameField.getText(),itemAltAddPriceField.getText()) ) {
                itemAltAddCodeField.setText("");
                itemAltAddNameField.setText("");
                itemAltAddPriceField.setText("");
            }
        });
        Button addDoneButton = new Button(DONE_BUTTON_TITLE);
        addDoneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox addButtonHB = new HBox(addItemButton,addDoneButton);
        addItemButton.setFont(buttonFont);
        addDoneButton.setFont(buttonFont);
        addButtonHB.setSpacing(sceneSpace);
        addButtonHB.setAlignment(Pos.CENTER);

        // Create add item node using add scene title, shared dataAlt VBox, and add button HBox
        VBox addItemVB = new VBox(addItemAltSceneTitle,itemAltAddTitleFieldVB,addButtonHB);
        addItemVB.setSpacing(sceneSpace);
        addItemVB.setAlignment(Pos.CENTER);

        // Create scene
        addItemScene = new Scene(addItemVB);

        // ************************************************************************************************************

        // Delete scene
        // Create delete item scene title
        Label deleteItemAltSceneTitle = new Label(DELETE_PRODUCT_TITLE);
        deleteItemAltSceneTitle.setFont(titleFont);

        // Create dataAlt delete title/field VBox node
        Label itemAltDeleteCodeTitle = new Label(PRODUCT_CODE_TITLE);
        TextField itemAltDeleteCodeField = new TextField();
        itemAltDeleteCodeField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox itemAltDeleteCodeHB = new HBox(itemAltDeleteCodeTitle,itemAltDeleteCodeField);
        itemAltDeleteCodeTitle.setFont(bodyFont);
        itemAltDeleteCodeField.setFont(bodyFont);
        itemAltDeleteCodeHB.setSpacing(sceneSpace);
        itemAltDeleteCodeHB.setAlignment(Pos.CENTER);

        Label itemAltDeleteNameTitle = new Label(PRODUCT_NAME_TITLE);
        TextField itemAltDeleteNameField = new TextField();
        itemAltDeleteNameField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox itemAltDeleteNameHB = new HBox(itemAltDeleteNameTitle,itemAltDeleteNameField);
        itemAltDeleteNameTitle.setFont(bodyFont);
        itemAltDeleteNameField.setFont(bodyFont);
        itemAltDeleteNameHB.setSpacing(sceneSpace);
        itemAltDeleteNameHB.setAlignment(Pos.CENTER);

        Label itemAltDeletePriceTitle = new Label(PRODUCT_PRICE_TITLE);
        TextField itemAltDeletePriceField = new TextField();
        itemAltDeletePriceField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox itemAltDeletePriceHB = new HBox(itemAltDeletePriceTitle,itemAltDeletePriceField);
        itemAltDeletePriceTitle.setFont(bodyFont);
        itemAltDeletePriceField.setFont(bodyFont);
        itemAltDeletePriceHB.setSpacing(sceneSpace);
        itemAltDeletePriceHB.setAlignment(Pos.CENTER);

        // Create add item TF VBox
        VBox itemAltDeleteTitleFieldVB = new VBox(itemAltDeleteCodeHB,itemAltDeleteNameHB,itemAltDeletePriceHB);
        itemAltDeleteTitleFieldVB.setSpacing(sceneSpace);
        itemAltDeleteTitleFieldVB.setAlignment(Pos.CENTER);

        // Select code from itemDeleteComboBox
        // Create update event for delete field nodes
        productSpecificationDeleteComboBox.setOnAction(e -> {
            if (!(productSpecificationDeleteComboBox == null)) {
                itemAltDeleteCodeField.setText(productSpecificationDeleteComboBox.getValue().getProductCode());
                itemAltDeleteNameField.setText(productSpecificationDeleteComboBox.getValue().getProductName());
                itemAltDeletePriceField.setText(currencyFormat.format(productSpecificationDeleteComboBox.getValue().getProductPrice()));
            }
        });
        productSpecificationDeleteComboBox.getEditor().setFont(bodyFont);

        // Create Delete/Done buttons and button HBox
        Button deleteItemButton = new Button(DELETE_PRODUCT_BUTTON_TITLE);
        deleteItemButton.setOnAction(e -> {
            // Try to delete productSpecification from product catalog, if valid then fields will be erased
            if (checkDeleteProduct(itemAltDeleteCodeField.getText())) {
                productSpecificationDeleteComboBox.setValue(null);
                itemAltDeleteCodeField.setText("");
                itemAltDeleteNameField.setText("");
                itemAltDeletePriceField.setText("");
            }
        });
        Button deleteDoneButton = new Button(DONE_BUTTON_TITLE);
        deleteDoneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox deleteButtonHB = new HBox(deleteItemButton,deleteDoneButton);
        deleteItemButton.setFont(buttonFont);
        deleteDoneButton.setFont(buttonFont);
        deleteButtonHB.setSpacing(sceneSpace);
        deleteButtonHB.setAlignment(Pos.CENTER);

        // Create delete item node using delete scene title, deleteComboBox, shared dataAlt VBox, and delete button HBox
        VBox deleteItemVB = new VBox(deleteItemAltSceneTitle,productSpecificationDeleteComboBox,itemAltDeleteTitleFieldVB,deleteButtonHB);
        deleteItemVB.setSpacing(sceneSpace);
        deleteItemVB.setAlignment(Pos.CENTER);

        // Create scene
        deleteItemScene = new Scene(deleteItemVB);

        // ************************************************************************************************************

        // Modify Scene
        // Create dataAlt modify title/field VBox node
        Label itemAltModifyCodeTitle = new Label(PRODUCT_CODE_TITLE);
        TextField itemAltModifyCodeField = new TextField();
        itemAltModifyCodeField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox itemAltModifyCodeHB = new HBox(itemAltModifyCodeTitle,itemAltModifyCodeField);
        itemAltModifyCodeTitle.setFont(bodyFont);
        itemAltModifyCodeField.setFont(bodyFont);
        itemAltModifyCodeHB.setSpacing(sceneSpace);
        itemAltModifyCodeHB.setAlignment(Pos.CENTER);

        Label itemAltModifyNameTitle = new Label(PRODUCT_NAME_TITLE);
        TextField itemAltModifyNameField = new TextField();
        itemAltModifyNameField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox itemAltModifyNameHB = new HBox(itemAltModifyNameTitle,itemAltModifyNameField);
        itemAltModifyNameTitle.setFont(bodyFont);
        itemAltModifyNameField.setFont(bodyFont);
        itemAltModifyNameHB.setSpacing(sceneSpace);
        itemAltModifyNameHB.setAlignment(Pos.CENTER);

        Label itemAltModifyPriceTitle = new Label(PRODUCT_PRICE_TITLE);
        TextField itemAltModifyPriceField = new TextField();
        itemAltModifyPriceField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox itemAltModifyPriceHB = new HBox(itemAltModifyPriceTitle,itemAltModifyPriceField);
        itemAltModifyPriceTitle.setFont(bodyFont);
        itemAltModifyPriceField.setFont(bodyFont);
        itemAltModifyPriceHB.setSpacing(sceneSpace);
        itemAltModifyPriceHB.setAlignment(Pos.CENTER);

        // Create add item TF VBox
        VBox itemAltModifyTitleFieldVB = new VBox(itemAltModifyCodeHB,itemAltModifyNameHB,itemAltModifyPriceHB);
        itemAltModifyTitleFieldVB.setSpacing(sceneSpace);
        itemAltModifyTitleFieldVB.setAlignment(Pos.CENTER);

        // Modify scene
        // Create modify item scene title
        Label modifyItemAltSceneTitle = new Label(MODIFY_PRODUCT_TITLE);
        modifyItemAltSceneTitle.setFont(titleFont);

        // Select code from itemModifyComboBox
        // Create update event for modify field nodes
        productSpecificationModifyComboBox.setOnAction(e -> {
            if (!(productSpecificationModifyComboBox == null)) {
                itemAltModifyCodeField.setText(productSpecificationModifyComboBox.getValue().getProductCode());
                itemAltModifyNameField.setText(productSpecificationModifyComboBox.getValue().getProductName());
                itemAltModifyPriceField.setText(currencyFormat.format(productSpecificationModifyComboBox.getValue().getProductPrice()));
            }
        });
        productSpecificationModifyComboBox.getEditor().setFont(bodyFont);

        // Create Modify/Done buttons and button HBox
        Button modifyItemButton = new Button(MODIFY_PRODUCT_BUTTON_TITLE);
        modifyItemButton.setOnAction(e -> {
            if ( checkModifyProduct(itemAltModifyCodeField.getText(),itemAltModifyNameField.getText(),itemAltModifyPriceField.getText()) ) {
                productSpecificationModifyComboBox.setValue(null);
                itemAltModifyCodeField.setText("");
                itemAltModifyNameField.setText("");
                itemAltModifyPriceField.setText("");
            }
        });
        Button modifyDoneButton = new Button(DONE_BUTTON_TITLE);
        modifyDoneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox modifyButtonHB = new HBox(modifyItemButton,modifyDoneButton);
        modifyItemButton.setFont(buttonFont);
        modifyDoneButton.setFont(buttonFont);
        modifyButtonHB.setSpacing(sceneSpace);
        modifyButtonHB.setAlignment(Pos.CENTER);

        // Create modify item node using modify scene title, modifyComboBox, shared dataAlt VBox, and modify button HBox
        VBox modifyItemVB = new VBox(modifyItemAltSceneTitle,productSpecificationModifyComboBox,itemAltModifyTitleFieldVB,modifyButtonHB);
        modifyItemVB.setSpacing(sceneSpace);
        modifyItemVB.setAlignment(Pos.CENTER);

        // Create scene
        modifyItemScene = new Scene(modifyItemVB);

        // ************************************************************************************************************

        // dataAlt scene
        // Create dataAlt title node
        Label dataAltLabel = new Label(DATA_ALT_TITLE);
        dataAltLabel.setFont(titleFont);

        // Create dataAlt button nodes
        Button dataAltAddItemButton = new Button(ADD_PRODUCT_BUTTON_TITLE);
        dataAltAddItemButton.setOnAction(e -> {
            // Resetting input fields in scene
            itemAltAddCodeField.setText("");
            itemAltAddNameField.setText("");
            itemAltAddPriceField.setText("");
            primaryStage.setScene(addItemScene);
        });
        Button dataAltDeleteItemButton = new Button(DELETE_PRODUCT_BUTTON_TITLE);
        dataAltDeleteItemButton.setOnAction(e -> {
            // Resetting input fields in scene
            productSpecificationDeleteComboBox.setValue(null);
            itemAltDeleteCodeField.setText("");
            itemAltDeleteNameField.setText("");
            itemAltDeletePriceField.setText("");
            primaryStage.setScene(deleteItemScene);
        });
        Button dataAltModItemButton = new Button(MODIFY_PRODUCT_BUTTON_TITLE);
        dataAltModItemButton.setOnAction(e -> {
            // Resetting input fields in scene
            productSpecificationModifyComboBox.setValue(null);
            itemAltModifyCodeField.setText("");
            itemAltModifyNameField.setText("");
            itemAltModifyPriceField.setText("");
            primaryStage.setScene(modifyItemScene);
        });
        HBox dataAltButtonHB = new HBox(dataAltAddItemButton,dataAltDeleteItemButton,dataAltModItemButton);
        dataAltAddItemButton.setFont(buttonFont);
        dataAltDeleteItemButton.setFont(buttonFont);
        dataAltModItemButton.setFont(buttonFont);
        dataAltButtonHB.setSpacing(sceneSpace);
        dataAltButtonHB.setAlignment(Pos.CENTER);

        // Create Done/Quit button node
        Button saleAltButton = new Button(SALE_BUTTON_TITLE);
        saleAltButton.setOnAction(e -> primaryStage.setScene(saleScene));
        Button quitAltButton = new Button(QUIT_BUTTON_TITLE);
        quitAltButton.setOnAction(e -> saveAndQuit());
        HBox dataAltDoneQuitButtonHB = new HBox(saleAltButton,quitAltButton);
        saleAltButton.setFont(buttonFont);
        quitAltButton.setFont(buttonFont);
        dataAltDoneQuitButtonHB.setSpacing(sceneSpace);
        dataAltDoneQuitButtonHB.setAlignment(Pos.CENTER);

        // Create dataAlt node
        VBox dataAltVB = new VBox(dataAltLabel,dataAltButtonHB,dataAltDoneQuitButtonHB);
        dataAltVB.setSpacing(sceneSpace);
        dataAltVB.setAlignment(Pos.CENTER);

        // Create scene
        dataAltScene = new Scene(dataAltVB);

        // ************************************************************************************************************

        // sale scene
        // Create sale title
        Label itemSaleTitle = new Label(ITEM_SALE_TITLE);
        itemSaleTitle.setFont(titleFont);

        // Create middle checkout Vbox
        // Create receipt Label node
        TextArea receiptTextArea = new TextArea(RECEIPT_TEXT);
        receiptTextArea.setPrefSize(RECEIPT_WIDTH,RECEIPT_HEIGHT);
        receiptTextArea.setFont(receiptFont);

        // Create subtotal title/field HBox nodes
        Label subtotalTitle = new Label(SUBTOTAL_TITLE);
        TextField subtotalField = new TextField();
        subtotalField.setPromptText(TOTAL_FIELDS_DEFAULT);
        HBox subtotalHB = new HBox(subtotalTitle,subtotalField);
        subtotalTitle.setFont(bodyFont);
        subtotalField.setFont(bodyFont);
        subtotalHB.setSpacing(sceneSpace);
        subtotalHB.setAlignment(Pos.CENTER);

        Label subtotalTaxTitle = new Label(SUBTOTAL_TAX_TITLE);
        TextField subtotalTaxField = new TextField();
        subtotalTaxField.setPromptText(TOTAL_FIELDS_DEFAULT);
        HBox subtotalTaxHB = new HBox(subtotalTaxTitle,subtotalTaxField);
        subtotalTaxTitle.setFont(bodyFont);
        subtotalTaxField.setFont(bodyFont);
        subtotalTaxHB.setSpacing(sceneSpace);
        subtotalTaxHB.setAlignment(Pos.CENTER);

        Label tenderTitle = new Label(TENDER_TITLE);
        TextField tenderTF = new TextField();
        tenderTF.setPromptText(TYPE_HERE_DEFAULT);
        HBox tenderHB = new HBox(tenderTitle,tenderTF);
        tenderTitle.setFont(bodyFont);
        tenderTF.setFont(bodyFont);
        tenderHB.setSpacing(sceneSpace);
        tenderHB.setAlignment(Pos.CENTER);

        Label changeTitle = new Label(CHANGE_TITLE);
        TextField changeField = new TextField();
        changeField.setPromptText(TOTAL_FIELDS_DEFAULT);
        HBox changeHB = new HBox(changeTitle,changeField);
        changeTitle.setFont(bodyFont);
        changeField.setFont(bodyFont);
        changeHB.setSpacing(sceneSpace);
        changeHB.setAlignment(Pos.CENTER);

        // Create item title/field HBox nodes
        Label itemSaleCodeTitle = new Label(ITEM_NAME_TITLE);
        TextField itemSaleCodeField = new TextField();
        itemSaleCodeField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox itemSaleCodeHB = new HBox(itemSaleCodeTitle,itemSaleCodeField);
        itemSaleCodeTitle.setFont(bodyFont);
        itemSaleCodeField.setFont(bodyFont);
        itemSaleCodeHB.setSpacing(sceneSpace);
        itemSaleCodeHB.setAlignment(Pos.CENTER);

        Label itemSaleNameTitle = new Label(ITEM_PRICE_TITLE);
        TextField itemSaleNameField = new TextField();
        itemSaleNameField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox itemSaleNameHB = new HBox(itemSaleNameTitle,itemSaleNameField);
        itemSaleNameTitle.setFont(bodyFont);
        itemSaleNameField.setFont(bodyFont);
        itemSaleNameHB.setSpacing(sceneSpace);
        itemSaleNameHB.setAlignment(Pos.CENTER);

        Label itemSalePriceTitle = new Label(ITEM_TOTAL_TITLE);
        TextField itemSalePriceField = new TextField();
        itemSalePriceField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox itemSalePriceHB = new HBox(itemSalePriceTitle,itemSalePriceField);
        itemSalePriceTitle.setFont(bodyFont);
        itemSalePriceField.setFont(bodyFont);
        itemSalePriceHB.setSpacing(sceneSpace);
        itemSalePriceHB.setAlignment(Pos.CENTER);

        Label itemSaleQuantityTitle = new Label(ITEM_QUANTITY_TITLE);
        TextField itemSaleQuantityField = new TextField();
        itemSaleQuantityField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox itemSaleQuantityHB = new HBox(itemSaleQuantityTitle,itemSaleQuantityField);
        itemSaleQuantityField.setPromptText(TYPE_HERE_DEFAULT);
        itemSaleQuantityTitle.setFont(bodyFont);
        itemSaleQuantityField.setFont(bodyFont);
        itemSaleQuantityHB.setSpacing(sceneSpace);
        itemSaleQuantityHB.setAlignment(Pos.CENTER);

        // Create item TF VBox
        VBox itemSaleTitleFieldVB = new VBox(itemSaleCodeHB,itemSaleNameHB,itemSalePriceHB,itemSaleQuantityHB);
        itemSaleTitleFieldVB.setSpacing(sceneSpace);
        itemSaleTitleFieldVB.setAlignment(Pos.CENTER);

        // Select item from itemSaleComboBox
        // Create update event for sale field nodes
        productSpecificationSaleComboBox.setOnAction(e -> {
            if (!(productSpecificationSaleComboBox.getValue() == null)) {
                itemSaleCodeField.setText(productSpecificationSaleComboBox.getValue().getProductCode());
                itemSaleNameField.setText(productSpecificationSaleComboBox.getValue().getProductName());
                itemSalePriceField.setText(currencyFormat.format(productSpecificationSaleComboBox.getValue().getProductPrice()));
            }
        });
        productSpecificationSaleComboBox.getEditor().setFont(bodyFont);

        // Create add button to top of sale
        Button addButton = new Button(ADD_BUTTON_TITLE);
        addButton.setOnAction(event -> {
            try {
                if (checkSaleAmount() == 0) {
                    // Empty
                    // Reset change field
                    changeField.setText("");
                }

                if ( (Integer.parseInt(itemSaleQuantityField.getText()) > 0) && !(itemSaleCodeField.getText().equals(""))) {
                    // Add product to sale and update receipt text
                    String newReceiptText = addSaleProduct(itemSaleCodeField.getText(), itemSaleQuantityField.getText());
                    RECEIPT_TEXT = RECEIPT_TEXT.concat(newReceiptText);

                    // Check if input valid
                    if (!newReceiptText.equals("")) {
                        // Reset field values
                        productSpecificationSaleComboBox.setValue(null);
                        itemSaleCodeField.setText("");
                        itemSaleNameField.setText("");
                        itemSalePriceField.setText("");
                        itemSaleQuantityField.setText("");

                        // Set textArea to updated textArea
                        receiptTextArea.setText(RECEIPT_TEXT);

                        // Update subtotal fields
                        subtotalField.setText(currencyFormat.format(register.getSubtotal()));
                        subtotalTaxField.setText(currencyFormat.format(register.getSubtotalTax()));
                    }
                }
            } catch (Exception exception) {
                // Reset input field
                itemSaleQuantityField.setText("");
            }
        });
        addButton.setFont(buttonFont);

        // Create sale node
        VBox saleTopVB = new VBox(productSpecificationSaleComboBox,itemSaleTitleFieldVB,addButton);
        saleTopVB.setSpacing(sceneSpace);
        saleTopVB.setAlignment(Pos.CENTER);

        // Create checkout button node
        Button checkoutButton = new Button(CHECKOUT_BUTTON_TITLE);
        checkoutButton.setOnAction(event -> {
            try {
                // Check if tenderField is not empty and the tenderTF is bigger or equal to subtotalTaxField
                if (!tenderTF.getText().equals("") &&
                        (Double.parseDouble(tenderTF.getText()) >= Double.parseDouble(subtotalTaxField.getText())) ) {
                    // Try checkout
                    if (checkOut(tenderTF.getText())) {
                        // Set up change value
                        Double changeAmount = Double.parseDouble(tenderTF.getText()) - Double.parseDouble(subtotalTaxField.getText());

                        // Update receipt field
                        RECEIPT_TEXT = RECEIPT_TEXT.concat(checkOutReceiptString(tenderTF.getText(),subtotalTaxField.getText()));
                        receiptTextArea.setText(RECEIPT_TEXT);

                        // Update change fields
                        changeField.setText(currencyFormat.format(changeAmount));

                        // Update register fields
                        register.resetSale();

                        // Update GUI total fields
                        subtotalField.setText("");
                        subtotalTaxField.setText("");
                        tenderTF.setText("");
                    }
                } else {
                    // Reset tender field
                    tenderTF.setText("");
                }
            } catch (NumberFormatException numberFormatException) {
                // Reset tender field
                tenderTF.setText("");
            }
        });
        checkoutButton.setFont(buttonFont);

        // Create subtotal VBox
        VBox subtotalVB = new VBox(subtotalHB,subtotalTaxHB,tenderHB,checkoutButton,changeHB);
        subtotalVB.setSpacing(sceneSpace);
        subtotalVB.setAlignment(Pos.CENTER);

        // Create bottom button HBox
        Button doneButton = new Button(DONE_BUTTON_TITLE);
        Button dataAltButton = new Button(DATA_ALT_BUTTON_TITLE);
        doneButton.setOnAction(event -> primaryStage.setScene(mainScene));
        dataAltButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox buttonHB = new HBox(doneButton,dataAltButton);
        doneButton.setFont(buttonFont);
        dataAltButton.setFont(buttonFont);
        buttonHB.setSpacing(sceneSpace);
        buttonHB.setAlignment(Pos.CENTER);

        // Create sale border pane
        HBox saleBodyHB = new HBox(saleTopVB,receiptTextArea,subtotalVB);
        VBox saleVB = new VBox(itemSaleTitle,saleBodyHB,buttonHB);
        saleVB.setSpacing(sceneSpace);
        saleVB.setAlignment(Pos.CENTER);

        // Create then set SaleScene
        saleScene = new Scene(saleVB);

        // ************************************************************************************************************

        // Main scene
        // Create main screen scene nodes
        Label mainTitleLabel = new Label(MAIN_TITLE);
        mainTitleLabel.setFont(titleFont);

        Button newSaleButton = new Button(NEW_SALE_BUTTON_TITLE);
        Button quitButton = new Button(QUIT_BUTTON_TITLE);
        newSaleButton.setOnAction(e -> primaryStage.setScene(saleScene));
        quitButton.setOnAction(event -> saveAndQuit());
        HBox mainButtonHB = new HBox(newSaleButton,quitButton);
        mainButtonHB.setSpacing(sceneSpace);
        mainButtonHB.setAlignment(Pos.CENTER);
        newSaleButton.setFont(buttonFont);
        quitButton.setFont(buttonFont);

        Label eodTotalLabel = new Label(EOD_TOTAL_TITLE + currencyFormat.format(eodTotalPrice));
        eodTotalLabel.setFont(bodyFont);

        // Create main node
        VBox mainVB = new VBox(mainTitleLabel,mainButtonHB,eodTotalLabel);
        mainVB.setSpacing(sceneSpace);
        mainVB.setAlignment(Pos.CENTER);

        // Create then set mainScene
        mainScene = new Scene(mainVB);

        // Set title and scene then show stage
        primaryStage.setTitle(MAIN_SCENE_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void initialize(){
        // Create register object instance
        register = new Register(currencyFormat,FILE_NAME_KEY);

        productSpecificationSaleComboBox = new ComboBox<>(register.getProductSpecificationSaleObservableList());
        productSpecificationDeleteComboBox = new ComboBox<>(register.getProductSpecificationDeleteObservableList());
        productSpecificationModifyComboBox = new ComboBox<>(register.getProductSpecificationModifyObservableList());

        // Update comboBoxes
        updateComboBoxes();
    }

    // Quitting and saving product Catalog to item.txt file
    private static void saveAndQuit() {
        // Save catalog
        register.saveCatalog();

        // Quit system
        System.exit(0);
    }

    // MIGHT BE ABLE TO REDUCE TO JUST ONE LIST
    private static void updateComboBoxes() {
        // Update ComboBoxes
        productSpecificationSaleComboBox.setItems(register.getProductSpecificationSaleObservableList());
        productSpecificationDeleteComboBox.setItems(register.getProductSpecificationDeleteObservableList());
        productSpecificationModifyComboBox.setItems(register.getProductSpecificationModifyObservableList());
    }

    /**
     * Handler method for adding item object to itemArrayList. If inputs are valid then return true and add item object
     * to itemArrayList, if inputs are invalid then return false.
     *
     * @param codeInput String, item code
     * @param nameInput String, item name
     * @param priceInput String, item price
     */
    private static boolean checkAddProduct(String codeInput, String nameInput, String priceInput) {
        // Check if input is valid
        try {
            if ((codeInput.matches("[AB]\\d\\d\\d")) && (Double.parseDouble(priceInput) > 0) &&
                    (!register.checkIfCreate(codeInput))) {
                // Input valid

                // Add product to register
                register.addProductCatalogProduct(codeInput,nameInput,Double.parseDouble(priceInput));

                // Update ObservationList and ComboBoxes
                updateComboBoxes();

                return true;

            } else {
                // input invalid
                return false;
            }

        }
        catch (Exception e) {
            // input invalid
            return false;
        }
    }

    /**
     * Handler method for deleting item object to itemArrayList. If inputs are valid then return true and remove item
     * object from itemArrayList, if inputs are invalid then return false.
     *
     * @param codeInput String, item code
     */
    private static boolean checkDeleteProduct(String codeInput) {
        // Check if input is valid
        try {
            if ((codeInput.matches("[AB]\\d\\d\\d")) && (register.checkIfCreate(codeInput))) {
                // Input valid

                // Remove product from register
                register.deleteProductCatalogProduct(codeInput);

                // Update ObservationList and ComboBoxes
                updateComboBoxes();

                return true;

            } else {
                // input invalid
                return false;
            }

        }
        catch (Exception e) {
            // input invalid
            return false;
        }
    }

    /**
     * Handler method for modifying item object to itemArrayList. If inputs are valid then return true and remove old
     * item object from itemArrayList and add new item object, if inputs are invalid then return false.
     *
     * @param codeInput String, item code
     * @param nameInput String, item name
     * @param priceInput String, item price
     */
    private static boolean checkModifyProduct(String codeInput, String nameInput, String priceInput) {
        // Check if input is valid
        try {
            if ((codeInput.matches("[AB]\\d\\d\\d")) && (Double.parseDouble(priceInput) > 0) &&
                    (register.checkIfCreate(codeInput))) {
                // Input valid

                // Modify product from register
                register.modifyProductCatalogProduct(codeInput,nameInput, Double.valueOf(priceInput));

                // Update ObservationList and ComboBoxes
                updateComboBoxes();

                return true;

            } else {
                // input invalid
                return false;
            }

        }
        catch (Exception e) {
            // input invalid
            return false;
        }
    }

    /**
     * Handler method for adding item and quantity to Sale object. If inputs are valid then return true amd add item to
     * Sale object, if inputs are invalid then return false.
     *
     * @param codeInput String, item code
     * @param quantityInput String, quantity of item
     */
    private static String addSaleProduct(String codeInput, String quantityInput) {
        try {
            // Convert priceInput into an Integer
            int quantityInputInt = Integer.parseInt(quantityInput);

            if ( (codeInput.matches("[AB]\\d\\d\\d")) && (quantityInputInt > 0 && quantityInputInt <= 100) &&
                    (register.checkIfCreate(codeInput)) ) {

                // Add product to Register and set return text to receiptTextArea
                return register.addProductToSale(codeInput,quantityInputInt);
            }
        } catch (Exception e){
            // Input is invalid
            return "";
        }
        return "";
    }

    // Returns true if sale is empty, false if not
    private static int checkSaleAmount() {
        return register.checkIfAmount();
    }

    private static String checkOutReceiptString(String tenderInput, String subtotalTaxInput) {
        // Find change amount and return String of receipt
        return register.checkOutReceiptString(Double.parseDouble(tenderInput) - Double.parseDouble(subtotalTaxInput));
    }

    // Returns boolean, true = valid, false = invalid
    private static boolean checkOut(String tenderInput) {
        try {
            // Convert tender into Double and checkout from register
            return register.checkOut(Double.parseDouble(tenderInput));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Start GUI
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        launch();
    }
}

