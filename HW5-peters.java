// Conformance to the OO Design: xx %
// Support of item change: full/partial/none
// Support of random access file: yes/no
// Javadoc conformed comments on the classes, methods, and attributes: full/partial/no
// Handling wrong input and invalid input: full/partial/no
// Program does not crash with exceptions: crashes/does not crash
// Correct handling of payment and taxes: yes/partial/no
// Overall layout of GUI and ease of use: almost perfect/good enough/not good but works

// !!!!!!!!!!!!NEED TO FILLOUT BEOFRE TURN IN

// Driver class for HW5

/**
 * @author Anthony Peters
 *
 * Driver class used to create a Register object then run it, after it finishes running it will exit the program
 */

public static void main(String[] args) {
        // Create register object instance
        Register register = new Register();

        // Run register object
        register.run();

        // Finish
        System.exit(0);
}