package com.minami.webcrawling.gameInfo;

import com.minami.webcrawling.CrawlingExample;
import com.minami.webcrawling.gameInfo.model.Game;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final CrawlingExample c;

    public void download(HttpServletResponse res) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("GameInfo"); // 엑셀 sheet 이름
        sheet.setDefaultColumnWidth(30); // 디폴트 너비 설정

        /*
          header font style
         */
        XSSFFont headerXSSFFont = (XSSFFont) workbook.createFont();
        headerXSSFFont.setColor(new XSSFColor(new byte[]{(byte) 255, (byte) 255, (byte) 255}));

        /*
          header cell style
         */
        XSSFCellStyle headerXssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();

        // 테두리 설정
        headerXssfCellStyle.setBorderLeft(BorderStyle.THIN);
        headerXssfCellStyle.setBorderRight(BorderStyle.THIN);
        headerXssfCellStyle.setBorderTop(BorderStyle.THIN);
        headerXssfCellStyle.setBorderBottom(BorderStyle.THIN);

        // 배경 설정
        headerXssfCellStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 34, (byte) 37, (byte) 41}));
        headerXssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerXssfCellStyle.setFont(headerXSSFFont);

        /*
          body cell style
         */
        XSSFCellStyle bodyXssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();

        // 테두리 설정
        bodyXssfCellStyle.setBorderLeft(BorderStyle.THIN);
        bodyXssfCellStyle.setBorderRight(BorderStyle.THIN);
        bodyXssfCellStyle.setBorderTop(BorderStyle.THIN);
        bodyXssfCellStyle.setBorderBottom(BorderStyle.THIN);

        /*
          header data
         */
        int rowCount = 0; // 데이터가 저장될 행
        String[] headerNames = new String[]{"게임명", "장르", "게임사"};

        Row headerRow;
        Cell headerCell;

        headerRow = sheet.createRow(rowCount++);
        for (int i = 0; i < headerNames.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headerNames[i]); // 데이터 추가
            headerCell.setCellStyle(headerXssfCellStyle); // 스타일 추가
        }

        Row bodyRow;
        Cell bodyCell;

        List<Game> gameList = c.crawling();
        for (Game g : gameList) {
            bodyRow = sheet.createRow(rowCount++);

            bodyCell = bodyRow.createCell(0);
            bodyCell.setCellValue(g.getName());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellValue(g.getGenre());
            bodyCell.setCellStyle(bodyXssfCellStyle);

            bodyCell = bodyRow.createCell(2);
            bodyCell.setCellValue(g.getCompany());
            bodyCell.setCellStyle(bodyXssfCellStyle);
        }
        /*
          download
         */
        String fileName = "game_info";

        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream servletOutputStream = res.getOutputStream();

        workbook.write(servletOutputStream);
        workbook.close();
        servletOutputStream.flush();
        servletOutputStream.close();
//        WebDriver driver = WebDriverUtil.getChromeDriver();
//        List<WebElement> webElementList = new ArrayList<>();
//        String url = "http://test";
//        String query = "#id";
//
//        if (!ObjectUtils.isEmpty(driver)) {
//            driver.get(url);
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//
//            webElementList = driver.findElements(By.cssSelector(query));
//        }
//
//        WebElement parentElement = webElementList.get(0);
//        List<WebElement> childElement = parentElement.findElements(By.tagName("td"));
//
//        System.out.println(childElement.get(0).getText());
//        System.out.println(parentElement.getAttribute("class"));
//        System.out.println(parentElement.getCssValue("height"));
//
//
//        parentElement.click();
//        parentElement.submit();
    }
}
