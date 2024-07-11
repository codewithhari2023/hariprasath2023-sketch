package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class DownloadController {
    @Autowired
    private BookService bookService;

//    @GetMapping("/api/admin/book/downloadFile/{id}")
//    public ResponseEntity<InputStreamResource> downloadProductFile(@PathVariable Long id) throws IOException {
//        System.out.println();
//        File file = bookService.getbook(id); // Assuming bookService.getBook(id) returns the file
//
//        if (file == null || !file.exists()) {
//            // Handle case where file is not found
//            return ResponseEntity.notFound().build();
//        }
//
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
//        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        headers.add("Pragma", "no-cache");
//        headers.add("Expires", "0");
//
//        MediaType mediaType = MediaType.parseMediaType(getContentType(file));
//        System.out.println("Hello");
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(mediaType)
//                .contentLength(file.length())
//                .body(resource);
//
//    }
//
//    private String getContentType(File file) {
//        String contentType;
//        try {
//            contentType = Files.probeContentType(file.toPath());
//        } catch (IOException e) {
//            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//        }
//        return contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE;
//    }
@GetMapping("/api/admin/book/downloadFile/{id}")
public ResponseEntity<InputStreamResource> downloadProductFile(@PathVariable Long id) throws IOException {
    File file = bookService.getbook(id);

    if (file == null || !file.exists()) {
        return ResponseEntity.notFound().build();
    }

    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");

    MediaType mediaType = getMediaType(file);

    return ResponseEntity.ok()
            .headers(headers)
            .contentType(mediaType)
            .contentLength(file.length())
            .body(resource);
}

    private MediaType getMediaType(File file) throws IOException {
        String fileName = file.getName();
        if (fileName.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else if (fileName.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else {
            // Default to octet-stream for unknown types
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
