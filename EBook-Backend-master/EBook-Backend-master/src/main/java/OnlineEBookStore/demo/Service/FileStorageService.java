package OnlineEBookStore.demo.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    private String uploadDir="C/Users/kumaran/Downloads/demo/demo/src/main/java/OnlineEBookStore/uploads/files";
    public String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(file.getInputStream(), uploadPath.resolve(fileName));
        return fileName;
    }
}
