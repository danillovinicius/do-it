package com.dvlima.javafx.doit;

import com.dvlima.javafx.doit.model.Task;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    private Controller controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui.fxml"));
        
        GridPane grid = loader.load();
        controller = loader.getController();
        
        Scene scene = new Scene(grid, 600, 450);
        
        stage.setScene(scene);
        stage.setTitle("TODO");
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        
        stage.setOnCloseRequest(this::onClose);
        controller.setTasksMap(readTasksFile());
        stage.show();
    }

    private HashMap<Integer, Task> readTasksFile() {
        FileInputStream in = null;
        HashMap<Integer, Task> tasksMap = new HashMap<Integer, Task>();
        try {
            in = new FileInputStream("tasks.xml");
            XMLDecoder decoder = new XMLDecoder(in);
            tasksMap = (HashMap<Integer, Task>) decoder.readObject();
            decoder.close();
        } catch (Exception e) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            return tasksMap;
        }
    }

    private void onClose(WindowEvent event) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("tasks.xml");
            XMLEncoder encoder = new XMLEncoder(out);
            encoder.writeObject(controller.getTasksMap());
            encoder.close();
        } catch (Exception e) {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
