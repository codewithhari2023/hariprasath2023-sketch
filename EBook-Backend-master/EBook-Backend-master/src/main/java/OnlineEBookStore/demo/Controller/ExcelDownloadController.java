package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.ExcelUpload;
import OnlineEBookStore.demo.Repository.ExcelRepository;
import OnlineEBookStore.demo.Request.ExcelRequest;
import OnlineEBookStore.demo.Service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/excel/download")
public class ExcelDownloadController {
    @Autowired
    private ExcelService excelService;
    @Autowired
    private ExcelRepository excelRepository;
//    @GetMapping("/user/{fileId}")
//    public ResponseEntity<InputStreamResource> downloadUSerExcelFile(@PathVariable Long fileId) throws IOException {
//        List<CommonUser>commonUsers=new ArrayList<>();
//        ExcelUpload excelUpload=new ExcelUpload();
//        excelService.saveUSerExcelFile(commonUsers,excelUpload.getUploadFileName());
//        excelUpload= excelRepository.findById(fileId).orElse(null);
//        if (excelUpload == null) {
//            return ResponseEntity.notFound().build();
//        }
//        InputStream inputStream = new FileInputStream(excelUpload.getPath());
//        InputStreamResource resource = new InputStreamResource(inputStream);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDispositionFormData("attachment", excelUpload.getUploadFileName());
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//
//        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
//    }
//    @GetMapping("/book/{fileId}")
//    public ResponseEntity<InputStreamResource> downloadBookExcelFile(@PathVariable Long fileId) throws IOException {
//        ExcelUpload excelUpload = excelRepository.findById(fileId).orElse(null);
//        if (excelUpload == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        File excelFile = new File(excelUpload.getPath());
//        if (!excelFile.exists() || !excelFile.isFile()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        InputStream inputStream = new FileInputStream(excelFile);
//        InputStreamResource resource = new InputStreamResource(inputStream);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDispositionFormData("attachment", excelUpload.getUploadFileName());
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(resource);
//    }
}
