package edu.tseidler.todo.datamodel;

import edu.tseidler.Main;
import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class TodoData {
    private static TodoData instance = new TodoData();
    private String fileName = Main.class.getResource("todos.txt").getPath();

    private List<TodoItem> todoItems;
    private DateTimeFormatter formatter;

    public static TodoData getInstance() {
        return instance;
    }

    private TodoData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyy").withLocale(Locale.US);
        loadTodos();
    }

    public List<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void loadTodos() {
        todoItems = FXCollections.observableArrayList();
        Path path = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String input;
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");
                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                LocalDate deadline = LocalDate.parse(itemPieces[2], formatter);
                todoItems.add(new TodoItem(shortDescription, details, deadline));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeItems() {
        Path path = Paths.get(fileName);
        try (BufferedWriter br = Files.newBufferedWriter(path)) {
            StringBuilder sb = new StringBuilder();
            for (TodoItem todoItem : todoItems) {
                br.write(String.format("%s\t%s\t%s\n",
                        todoItem.getShortDescription(),
                        todoItem.getDetails(),
                        todoItem.getDeadline().format(formatter)));
            }
            br.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTodoItem(TodoItem todoItem) {
        todoItems.add(todoItem);
    }
}
