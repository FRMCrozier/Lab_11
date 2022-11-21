package com.example.lab_11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class DBClass {
    private static Connection con;
    private static ObservableList<Wow> data;

    public static ObservableList<Wow> getData() {
        return data;
    }
    public static void setData(ObservableList<Wow> d) {
        data = d;
    }

    static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");
        properties.setProperty("serverTimezone", "UTC");
        properties.setProperty("useSSL", "false");
        properties.setProperty("autoReconnect", "true");

        return properties;
    }

    /**
     * Подключение к БД
     */

    private static boolean DBconnect(String conn) {
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error!");
        }

        try {
            String url = "jdbc:mysql://" + conn;
            con = getConnection(url, getProperties());
            result = true;
            MBox.MessageBox(Alert.AlertType.INFORMATION, null, "Connected successfully");
        } catch (SQLException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error!");
        }

        return result;
    }

    public static void ConnectDB() {
        DBconnect("localhost:3306/test");
    }

    /**
     * Загрузка апи в БД
     */
    public static void DownloadDB() throws IOException {
        try {
            con = getConnection(Constants.URL, getProperties());
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("use test;");

            for (Wow wow : Wows.download()) {
                PreparedStatement st1 = con.prepareStatement("insert into wows( Movie, MovieYear, ReleaseDate, Director, MovieCharacter, MovieDuration, TimestampM, FullLine, CurrentWow, TotalWow, Poster, Video, Audio) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                st1.setString(1, wow.getMovie());
                st1.setInt(2, wow.getYear());
                st1.setDate(3, Date.valueOf(wow.getReleaseDate()));
                st1.setString(4, wow.getDirector());
                st1.setString(5, wow.getCharacter());
                st1.setTime(6, Time.valueOf(wow.getMovieDuration()));
                st1.setTime(7, Time.valueOf(wow.getTimestamp()));
                st1.setString(8, wow.getFullLine());
                st1.setInt(9, wow.getCurrentWowInMovie());
                st1.setInt(10, wow.getTotalWowsInMovie());
                st1.setString(11, wow.getPoster());
                st1.setString(12, wow.getVideo());
                st1.setString(13, wow.getAudio());
                System.out.println("\nParametrized query" + st1);
                int result = st1.executeUpdate();
                System.out.println("\nInserted " + result + " records");
            }

            rs.close();
            st.close();
            MBox.MessageBox(Alert.AlertType.INFORMATION, null, "Wows are loaded to DB");

        } catch (SQLException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error!");
        }
    }

    /**
     * Разрыв соединения
     */
    public static void Exit() {
        if (con != null) {
            try {
                con.close();
                MBox.MessageBox(Alert.AlertType.INFORMATION, null, "Disconnected successfully");
            } catch (Exception e) {
                MBox.MessageBox(Alert.AlertType.WARNING, null, "Error!");
            }
        }
    }

    public static void LoadDT() {
        try {
            con = getConnection(Constants.URL, DBClass.getProperties());
            Statement statement = con.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from wows;");
            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                data.add(new Wow(resultSet.getInt("WowID"), resultSet.getString("Movie"), resultSet.getInt("movieYear"), resultSet.getDate("ReleaseDate").toLocalDate(), resultSet.getString("Director"), resultSet.getString("MovieCharacter"), resultSet.getTime("MovieDuration").toLocalTime(), resultSet.getTime("TimestampM").toLocalTime(), resultSet.getString("FullLine"),
                        resultSet.getInt("CurrentWow"), resultSet.getInt("TotalWow"), resultSet.getString("Poster"), resultSet.getString("Video"), resultSet.getString("Audio")));
            }
            setData(data);
            //tableWows.setItems(data);
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error! Disconnect!");
        }
    }

    public static void ClearTable(TableView<Wow> tableWows) {
        try {
            con = getConnection(Constants.URL, DBClass.getProperties());
            String query = "TRUNCATE TABLE wows;";
            Statement stm = con.createStatement();
            stm.execute(query);
            LoadDT();
            stm.close();
            MBox.MessageBox(Alert.AlertType.INFORMATION, null, "Table is clear!");

        } catch (SQLException e) {
            MBox.MessageBox(Alert.AlertType.WARNING, null, "Error!");
        }
    }
}
