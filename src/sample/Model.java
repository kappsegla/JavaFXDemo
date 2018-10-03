package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {

    private StringProperty text;
    private BooleanProperty disabled;

    public boolean getDisabled() {
        return disabled.get();
    }

    public BooleanProperty disabledProperty() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled.set(disabled);
    }

    public Model() {
        text = new SimpleStringProperty();
        disabled = new SimpleBooleanProperty();
    }

    public final String getText() {
        return text.get();
    }

    public void setText(String s) {
        text.setValue(s);
    }

    public StringProperty textProperty() {
        return text;
    }
}
