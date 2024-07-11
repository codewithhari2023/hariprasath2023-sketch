package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Model.*;
import OnlineEBookStore.demo.Repository.ExcelRepository;
import OnlineEBookStore.demo.Repository.UserRepository;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.ExcelService;

import OnlineEBookStore.demo.Service.UserService;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/excel")
public class ExcelController {
    @Autowired
    private ExcelService excelService;
    @Autowired
    private ExcelRepository excelRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private APIResponse apiResponse;
//


@PostMapping("/download")
public void downloadExcel(HttpServletResponse response) {
    List<CommonUser> users = userRepository.findAll(); // Assuming you have a method to fetch all books from the database

    try (Workbook workbook = new XSSFWorkbook()) {
        Sheet sheet = workbook.createSheet("Users");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Username");

        headerRow.createCell(1).setCellValue("Address");

        // Populate data rows
        int rowNum = 1;
        for (CommonUser user :users) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getName());
            row.createCell(1).setCellValue(user.getUsername());
            row.createCell(3).setCellValue(user.getAddress().toString());

        }

        // Set content type and header for response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=books.xlsx");

        // Write Excel to response output stream
        workbook.write(response.getOutputStream());
    } catch (IOException e) {
        // Handle exception
        e.printStackTrace();
        // You might want to return an error response to the client here
    }

}

//    @PostMapping("/downloads")
//    public ResponseEntity<byte[]> downloadExcel() {
//        List<CommonUser> users = userRepository.findAll(); // Assuming you have a method to fetch all books from the database
//
//        try (Workbook workbook = new XSSFWorkbook()) {
//            Sheet sheet = workbook.createSheet("Users");
//
//            // Create header row
//            Row headerRow = sheet.createRow(0);
//            headerRow.createCell(0).setCellValue("Name");
//            headerRow.createCell(1).setCellValue("Username");
//            headerRow.createCell(2).setCellValue("Password");
//            headerRow.createCell(3).setCellValue("Address");
//            headerRow.createCell(4).setCellValue("Card");
//
//            // Populate data rows
//            int rowNum = 1;
//            for (CommonUser user : users) {
//                Row row = sheet.createRow(rowNum++);
//                row.createCell(0).setCellValue(user.getName());
//                row.createCell(1).setCellValue(user.getUsername());
//                row.createCell(2).setCellValue(user.getPassword());
//                row.createCell(3).setCellValue(user.getAddress().toString());
////                row.createCell(4).setCellValue(user.getCard().toString());
//            }
//
//            // Convert workbook to byte array
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            workbook.write(outputStream);
//            byte[] excelBytes = outputStream.toByteArray();
//
//            // Set content type and headers for response
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
//            headers.setContentDispositionFormData("attachment", "books.xlsx");
//            headers.setContentLength(excelBytes.length);
//
//            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
//        } catch (IOException e) {
//            // Handle exception
//            e.printStackTrace();
//            // You might want to return an error response to the client here
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

}


