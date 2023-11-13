package com.example.newyorktimesspellingbee;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.example.newyorktimesspellingbee.controllers.MenuController;
import com.example.newyorktimesspellingbee.core.data.DataFilter;
import com.example.newyorktimesspellingbee.core.data.DataReader;
import com.example.newyorktimesspellingbee.core.data.TxtReader;
import com.example.newyorktimesspellingbee.core.data.WordFilter;
import com.example.newyorktimesspellingbee.models.MenuModel;
import com.example.newyorktimesspellingbee.views.MenuView;

public class App extends Application {
    private static App instance = null;
    private Stage stage = null;
    private DataReader dataReader;
    private DataFilter dataFilter;

    @Override
    public void init() {
        instance = this;
        dataReader = new TxtReader();
        dataFilter = new WordFilter();
        dataReader.read();
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        MenuModel model = new MenuModel();
        MenuController controller = new MenuController(model);
        MenuView view = new MenuView(model, controller);
        Scene scene = new Scene(view.getAsParent());
        scene.getStylesheets().addAll("com/example/newyorktimesspellingbee/style.css");

        stage.setTitle("New York Times SpellingBee");
        stage.getIcons().add(new Image("com/example/newyorktimesspellingbee/logo.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static App getInstance() {
        return instance;
    }

    public void setScene(Scene scene) {
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }

    public DataReader getDataReader() {
        return dataReader;
    }

    public DataFilter getDataFilter() {
        return dataFilter;
    }
}