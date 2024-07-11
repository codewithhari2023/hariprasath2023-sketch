package OnlineEBookStore.demo.Service;

import OnlineEBookStore.Helper.ExcelHelper;
import OnlineEBookStore.demo.Dao.ExcelDao;
import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.ExcelUpload;
import OnlineEBookStore.demo.Model.Roles;
import OnlineEBookStore.demo.Repository.BookRepository;
import OnlineEBookStore.demo.Repository.ExcelRepository;
import OnlineEBookStore.demo.Repository.UserRepository;
import OnlineEBookStore.demo.Request.ExcelRequest;
import OnlineEBookStore.demo.Utils.ExcelUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ExcelService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ExcelRepository excelRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExcelDao excelDao;

    public ByteArrayOutputStream writeDataToExcel(ExcelRequest excelRequest) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(excelRequest.getFilename());
        // Add headers to Excel sheet
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Title");

        headerRow.createCell(1).setCellValue("Author");
        headerRow.createCell(2).setCellValue("description");
        headerRow.createCell(3).setCellValue("Price");

        // Add book data to Excel sheet
        int rowNum = 1;
        for (Book book : excelRequest.getBooks()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(book.getTitle());
            row.createCell(1).setCellValue(book.getAuthor());
            row.createCell(2).setCellValue(book.getDescription());
            row.createCell(3).setCellValue(book.getPrice());

        }

        // Write Excel to ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
       headers.setContentDispositionFormData("attachment", "product_Book.xlsx");
        return outputStream;
    }

    public ByteArrayOutputStream writeUserDataToExcel(List<CommonUser> commonUsers, String sheetName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        // Add headers to Excel sheet
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Username");
        headerRow.createCell(1).setCellValue("Email");
        headerRow.createCell(2).setCellValue("Password");
        headerRow.createCell(3).setCellValue("Address");
        headerRow.createCell(4).setCellValue("Cart");
        headerRow.createCell(5).setCellValue("Orders");
        headerRow.createCell(6).setCellValue("Role");
        // Add book data to Excel sheet
        int rowNum = 1;
        for (CommonUser user : commonUsers) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getUsername());
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getPassword());


        }

        // Write Excel to ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "product_User.xlsx");
        return outputStream;

    }
    public void saveUSerExcelFile(List<CommonUser> commonUsers,String fileName) throws IOException {
        ByteArrayOutputStream outputStream = writeUserDataToExcel(commonUsers, fileName);

        // Save Excel file to a specified location
        String filePath = "src/main/java/OnlineEBookStore/demo/Excel/" + fileName + ".xlsx";
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        outputStream.writeTo(fileOutputStream);
        fileOutputStream.close();
        Roles roles=new Roles();
        // Store file details in the repository
        ExcelUpload excelFile = new ExcelUpload();
        excelFile.setUploadFileName(fileName + ".xlsx");
        excelFile.setRole(roles.getRole());
        excelRepository.save(excelFile);
    }
    public void saveExcelFile(ExcelRequest excelRequest) throws IOException {
        try {
            ByteArrayOutputStream outputStream = writeDataToExcel(excelRequest);

            // Save Excel file to a specified location
            String filePath = "src/main/java/OnlineEBookStore/demo/Excel/" + excelRequest.getFilename() + ".xlsx";
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            outputStream.writeTo(fileOutputStream);
            fileOutputStream.close();
            Roles roles = new Roles();
            // Store file details in the repository
            ExcelUpload excelFile = new ExcelUpload();
            excelFile.setPath(excelRequest.getFilename());
            excelFile.setUploadFileName(excelRequest.getFilename() + ".xlsx");
            excelFile.setRole(roles.getRole());
            excelRepository.save(excelFile);
        }catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
            e.printStackTrace();
        }
    }
