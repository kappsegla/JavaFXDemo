package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Controller {

    public void button1Action(ActionEvent actionEvent) {
        ((Button) actionEvent.getSource()).setText("Clicked");
    }
}
