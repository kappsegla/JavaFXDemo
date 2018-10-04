package sample;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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
    @FXML
    ImageView imageView;

    @FXML
    ChoiceBox choiceBox;

    Model model = new Model();

    public Controller() {

    }

    public void button1Action(ActionEvent actionEvent) {
        model.setText("Button 1 was just pressed");
        model.setDisabled(!model.isDisabled());
    }

    public void button2Action(ActionEvent actionEvent) {
        model.setText("Button two");
        //    Image image = new Image("https://img00.deviantart.net/547a/i/2010/267/7/5/duke_from_java_by_reallyn00b-d2zdiy7.png"
        //            ,true);
        //    imageView.setImage(image);
        downloadWithTask();
        //downloadWithRunnable();
    }

    private void downloadWithTask() {
        Task<Image> task = new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                Image image = new Image("https://img00.deviantart.net/547a/i/2010/267/7/5/duke_from_java_by_reallyn00b-d2zdiy7.png", false);
                throw new Exception();

            }
        };

        imageView.imageProperty().bind(task.valueProperty());

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        //Register for events from task. Will run on gui thread
        task.setOnScheduled(event -> button2.setText("Started..."));
        task.setOnSucceeded(event -> button2.setText("Download complete"));
        task.setOnFailed(event -> button2.setText("Failed"));
    }

    private void downloadWithRunnable() {
        //File read takes time. Run in own thread so gui doesn't block.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = Controller.class.getResource("/alternatives.txt");

                try {
                    List<String> list = Files.lines(Paths.get(url.toURI())).collect(Collectors.toList());

                    //Ok to change model from other thread than gui-thread
                    model.getChoiceOptions().addAll(list);

                    //Not ok to call methods on gui objects from other thread than gui-thread.
                    // button1.setDisable(true);

                    //Run on gui thread instead
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            button1.setDisable(true);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    public void init() {
        //Make our bindings here, everything is loaded.
        model.textProperty().bindBidirectional(textField1.textProperty());
        model.textProperty().bindBidirectional(textField2.textProperty());
        button2.disableProperty().bind(model.disabledProperty());
        model.getObservableList().addListener((ListChangeListener<Point2D>) c -> draw());

        //    model.getChoiceOptions().addAll("Alt1", "Alt2", "Alt3");
        choiceBox.setItems(model.getChoiceOptions());

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
