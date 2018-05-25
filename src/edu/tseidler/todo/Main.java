package edu.tseidler.todo;

import edu.tseidler.todo.datamodel.TodoData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        primaryStage.setTitle("Todo List");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        TodoData.getInstance().loadTodos();
    }

    @Override
    public void stop() throws Exception {
        TodoData.getInstance().storeItems();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
