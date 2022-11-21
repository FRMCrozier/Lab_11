package com.example.lab_11;

import javafx.scene.control.Alert;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TxtSave {

    /**
     * метод сохранения в файл TXT
     */
    public static void saveToFile(String filename) throws IOException, SQLException {
        DBClass.ConnectDB();
        DBClass.LoadDT();
        List<Wow> list = DBClass.getData();
        FileWriter fileWriter = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        for (Wow w : list) {
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
        fileWriter.close();
        DBClass.Exit();
        MBox.MessageBox(Alert.AlertType.INFORMATION, null, "TABLE SAVED TO TXT FILE");
    }

}