package OnlineEBookStore.Helper;

import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Service.BookService;
import OnlineEBookStore.demo.Service.UserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ExcelHelper {
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public ExcelHelper(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Id", "username", "name", "Password" };
    static String SHEET = "Books";
    static String SHEETS="Sheet1";
    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static ByteArrayInputStream UserToExcel(List<CommonUser> commonUserList) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (CommonUser commonUser : commonUserList) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(commonUser.getId());
                row.createCell(1).setCellValue(commonUser.getName());
                row.createCell(2).setCellValue(commonUser.getUsername());
                row.createCell(3).setCellValue(commonUser.getPassword());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<CommonUser> UserExcels(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.createSheet("users");
            Iterator<Row> rows = sheet.iterator();

            List<CommonUser> commonUsers =new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

               CommonUser commonUser=new CommonUser();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            commonUser.setId((long) currentCell.getNumericCellValue());
                            break;

                        case 1:
                            commonUser.setName(currentCell.getStringCellValue());
                            break;

                        case 2:
                            commonUser.setUsername(currentCell.getStringCellValue());
                            break;

                        case 3:
                            commonUser.setPassword(currentCell.getStringCellValue());
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                commonUsers.add(commonUser);
            }

            workbook.close();

            return commonUsers;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
    public static ByteArrayInputStream BookToExcel(List<Book> bookList) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (Book book : bookList) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(book.getId());
                row.createCell(1).setCellValue(book.getTitle());
                row.createCell(2).setCellValue(book.getDescription());
                row.createCell(3).setCellValue(book.getAuthor());
                row.createCell(4).setCellValue(book.getPrice());
                row.createCell(5).setCellValue(book.getCreatedAt());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<Book> BooktoExcel(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEETS);
            Iterator<Row> rows = sheet.iterator();

            List<Book> books =new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

              Book book=new Book();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            book.setId((long) currentCell.getNumericCellValue());
                            break;

                        case 1:
                            book.setTitle(currentCell.getStringCellValue());
                            break;

                        case 2:
                            book.setAuthor(currentCell.getStringCellValue());
                            break;

                        case 3:
                            book.setDescription(currentCell.getStringCellValue());
                            break;
                        case 4:
                            book.setPrice(Double.valueOf(currentCell.getStringCellValue()));
                            break;

                        case 5:
                            book.setCreatedAt(Timestamp.valueOf(currentCell.getStringCellValue()));
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                books.add(book);
            }

            workbook.close();

            return books;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
