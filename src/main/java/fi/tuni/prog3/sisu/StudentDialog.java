package fi.tuni.prog3.sisu;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A dialog that asks for a student number
 */
public class StudentDialog {
    /**
     * Is the string empty or not?
     * @param text The text to inspect.
     * @return true if the text is empty or contains only whitespace, false otherwise.
     */
    private static boolean isEmpty(String text)
    {
        return text.trim().length() == 0;
    }

    /**
     * creates input window for student number, gets inputted number
     * @return user input student number
     */
    public static String getNumberFromDialog()
    {
        AtomicReference<String> studentNumber = new AtomicReference<>("");

        while (studentNumber.get().length() == 0)
        {
            TextInputDialog dialog = new TextInputDialog("123456");
            Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            TextField inputField = dialog.getEditor();
            BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> isEmpty(inputField.getText()), inputField.textProperty());
            okButton.disableProperty().bind(isInvalid);

            dialog.setTitle("Opiskelijanumero");
            dialog.setHeaderText("Opiskelijanumero");
            dialog.setContentText("Syötä opiskelijanumero:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(studentNumber::set);
        }

        System.out.println("Student number: " + studentNumber);
        return studentNumber.get().trim();
    }
}
