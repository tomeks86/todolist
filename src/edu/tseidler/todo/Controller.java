package edu.tseidler.todo;

import edu.tseidler.todo.datamodel.TodoData;
import edu.tseidler.todo.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Controller {
    private List<TodoItem> todoItems;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy").withLocale(Locale.US);
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadlineLabel;

    public void initialize() {
        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleSelection(newValue);
        });

        todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
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
    public void shouwNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();

    }

}
