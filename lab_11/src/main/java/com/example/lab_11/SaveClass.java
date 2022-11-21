package com.example.lab_11;


import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.sql.SQLException;

public class SaveClass {
    static ExcelSave excelSave;
    static JsonSave jsonSave;
    static TxtSave txtSave;

    public SaveClass() {
        initialize();
    }

    public void initialize() {
        excelSave = new ExcelSave();
        jsonSave = new JsonSave();
        txtSave = new TxtSave();
    }
    /**
     * сохранение в формате xlsx
     */
    public static void SaveToExcel() {
        excelSave.saveToFile("Wow.xls");
    }


    /**
     * сохранение в формате json
     */
    public static void SaveToJson() throws IOException, SQLException {
        jsonSave.saveToFile("Wow.json");
    }

    /**
     * сохранение в формате txt
     */
    public static void SaveToTxt() throws IOException, SQLException {
        txtSave.saveToFile("Wow.txt");
    }


}