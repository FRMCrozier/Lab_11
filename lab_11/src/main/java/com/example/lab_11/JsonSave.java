package com.example.lab_11;

import com.alibaba.fastjson.JSON;
import javafx.scene.control.Alert;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class JsonSave {

    /**
     * метод сохранения в файл JSON
     */
    public static void saveToFile(String filename) throws IOException, SQLException {
        DBClass.ConnectDB();
        DBClass.LoadDT();
        List<Wow> list = DBClass.getData();
        FileWriter outStream = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(outStream);
        try {
            bw.write(JSON.toJSONString(list));
        } catch (IOException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error!");
        }
        bw.close();
        outStream.close();
        DBClass.Exit();
        MBox.MessageBox(Alert.AlertType.INFORMATION, null, "TABLE SAVED TO JSON FILE");

    }
}