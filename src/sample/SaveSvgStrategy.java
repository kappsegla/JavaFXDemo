package sample;

import javafx.scene.shape.Shape;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveSvgStrategy {
    public void save(List<Shape> shapeList, File file) {

        String svgText = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">";

        for (Shape shape : shapeList) {
            svgText += shape.toString();

            //Override toString in all Shape types
            //"<circle cx=\"110\" cy=\"107\" r=\"80\"  fill=\"red\"/>\n"
            //"<rect x="25" y="25" width="200" height="200" fill="lime"/>\n"
        }

        svgText += "</svg>";

        //Run file saving on it's own thread
        //  Thread thread = new Thread( () -> {
        //Save svg string to file
        writeTextFile(file, svgText);
        //  });
        //thread.start();


    }

    /**
     * Writes information to a textfile
     *
     * @param file File to write to
     * @param text String with text to write to file
     */
    public static void writeTextFile(File file, String text) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
