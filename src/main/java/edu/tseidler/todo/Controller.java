package edu.tseidler.todo;

import edu.tseidler.todo.datamodel.TodoData;
import edu.tseidler.todo.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class Controller {
    private List<TodoItem> todoItems;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy").withLocale(Locale.US);
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadlineLabel;
    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {
        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleSelection(newValue);
        });

        todoListView.setItems(TodoData.getInstance().getTodoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();
    }

    private void handleSelection(TodoItem newValue) {
        if (newValue != null) {
            TodoItem item = todoListView.getSelectionModel().getSelectedItem();
            itemDetailsTextArea.setText(item.getDetails());
            deadlineLabel.setText(formatter.format(item.getDeadline()));
        }
    }

    @FXML
    public void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new Todo item");
        dialog.setHeaderText("Use this dialog to create a new todo item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoitemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> testForOKAndCancelButtons(buttonType, fxmlLoader));
    }

    private void testForOKAndCancelButtons(ButtonType buttonType, FXMLLoader fxmlLoader) {
        if (buttonType == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            try {
                TodoItem newItem = controller.processResults();
                todoListView.getSelectionModel().select(newItem);
            } catch (IllegalArgumentException e) {

            }
            System.out.println("OK pressed");
        } else if (buttonType == ButtonType.CANCEL)
            System.out.println("CANCEL pressed");
    }
}
