package com.example.lab_11;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public TableView<Wow> tableWows;
    @FXML
    private ChoiceBox<String> chooseFormat;
    @FXML
    TableColumn<Wow, Integer> WowID; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> Movie; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, Integer> movieYear;
    @FXML
    TableColumn<Wow, String> ReleaseDate; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> Director; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> MovieCharacter; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> MovieDuration; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> TimestampM; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> FullLine; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, Integer> CurrentWow; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, Integer> TotalWow; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> Poster; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> Video; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    @FXML
    TableColumn<Wow, String> Audio; //NOPMD - suppressed FieldNamingConventions - TODO explain reason for suppression
    private Connection con;


    /**
     * Подключение к БД
     */


    public void ConnectDB() {
        DBClass.ConnectDB();
    }

    /**
     * Загрузка апи в БД
     */
    public void DownloadDB() throws IOException {
        DBClass.DownloadDB();
    }

    /**
     * Разрыв соединения
     */
    public void Exit() {
        DBClass.Exit();
    }

    /**
     * Вспомогательный метод для загрузки значений из БД в таблицу
     */
    public void Load() {
        WowID.setCellValueFactory(new PropertyValueFactory<>("WowID"));
        Movie.setCellValueFactory(new PropertyValueFactory<>("Movie"));
        movieYear.setCellValueFactory(new PropertyValueFactory<>("Year"));
        ReleaseDate.setCellValueFactory(new PropertyValueFactory<>("ReleaseDate"));
        Director.setCellValueFactory(new PropertyValueFactory<>("Director")); //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
        MovieCharacter.setCellValueFactory(new PropertyValueFactory<>("Character")); //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
        MovieDuration.setCellValueFactory(new PropertyValueFactory<>("MovieDuration"));
        TimestampM.setCellValueFactory(new PropertyValueFactory<>("Timestamp")); //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
        FullLine.setCellValueFactory(new PropertyValueFactory<>("FullLine")); //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
        CurrentWow.setCellValueFactory(new PropertyValueFactory<>("currentWowInMovie"));
        TotalWow.setCellValueFactory(new PropertyValueFactory<>("totalWowsInMovie")); //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
        Poster.setCellValueFactory(new PropertyValueFactory<>("Poster"));
        Video.setCellValueFactory(new PropertyValueFactory<>("Video")); //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
        Audio.setCellValueFactory(new PropertyValueFactory<>("Audio"));
    }
    //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression

    /**
     * //NOPMD - suppressed AvoidDuplicateLiterals - TODO explain reason for suppression
     * Загрузка из БД в таблицу
     */
    public void LoadDT(ActionEvent actionEvent) {
        DBClass.LoadDT(tableWows);
        Load();
    }

    /**
     * Очистка таблицы
     */
    public void ClearTable(ActionEvent actionEvent) {
        DBClass.ClearTable(tableWows);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        con = null;
        chooseFormat.getItems().addAll(".xls", ".json", ".txt");
        chooseFormat.setOnAction(this::getFormat);
    }

    /**
     * Выбор формата файла, в который сохранится таблица
     */
    public void getFormat(ActionEvent event){

        String myformat = chooseFormat.getValue();
        System.out.println(myformat);
        DBClass.LoadDT(tableWows);
        switch (myformat) {
            case  (".xls"):
                try{
                    SaveClass.SaveToExcel(tableWows);
                }
                catch(Exception e)
                {
                    MBox.MessageBox(Alert.AlertType.WARNING, null, "Create the table!");
                }
                break;

            case (".json"):
                try {
                    SaveClass.SaveToJson(tableWows);
                }
                catch(Exception e)
                {
                    MBox.MessageBox(Alert.AlertType.WARNING, null, "Create the table!");
                }
                break;

            case (".txt"):
                try{
                    SaveClass.SaveToTxt(tableWows);
                }
                catch (Exception e){
                    MBox.MessageBox(Alert.AlertType.WARNING, null, "Create the table!");
                }
                break;
        }
    }

}