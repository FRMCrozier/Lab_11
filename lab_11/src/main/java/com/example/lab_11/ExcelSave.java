package com.example.lab_11;

import javafx.scene.control.Alert;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ExcelSave {


    /**
     * метод для заполнения HSSFSheet данными из List
     */

    private static void createSheetHeader(HSSFSheet sheet, int rowNum, Wow wow) {

        Row row = sheet.createRow(rowNum);
        row.createCell((short)0).setCellValue(wow.getWowID());
        row.createCell((short)1).setCellValue(wow.getMovie());
        row.createCell((short)2).setCellValue(wow.getYear());
        row.createCell((short) 3).setCellValue(Date.valueOf(wow.getReleaseDate()));
        row.createCell((short) 4).setCellValue(wow.getDirector());
        row.createCell((short)5).setCellValue(wow.getCharacter());
        row.createCell((short)5).setCellValue(Time.valueOf(wow.getMovieDuration()));
        row.createCell((short)7).setCellValue(Time.valueOf(wow.getTimestamp()));
        row.createCell((short)8).setCellValue(wow.getFullLine());
        row.createCell((short)9).setCellValue(wow.getCurrentWowInMovie());
        row.createCell((short)10).setCellValue(wow.getTotalWowsInMovie());
        row.createCell((short)11).setCellValue(wow.getPoster());
        row.createCell((short)12).setCellValue(wow.getVideo());
        row.createCell((short)13).setCellValue(wow.getAudio());
    }


    /**
     * метод сохранения данных в файл
     */
    public static void saveToFile(String filename) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Sheet1");
        DBClass.ConnectDB();
        DBClass.LoadDT();
        List <Wow> list = DBClass.getData();
        int rowNum = 0;
        HSSFRow fr = hssfSheet.createRow(rowNum);
        fr.createCell((short) 0).setCellValue("WowID");
        fr.createCell((short)1).setCellValue("Movie");
        fr.createCell((short)2).setCellValue("movieYear");
        fr.createCell((short)3).setCellValue("ReleaseDate");
        fr.createCell((short)4).setCellValue("Director");
        fr.createCell((short)5).setCellValue("MovieCharacter");
        fr.createCell((short)6).setCellValue("MovieDuration");
        fr.createCell((short)7).setCellValue("TimestampM");
        fr.createCell((short)8).setCellValue("FullLine");
        fr.createCell((short)9).setCellValue("CurrentWow");
        fr.createCell((short)10).setCellValue("TotalWow");
        fr.createCell((short)11).setCellValue("Poster");
        fr.createCell((short)12).setCellValue("Video");
        fr.createCell((short)13).setCellValue("Audio");
        for (Wow w : list) {
            createSheetHeader(hssfSheet, rowNum++, w);
        }
        try (FileOutputStream out = new FileOutputStream(filename)) {
            hssfWorkbook.write(out);
        } catch (IOException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error!");
        }
        DBClass.Exit();
        MBox.MessageBox(Alert.AlertType.INFORMATION, null, "TABLE SAVED TO EXCEL FILE");
    }
}