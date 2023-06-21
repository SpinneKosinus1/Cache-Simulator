package org.example.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.example.Model.Model;

import java.io.File;

public class Controller {
    // Variables
    // Model
    private Model Model;

    // FilePath
    String FilePath;

    @FXML
    private GridPane GridPane;

    @FXML
    private TextField BlockNumber;

    @FXML
    private TextField BlockSize;

    @FXML
    private TextField Associativity;

    @FXML
    private ComboBox Replacement;

    @FXML
    private ComboBox WriteHit;

    @FXML
    private ComboBox WriteMiss;

    public void StartSimulation() {
        if (FilePath != null) {
            Model = new Model(Integer.parseInt(BlockNumber.getText()), Integer.parseInt(BlockSize.getText()),
                    Integer.parseInt(Associativity.getText()), Replacement.getValue().toString(),
                    WriteHit.getValue().toString(), WriteMiss.getValue().toString());

            Model.StartSimulation(FilePath);
        }
    }

    public void LoadFile(ActionEvent actionEvent) {
        // get the file selected
        FileChooser FileChooser = new FileChooser();
        Window stage = GridPane.getScene().getWindow();
        FileChooser.setTitle("Load TraceFile");

        try {
            File file = FileChooser.showOpenDialog(stage);
            FileChooser.setInitialDirectory(file.getParentFile());
            FilePath = file.getPath();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
