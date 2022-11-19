package com.example.lab_11;

import com.alibaba.fastjson.JSON;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveClass {

    /**
     * Сохранение в эксель файл
     */
    public static void SaveToExcel(TableView<Wow> tableView) {

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Sheet1");
        HSSFRow firstRow = hssfSheet.createRow(0);

        /*
        установить заголовки колонок
        */
        for (int i = 0; i < tableView.getColumns().size(); i++) {

            firstRow.createCell((short) i).setCellValue(tableView.getColumns().get(i).getText());

        }


        for (int row = 0; row < tableView.getItems().size(); row++) {

            HSSFRow hssfRow = hssfSheet.createRow(row + 1);

            for (int col = 0; col < tableView.getColumns().size(); col++) {

                Object celValue = tableView.getColumns().get(col).getCellObservableValue(row).getValue();

                try {
                    if (celValue != null && Double.parseDouble(celValue.toString()) != 0.0) {
                        hssfRow.createCell((short) col).setCellValue(Double.parseDouble(celValue.toString()));
                    }
                } catch (NumberFormatException e) {

                    hssfRow.createCell((short) col).setCellValue(celValue.toString());
                }

            }

        }

        try {
            hssfWorkbook.write(new FileOutputStream("Wow.xls"));
            hssfWorkbook.close();
            MBox.MessageBox(Alert.AlertType.INFORMATION, null, "TABLE SAVED TO EXCEL FILE");
        } catch (IOException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error!");
        }

    }

    /**
     * Сохранение в джейсон файл с помощью сериализации фастджейсон
     */
    public static void SaveToJson(TableView<Wow> tableView) throws IOException {
        FileWriter outStream = new FileWriter("Wow.json");
        BufferedWriter bw = new BufferedWriter(outStream);
        try {
            bw.write(JSON.toJSONString(new ArrayList<Wow>(tableView.getItems())));
        } catch (IOException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error!");
        }
        bw.close();
        outStream.close();
        MBox.MessageBox(Alert.AlertType.INFORMATION, null, "TABLE SAVED TO JSON FILE");
    }

    /**
     * Сохранение в текстовый файл
     */
    public static void SaveToTxt(TableView<Wow> tableView) throws IOException {

        FileWriter outStream = new FileWriter("Wow.txt");
        BufferedWriter bw = new BufferedWriter(outStream);
        for (Wow w : new ArrayList<Wow>(tableView.getItems())) {
            try {
                bw.write(String.valueOf(w.getWowID()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(w.getMovie()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(w.getYear()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(w.getReleaseDate()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(w.getDirector()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(w.getCharacter()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(w.getMovieDuration()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(w.getTimestamp()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(w.getFullLine()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(w.getCurrentWowInMovie()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(w.getTotalWowsInMovie()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(w.getPoster()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(w.getVideo()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(w.getAudio()));
                bw.write(System.lineSeparator());
            } catch (IOException e) {
                MBox.MessageBox(Alert.AlertType.WARNING, null, "Error!");
            }
        }

        bw.close();
        outStream.close();
        MBox.MessageBox(Alert.AlertType.INFORMATION, null, "TABLE SAVED TO TXT FILE");
    }
}
