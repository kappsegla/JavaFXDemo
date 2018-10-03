package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    Button button1;
    @FXML
    Button button2;
    @FXML
    TextField textField1;
    @FXML
    TextField textField2;
    @FXML
    Canvas canvas;

    Model model = new Model();

    public Controller() {

    }

    public void button1Action(ActionEvent actionEvent) {
        model.setText("Button 1 was just pressed");
        model.setDisabled(!model.getDisabled());
    }

    public void button2Action(ActionEvent actionEvent) {
        model.setText("Button two");
    }

    public void init() {
        //Make our bindings here, everything is loaded.
        model.textProperty().bindBidirectional(textField1.textProperty());
        model.textProperty().bindBidirectional(textField2.textProperty());
        button2.disableProperty().bind(model.disabledProperty());
    }
}
