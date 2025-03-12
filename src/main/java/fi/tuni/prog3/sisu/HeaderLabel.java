package fi.tuni.prog3.sisu;

import javafx.scene.control.*;
import javafx.scene.text.Font;

/**
 * A label that automatically gets header styling with a larger font.
 */
public class HeaderLabel extends Label {

    /**
     * Creates a label with text and sets a header styling for it.
     * @param s the text to be displayed by the label.
     */
    public HeaderLabel(String s)
    {
        this.setText(s);
        this.setFont(Font.font("Verdana", 20));
    }
}
