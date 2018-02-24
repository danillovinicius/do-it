package com.dvlima.javafx.doit;

import com.dvlima.javafx.doit.model.Task;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author danilolima
 */
public class Controller implements Initializable {

    private final Task currentTask = new Task();

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    private final HashMap<Integer, Task> tasksMap = new HashMap<>();

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TableView<Task> tasksTable;

    @FXML
    private TableColumn<Task, String> priorityColumn;

    @FXML
    private TableColumn<Task, String> descriptionColumn;

    @FXML
    private TableColumn<Task, String> progressColumn;

    @FXML
    private ComboBox<String> prioritiesCmb;

    @FXML
    private TextField taskDescription;

    @FXML
    private Button add;

    @FXML
    private Spinner<Integer> progressSpinner;

    @FXML
    private CheckBox completedCheckBox;

    @FXML
    private Button cancel;

    private int lastId = 0;

    @FXML
    private void addButtonCliked(ActionEvent event) {
        if (currentTask.getId() == null) {
            Task task = new Task(++lastId, currentTask.getPriority(), currentTask.getDescription(), currentTask.getProgress());
            tasks.add(task);
            tasksMap.put(lastId, task);
        } else {
            Task taskUpdate = tasksMap.get(currentTask.getId());
            taskUpdate.setDescription(currentTask.getDescription());
            taskUpdate.setPriority(currentTask.getPriority());
            taskUpdate.setProgress(currentTask.getProgress());
        }

        setCurrentTask(null);
    }

    @FXML
    private void cancelButtonCliked(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Deseja cancelar?");
        alert.setTitle("Cancelar");
        alert.getButtonTypes().remove(0, 2);
        alert.getButtonTypes().add(0, ButtonType.OK);
        alert.getButtonTypes().add(0, ButtonType.CANCEL);

        Optional<ButtonType> confirmationResponse = alert.showAndWait();

        if (confirmationResponse.get() == ButtonType.OK) {
            setCurrentTask(null);
            tasksTable.getSelectionModel().clearSelection();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prioritiesCmb.getItems().addAll("High", "Medium", "Low");
        progressSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));

        progressSpinner.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
            completedCheckBox.setSelected(100 == newValue.intValue());
        });

        ReadOnlyIntegerProperty intProgress = ReadOnlyIntegerProperty.readOnlyIntegerProperty(progressSpinner.valueProperty());
        progressBar.progressProperty().bind(intProgress.divide(100.0));

        prioritiesCmb.valueProperty().bindBidirectional(currentTask.priorityStringProperty());
        taskDescription.textProperty().bindBidirectional(currentTask.descriptionStringProperty());
        progressSpinner.getValueFactory().valueProperty().bindBidirectional(currentTask.progressObjectProperty());

        tasksTable.setItems(tasks);
        priorityColumn.setCellValueFactory(rowData -> rowData.getValue().priorityStringProperty());
        descriptionColumn.setCellValueFactory(rowData -> rowData.getValue().descriptionStringProperty());
        progressColumn.setCellValueFactory(rowData -> Bindings.concat(rowData.getValue().progressObjectProperty(), "%"));

        StringBinding addButtonTextBinding = new StringBinding() {
            {
                super.bind(currentTask.idObjectProperty());
            }

            @Override
            protected String computeValue() {
                return currentTask.getId() == null ? "Add" : "Update";
            }
        };

        add.textProperty().bind(addButtonTextBinding);
        add.disableProperty().bind(Bindings.greaterThan(3, currentTask.descriptionStringProperty().length()));

        tasksTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCurrentTask(newValue);
        });
    }

    private void setCurrentTask(Task selectedTask) {
        if (selectedTask != null) {
            currentTask.setId(selectedTask.getId());
            currentTask.setPriority(selectedTask.getPriority());
            currentTask.setDescription(selectedTask.getDescription());
            currentTask.setProgress(selectedTask.getProgress());
        } else {
            currentTask.setId(null);
            currentTask.setPriority("");
            currentTask.setDescription("");
            currentTask.setProgress(0);
        }
    }

    public HashMap<Integer, Task> getTasksMap() {
        return tasksMap;
    }

    void setTasksMap(HashMap<Integer, Task> initialTasksMap) {
        tasksMap.clear();
        tasks.clear();
        
        if(initialTasksMap != null){
            tasksMap.putAll(initialTasksMap);
            tasks.addAll(initialTasksMap.values());
            lastId = tasksMap.keySet().stream().max(Integer::compare).get();
        }
        
    }
}
