package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private FileStorageService fileStorageService;
    private String uploadDir="C/Users/kumaran/Downloads/demo/demo/src/main/java/OnlineEBookStore/uploads/files";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.saveFile(file);
            return ResponseEntity.ok().body("File uploaded successfully: " + fileName);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file.");
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String fileName) throws IOException {
        Path filePath = (Path) Paths.get(uploadDir).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not found: " + fileName);
        }

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
