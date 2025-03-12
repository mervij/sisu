package fi.tuni.prog3.sisu;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Displays information about things happening in the program for the user, including errors
 */
public class StatusDisplayer {
    /**
     * Determines whether the status message is an info or an error message.
     */
    public enum DisplayMode
    {
        Info,
        Error
    }

    private static Label statusLabel;

    /**
     * Sets the label in which you want to write the messages.
     * @param label The label to write the messages to.
     */
    public static void setStatusLabel(Label label)
    {
        statusLabel = label;
    }

    /**
     * Writes a status message to the bottom of the screen.
     * @param message The message to be displayed.
     * @param displayMode Is the message an info message or and error message
     */
    public static void setStatus(String message, DisplayMode displayMode)
    {
        if (statusLabel != null)
        {
            String statusHeader = "";

            switch (displayMode)
            {
                case Info:
                    statusHeader = "Info: ";
                    statusLabel.setTextFill(Color.BLACK);
                    break;
                case Error:
                    statusHeader = "Virhe: ";
                    statusLabel.setTextFill(Color.DARKRED);
                    break;
            }

            statusLabel.setText(statusHeader + message);
        }
    }

    /**
     * Writes an info message to the bottom of the screen.
     * @param message The message to be displayed.
     */
    public static void setStatus(String message)
    {
        setStatus(message, DisplayMode.Info);
    }
}
