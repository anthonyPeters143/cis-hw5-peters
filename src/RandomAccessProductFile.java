import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessProductFile extends RandomAccessFile {

    public RandomAccessProductFile(String name, String mode) throws FileNotFoundException {
        super(name, mode);
    }

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