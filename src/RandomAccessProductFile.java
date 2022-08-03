import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Anthony Peters
 *
 * RandomAccessFile subclass to extend reading from and writing to file
 */

public class RandomAccessProductFile extends RandomAccessFile {

    /**
     * Aug. Constructor
     * Takes file and mode inputs, creates using super constructor with given inputs
     *
     * @param file File, file input
     * @param mode String, mode input
     * @throws FileNotFoundException
     */
    public RandomAccessProductFile(File file, String mode) throws FileNotFoundException {
        super(file, mode);
    }

    /**
     * Read from file at given input position
     *
     * @param position Int, position inpuht
     * @return String, string from file
     */
    public String readProductFromRandomAccessProductFile(int position) {
        String productRecord = "";

        try {
            // Move file pointer
            this.seek(position);

            // Read from RandomAccessProductFile
            productRecord = this.readUTF();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return productRecord;
    }

    /**
     * Write String input to file at given input position
     *
     * @param position Int, position input
     * @param productRecord String, String to input
     */
    public void writeProductToRandomAccessProductFile(int position, String productRecord) {
        try {
            // Move file pointer
            this.seek(position);

            // Write to RandomAccessProductFile
            this.writeBytes(productRecord);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
