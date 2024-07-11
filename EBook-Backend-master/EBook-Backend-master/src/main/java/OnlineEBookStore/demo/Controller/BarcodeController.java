package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Model.BarCode;
import OnlineEBookStore.demo.Request.BookBarcodeRequest;
import OnlineEBookStore.demo.Response.BarcodeResponse;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.BarcodeService;
import OnlineEBookStore.demo.Service.StorageService;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/barcode")
public class BarcodeController {
    @Autowired
    private BarcodeService barcodeService;
    @Autowired
    private APIResponse apiResponse;
    @Autowired
    private StorageService storageService;

    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateBarcodesForBooks(@RequestBody BookBarcodeRequest barcodeRequest) throws IOException {
        Map<Long, byte[]> barcodeImages = barcodeService.generateBarcodeForBooks(barcodeRequest.getBooks());

        if (barcodeImages.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            // Assuming barcodeImages is a map of book IDs to byte arrays representing barcode images
            // For each book's barcode image, you can convert the byte array to BufferedImage and then to PNG bytes
            Long firstBookId = barcodeImages.keySet().iterator().next(); // Get the ID of the first book

            byte[] firstBarcodeImageBytes = barcodeImages.get(firstBookId);
            BufferedImage firstBarcodeImage = ImageIO.read(new ByteArrayInputStream(firstBarcodeImageBytes));
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            ImageIO.write(firstBarcodeImage, "png", pngOutputStream);

            // Set response headers for PNG image download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentDispositionFormData("attachment", "barcode.png");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pngOutputStream.toByteArray());
        }
    }

@GetMapping(value = "image/{barcodeId}", produces = MediaType.IMAGE_PNG_VALUE)
public ResponseEntity<byte[]> getBarcodeImage(@PathVariable Long barcodeId) {
    byte[] imageData = barcodeService.retrieveBarcodeImage(barcodeId);

    if (imageData != null) {
        // Return the barcode image as PNG bytes
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageData);
    } else {
        // Barcode not found
        return ResponseEntity.notFound().build();
    }
}
    @GetMapping(value = "/images", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getAllBarcodeImages() throws IOException {
        List<byte[]> imageDataList = barcodeService.getAllBarcodeImages(); // Assuming this returns a list of byte arrays

        if (imageDataList != null && !imageDataList.isEmpty()) {
            // Since you're returning PNG images, you can merge multiple PNG images into one if needed
            byte[] mergedImageData = barcodeService.mergeImages(imageDataList);

            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(mergedImageData);
        } else {
            // No barcode images found
            return ResponseEntity.notFound().build();
        }
    }



}
