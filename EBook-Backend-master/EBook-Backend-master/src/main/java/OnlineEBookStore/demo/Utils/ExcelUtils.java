package OnlineEBookStore.demo.Utils;

import OnlineEBookStore.demo.Model.Book;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ExcelUtils {
    public static byte[] decompressExcelFile(byte[] compressedData) {
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(compressedData);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4 * 1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();

            byte[] decompressedData = outputStream.toByteArray();

            Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(decompressedData));
            ByteArrayOutputStream excelStream = new ByteArrayOutputStream();
            workbook.write(excelStream);

            return excelStream.toByteArray();
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] compressExcelData(byte[] excelData) throws IOException {
        // Create a Deflater object with BEST_COMPRESSION level
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);

        // Set the input data to be compressed
        deflater.setInput(excelData);

        // Create a ByteArrayOutputStream to store the compressed data
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(excelData.length);

        // Compress the data
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // Compress data into buffer
            outputStream.write(buffer, 0, count); // Write compressed data to output stream
        }

        // Close resources
        outputStream.close();
        deflater.end();

        // Get the compressed data as a byte array
        return outputStream.toByteArray();
    }
}