//    public static boolean isvalid(MultipartFile file)
//    {
//        return Objects.equals(file.getContentType(),"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//
//    }
//    public  static List<CommonUser> getUserData(InputStream inputStream) throws IOException {
//         List<CommonUser> commonUsers=new ArrayList<>();
//         XSSFWorkbook workbooks=new XSSFWorkbook(inputStream);
////        FileInputStream fileInputStream = new FileInputStream("src/main/java/OnlineEBookStore/demo/Excel/PublicUser.xlsx");
////        Workbook workbook = WorkbookFactory.create(fileInputStream);
//
//        XSSFSheet sheet=workbooks.getSheet(0y)"commonuser");
//
//        int rowIndex=0;
//        for (Row row:sheet)
//        {
//            if(rowIndex==0)
//            {
//                rowIndex++;
//                continue;
//            }
//            Iterator<Cell>cellIterator=row.iterator();
//            int cellIndex=0;
//            CommonUser commonUser=new CommonUser();
//            while (cellIterator.hasNext()){
//                Cell cell=  cellIterator.next();
//                switch (cellIndex)
//                {
//                    case 0->commonUser.setId((long) cell.getNumericCellValue());
//                    case 1->commonUser.setName(cell.getStringCellValue());
//                    case 2->commonUser.setUsername(cell.getStringCellValue());
//                    case 3->commonUser.setPassword(cell.getStringCellValue());
//                    default -> {}
//
//                }
//                cellIndex++;
//                commonUsers.add(commonUser);
//            }
//
//        }
//        return commonUsers;
//    }

    public void save(MultipartFile file) {
        try {
            List<CommonUser> commonUsers = readExcelFile(file.getInputStream());

            userRepository.saveAll(commonUsers);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }
    public void savefile(MultipartFile file) {
        try {
            List<Book>bookList = ExcelHelper.BooktoExcel(file.getInputStream());

            bookRepository.saveAll(bookList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load() {
        List<CommonUser> commonUserList = userRepository.findAll();

        ByteArrayInputStream in = ExcelHelper.UserToExcel(commonUserList);
        return in;
    }

    public List<CommonUser> getAllTutorials() {
        return userRepository.findAll();
    }


    private List < CommonUser> commonUsers;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private void writeHeader() {
        sheet = workbook.createSheet("Student");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Student Name", style);
        createCell(row, 2, "Email", style);
        createCell(row, 3, "Mobile No.", style);
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (CommonUser record: commonUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getName(), style);
            createCell(row, columnCount++, record.getUsername(), style);
            createCell(row, columnCount++, record.getPassword(), style);
        }
    }
//    public byte[] generateExcelFile(ExcelRequest excelRequest) {
//        String query = excelRequest.getFilename();
//        List<Book> columns = excelRequest.getBooks();
//        List<Map<String, Object>> queryResults =excelDao.executeQuery(query);
//        return ExcelUtils.generateExcelFile(queryResults, columns);
//    }
    public static void exportDataToExcel(List<CommonUser> commonUsers, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        int rowNum = 0;
        for (CommonUser data : commonUsers) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.getId());
            row.createCell(1).setCellValue(data.getName());
            row.createCell(2).setCellValue(data.getUsername());
            row.createCell(3).setCellValue(data.getPassword());

            // Add more columns as needed
        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }



    @Autowired
    private ExcelRepository studentRepository;

    public void saves(MultipartFile file) throws EncryptedDocumentException, IOException {
        List<List<String>> rows = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
// Iterator<Row> rowIterator = sheet.iterator();
        rows = StreamSupport.stream(sheet.spliterator(), false)
                .map(row -> StreamSupport
                        .stream(row.spliterator(), false)
                        .map(this::getCellStringValue)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        System.out.println("rows :: " + rows);
// Save data to the database
        List<CommonUser> excelDataList = rows.stream().map(row -> {
            CommonUser excelData = new CommonUser();
            excelData.setName(row.get(0));
            excelData.setUsername(row.get(1));
            excelData.setPassword(row.get(2));
            return excelData;
        }).collect(Collectors.toList());
//        studentRepository.saveAll(excelDataList);
    }
    private String getCellStringValue(Cell cell) {
        CellType cellType = cell.getCellType();

        if (cellType == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cellType == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else if (cellType == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        }

        return null;
    }
    public static List<CommonUser> readExcelFile(InputStream is) throws IOException {
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
        Iterator<Cell> cellsInRow;
        List<CommonUser> userList = new ArrayList<>();

        for (Row row : sheet) {
            cellsInRow = row.iterator();
            CommonUser user = new CommonUser();

            // Assuming the cell order is ID, Name, Email
            Cell idCell = cellsInRow.next();
            user.setId((long) idCell.getNumericCellValue());

            Cell nameCell = cellsInRow.next();
            user.setName(nameCell.getStringCellValue());

            Cell emailCell = cellsInRow.next();
            user.setUsername(emailCell.getStringCellValue());

            userList.add(user);
        }

        workbook.close();
        return userList;
    }
}

