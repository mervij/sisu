package fi.tuni.prog3.sisu;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * A dialog for creating a study plan and selecting a programme
 */
public class ProgrammeSelectorDialog {

    /**
     * Checks id the name for study plan is valid or not.
     * @param name the proposed name of the plan.
     * @param user the user to create a plan for.
     * @param allowedName a name that you can allow the user to have while editing a plan.
     * @return false if the field is empty or the name has been taken, true otherwise.
     */
    private static boolean isNameValid(String name, User user, String allowedName)
    {
        String lowerName = name.trim().toLowerCase(Locale.ROOT).trim();
        if (name.trim().length() == 0)
        {
            return false;
        }

        // While editing a study plan you want to allow the previous name of the plan.
        if (lowerName.equals(allowedName.toLowerCase()))
        {
            return true;
        }

        for (StudyPlan plan : user.getSavedStudyPlans()) {
            if (plan.getName().toLowerCase(Locale.ROOT).trim().equals(lowerName))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Opens a dialog that allows the user to create/edit a new study plan.
     * @param user The user to make/edit a study plan for.
     * @param editedPlan The study plan to be edited. If null, a new plan will be created.
     */
    private static void createPlanDialog(User user, StudyPlan editedPlan) {
        boolean editingPlan = editedPlan != null;

        Dialog<Pair<Programme, String>> dialog = new Dialog<>();
        if (editingPlan)
        {
            dialog.setTitle("Opintosuunnitelman muokkaus");
        }
        else
        {
            dialog.setTitle("Opintosuunnitelman luonti");
        }
        dialog.setHeaderText("Valitse tutkinto-ohjelma ja nimeä opintosuunnitelma.");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setMinWidth(512);

        VBox contentBox = new VBox(10);

        ProgrammeSelector selector = new ProgrammeSelector();


        Label nameLabel = new Label("Opintosuunnitelman nimi:");
        TextField nameField = new TextField();
        if (editingPlan)
        {
            nameField.setText(editedPlan.getName());
        }

        contentBox.getChildren().addAll(selector, nameLabel, nameField);

        dialog.getDialogPane().setContent(contentBox);

        String allowedName = editingPlan ? editedPlan.getName() : "";

        BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> !isNameValid(nameField.getText(), user, allowedName), nameField.textProperty());
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.disableProperty().bind(
                Bindings.or(
                        Bindings.or(
                                selector.getListView().getSelectionModel().selectionModeProperty().isNull(),
                                Bindings.isEmpty(selector.getListView().getSelectionModel().getSelectedItems())),
                        isInvalid));

        if (editingPlan)
        {
            Programme currentProgramme = ProgrammeData.readBasicInformation(editedPlan.getProgrammeId());;
            Optional<Programme> item = selector.getListView().getItems().stream().filter(programme -> Objects.equals(programme.getId(), currentProgramme.getId())).findFirst();
            item.ifPresent(programme -> selector.getListView().getSelectionModel().select(programme));
        }

        dialog.setResultConverter(dialogButton ->
        {
            if (dialogButton == ButtonType.OK)
            {
                return new Pair<>(selector.getSelectedProgramme(), nameField.getText());
            }
            return null;
        });

        Optional<Pair<Programme, String>> result = dialog.showAndWait();

        result.ifPresent(programmePlan ->
        {
            Programme programme = programmePlan.getKey();
            String name = programmePlan.getValue().trim();
            if (programme != null && isNameValid(name, user, allowedName))
            {
                if (editingPlan)
                {
                    editedPlan.setName(name);
                    editedPlan.setProgrammeId(programme.getId());
                }
                else
                {
                    StudyPlan newPlan = new StudyPlan(name, programme.getId());
                    user.addStudyPlan(newPlan);
                    user.setPrimaryStudyPlan(newPlan);
                }
            }
        });
    }

    /**
     * Opens a dialog that allows the user to create a new study plan.
     * @param user The user to make a study plan for.
     */
    public static void newPlan(User user) {
        createPlanDialog(user, null);
    }

    /**
     * Opens a dialog that allows the user to edit a study plan
     * @param user The user whose study plan is to being edited.
     * @param plan The study plan to edit.
     */
    public static void editPlan(User user, StudyPlan plan) {
        createPlanDialog(user, plan);
    }
}
