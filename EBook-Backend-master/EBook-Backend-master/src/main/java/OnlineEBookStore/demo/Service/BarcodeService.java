package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Dto.BarcodeDto;
import OnlineEBookStore.demo.Model.BarCode;
import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Repository.BarcodeRepository;
import OnlineEBookStore.demo.Response.BarcodeResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class BarcodeService {
    @Autowired
    private BarcodeRepository barcodeRepository;
    @Autowired
    private BarcodeDto barcodeDto;
    @Autowired
    private EntityManager entityManager;

    public Map<Long, byte[]> generateBarcodeForBooks(List<Book> books) {
        Map<Long, byte[]> barcodeImages = new HashMap<>();
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            for (Book book : books) {

                String productCode = book.getTitle(); // Assuming title as product code, adjust as needed
                BitMatrix bitMatrix = writer.encode(productCode, BarcodeFormat.CODE_128, 200, 50);
                byte[] imageData = convertBitMatrixToByteArray(bitMatrix);
                saveBarcodeImage(bitMatrix,book.getId());
                barcodeImages.put(book.getId(), imageData);
                saveBarcodeImage(book.getId(), imageData);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception as per your requirement
        }
        return barcodeImages;
    }

    private byte[] convertBitMatrixToByteArray(BitMatrix bitMatrix) throws IOException {
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        return outputStream.toByteArray();
    }


    private void saveBarcodeImage(BitMatrix bitMatrix, Long bookId) throws IOException, FileNotFoundException {
        String fileName = "barcode_" + bookId + ".png";
        Path filePath = Paths.get("C:/Users/kumaran/Downloads/demo/demo/src/main/java/OnlineEBookStore/demo/"+fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);
    }

    private void saveBarcodeImage(Long bookId, byte[] imageData) {
        BarCode barcode = new BarCode();
        barcode.setBook_id(bookId);
        barcode.setImageData(imageData);
        barcodeRepository.save(barcode);
    }

    public BarcodeResponse findall() {
        List<BarCode> barCodes = barcodeRepository.findAll();
        return barcodeDto.MapToBarcodeResponse(barCodes);
    }
    public  List<BarCode> findAllBarcodes(){
        return barcodeRepository.findAll();
    }

    //    public Long GetBarcode(Long id){
//
//    }
    public String getBarcodeImageString(Long barcodeId) {
        BarCode barcode = entityManager.find(BarCode.class, barcodeId);
        if (barcode != null) {
            byte[] imageData = barcode.getImageData();
            return Base64.getEncoder().encodeToString(imageData);
        } else {
            return null; // Barcode not found
        }

    }
    public void saveImageToFile(byte[] imageData, String fileName) {

        try {
            Path filePath = Paths.get(fileName);
            Files.write(filePath, imageData);
            System.out.println("PNG file saved as: " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving PNG file: " + e.getMessage());
        }

    }
    public List<byte[]> getAllBarcodeImages() {
        List<byte[]> imageDataList = new ArrayList<>();

        // Fetch all barcode images from your repository or storage
        // Example logic:
        List<BarCode> barcodeEntities = barcodeRepository.findAll(); // Assuming BarcodeEntity is your entity class

        for (BarCode barcodeEntity : barcodeEntities) {
            // Convert each barcode entity's image data to byte array and add to the list
            byte[] imageData = barcodeEntity.getImageData(); // Assuming getImageData() returns byte[]
            imageDataList.add(imageData);
        }

        return imageDataList;
    }

    public byte[] retrieveBarcodeImage(Long barcodeId) {
        // Assuming barcodeDataRepository is your repository or data access object (DAO) for fetching barcode data
        Optional<BarCode> barcodeData = barcodeRepository.findById(barcodeId);

        if (barcodeData != null) {
            // Assuming the barcodeData object has a method to retrieve the image data as a byte array
            byte[] imageData = barcodeData.get().getImageData();
            return imageData;
        } else {
            // Barcode data not found
            return null;
        }
    }
    public List<byte[]> getAllBarcodes() {
        // Assuming your BarcodeRepository has a method findAllBarcodeImages() to retrieve all images
        return barcodeRepository.findAllBarcodeImages();
    }

    public BarCode saveBarcode(BarCode barcode) {
        return barcodeRepository.save(barcode);
    }
    public List<String> convertToBase64(List<byte[]> byteArrays) {
        List<String> base64Images = new ArrayList<>();
        for (byte[] byteArray : byteArrays) {
            String base64Image = Base64.getEncoder().encodeToString(byteArray);
            base64Images.add(base64Image);
        }
        return base64Images;
    }
    public byte[] mergeImages(List<byte[]> imageDataList) throws IOException {
        if (imageDataList.isEmpty()) {
            return null;
        }

        // Read the first image to get dimensions and format
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageDataList.get(0));
        BufferedImage baseImage = ImageIO.read(inputStream);

        // Create a new BufferedImage for the merged image
        BufferedImage mergedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight() * imageDataList.size(), baseImage.getType());

        // Create a Graphics object to draw images onto the mergedImage
        int yOffset = 0; // Y offset to position images vertically
        for (byte[] imageData : imageDataList) {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageData);
            BufferedImage image = ImageIO.read(imageStream);

            // Draw the current image onto the mergedImage at the appropriate position
            mergedImage.getGraphics().drawImage(image, 0, yOffset, null);
            yOffset += image.getHeight(); // Increment yOffset for next image
        }

        // Write the mergedImage to a ByteArrayOutputStream as PNG
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(baseImage, "png", outputStream);

        return outputStream.toByteArray();
    }
}
