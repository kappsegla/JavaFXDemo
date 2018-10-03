package sample;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
        model.setDisabled(!model.isDisabled());
    }

    public void button2Action(ActionEvent actionEvent) {
        model.setText("Button two");
    }

    public void init() {
        //Make our bindings here, everything is loaded.
        model.textProperty().bindBidirectional(textField1.textProperty());
        model.textProperty().bindBidirectional(textField2.textProperty());
        button2.disableProperty().bind(model.disabledProperty());

        model.getObservableList().addListener((ListChangeListener<Point2D>) c -> draw());
    }

    public void canvasClicked(MouseEvent event) {
        Point2D point = new Point2D(event.getX(), event.getY());
        model.getObservableList().add(point);
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Paint p = Color.RED;
        gc.setFill(p);
        for (Point2D point : model.getObservableList()) {
            gc.fillOval(point.getX() - 10, point.getY() - 10, 20, 20);
        }
        gc.setLineWidth(5.0);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

}
