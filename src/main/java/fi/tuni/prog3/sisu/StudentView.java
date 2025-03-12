package fi.tuni.prog3.sisu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Visual element for the student data tab
 */
public class StudentView extends VBox {
    private final TextField nameField;
    private final TextField addressField;
    private final TextField emailField;
    private final TextField phoneField;
    private final User user;

    private final ComboBox<StudyPlan> planComboBox;

    /**
     * Save all the edited data
     */
    private void saveData()
    {
        user.setName(nameField.getText());
        user.setAddress(addressField.getText());
        user.setEmail(emailField.getText());
        user.setPhonenumber(phoneField.getText());
        UserData.saveUserData(user);
        StatusDisplayer.setStatus("Käyttäjän " + user.getStudentNumber() + " tiedot tallennettu");
    }

    /**
     * Populates the fields with correct data
     */
    private void loadData()
    {
        nameField.setText(user.getName());
        addressField.setText(user.getAddress());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhonenumber());
    }

    /**
     * Opens a dialog for creating a new plan
     */
    private void newPlan()  {
        ProgrammeSelectorDialog.newPlan(user);
        UserData.saveUserData(user);
        fillPlanComboBox(user.getSavedStudyPlans());
    }

    /**
     * Opens a dialog for editing a new plan
     */
    private void editPlan() {
        ProgrammeSelectorDialog.editPlan(user, user.primaryStudyPlan);
        UserData.saveUserData(user);
        fillPlanComboBox(user.getSavedStudyPlans());
    }

    /**
     * Removes the active study plan
     */
    private void removePlan()
    {
        StudyPlan plan = planComboBox.getSelectionModel().getSelectedItem();
        if (plan != null)
        {
            user.setPrimaryStudyPlan(null);
            planComboBox.getSelectionModel().select(null);
            user.getSavedStudyPlans().remove(plan);
            UserData.saveUserData(user);
            fillPlanComboBox(user.getSavedStudyPlans());
            StatusDisplayer.setStatus("Opintosuunnitelma poistettu.");
        }
    }

    /**
     * Makes the study plan combo-box items appear and behave in the desired manner
     */
    private void initializeStudyPlanCellBehavior()
    {
        planComboBox.setCellFactory(new Callback<ListView<StudyPlan>, ListCell<StudyPlan>>() {
            @Override
            public ListCell<StudyPlan> call(ListView<StudyPlan> studyPlanListView) {
                return new ListCell<StudyPlan>(){
                    @Override protected void updateItem(StudyPlan plan, boolean empty)
                    {
                        super.updateItem(plan, empty);

                        if (plan != null && !empty)
                        {
                            setText(plan.getName());
                        }
                    }
                };
            }
        });

        planComboBox.setConverter(new StringConverter<StudyPlan>() {
            @Override
            public String toString(StudyPlan plan) {
                return plan != null ? plan.getName() : null;
            }

            @Override
            public StudyPlan fromString(String s) {
                return planComboBox.getItems().stream().filter(plan -> plan.getName().equals(s)).findFirst().orElse(null);
            }
        });

        planComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldPlan, newPlan) ->
        {
            if (newPlan != null)
            {
                user.setPrimaryStudyPlan(newPlan);
                StatusDisplayer.setStatus("Opintosuunnitelmaksi valittu " + newPlan.getName());
                UserData.saveUserData(user);
            }
        });
    }


    /**
     * Fills the study plan combo-box with user's saved study plans
     * @param plans list of user's study plans
     */
    private void fillPlanComboBox(List<StudyPlan> plans)
    {
        planComboBox.getItems().clear();
        for (StudyPlan plan: plans) {
            planComboBox.getItems().add(plan);
        }
        if (user.getPrimaryStudyPlan() != null)
        {
            Optional<StudyPlan> item = planComboBox.getItems().stream().filter(plan -> Objects.equals(plan.getName(), user.getPrimaryStudyPlan().getName())).findFirst();
            item.ifPresent(plan -> planComboBox.getSelectionModel().select(plan));
        }
    }

    /**
     * The constructor for the visual element for the student data tab
     * @param student The student whose data to display
     */
    public StudentView(User student) {
        user = student;
        // Initialize the text fields before populating them with text
        nameField = new TextField();
        addressField = new TextField();
        emailField = new TextField();
        phoneField = new TextField();
        planComboBox = new ComboBox<>();

        // Load user data for the text fields
        loadData();

        // Make the tab more aesthetically pleasing
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        HeaderLabel header = new HeaderLabel("Opiskelijan tiedot");

        // Section with text fields regarding student's information
        VBox formSection = new VBox();
        Label nameLabel = new Label("Nimi:");

        Label addressLabel = new Label("Osoite:");
        Label emailLabel = new Label("Sähköposti:");
        Label phoneLabel = new Label("Puhelinnumero:");

        Button buttonSave = new Button("Tallenna");

        // Make the save button save the user data
        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * Determines what happens when the save button is pressed.
             * @param actionEvent button press event.
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                saveData();
            }
        });

        formSection.getChildren().addAll(nameLabel, nameField, addressLabel, addressField, emailLabel, emailField, phoneLabel, phoneField, buttonSave);

        Separator separator = new Separator();

        // Programme selection/creation section
        HBox programmeSection = new HBox();
        programmeSection.setSpacing(10);

        // Select a plan
        VBox selectProgrammeSection = new VBox();
        Label selectProgrammeLabel = new Label("Valitse opintosuunnitelma:");
        List<StudyPlan> plans = user.getSavedStudyPlans();
        initializeStudyPlanCellBehavior();
        fillPlanComboBox(plans);

        selectProgrammeSection.getChildren().addAll(selectProgrammeLabel, planComboBox);

        // Create a plan
        VBox newProgrammeSection = new VBox(10);
        Button newProgrammeButton = new Button("Uusi opintosuunnitelma");

        Button removeProgrammeButton = new Button("Poista opintosuunnitelma");

        Button editProgrammeButton = new Button("Muokkaa opintosuunnitelmaa");

        newProgrammeButton.setOnAction(new EventHandler<ActionEvent>(){
            /**
             * Determines what happens when the button has been pressed
             * @param actionEvent button press event
             */
            @Override
            public void handle(ActionEvent actionEvent){newPlan(); }
        } );

        removeProgrammeButton.disableProperty().bind(planComboBox.getSelectionModel().selectedItemProperty().isNull());
        editProgrammeButton.disableProperty().bind(planComboBox.getSelectionModel().selectedItemProperty().isNull());

        removeProgrammeButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * Determines what happens when the button has been pressed
             * @param actionEvent button press event
             */
            @Override
            public void handle(ActionEvent actionEvent) {removePlan(); }
        });

        editProgrammeButton.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * Determines what happens when the button has been pressed
             * @param actionEvent button press event
             */
            @Override
            public void handle(ActionEvent actionEvent) {editPlan();}
        });

        newProgrammeSection.getChildren().addAll(newProgrammeButton, removeProgrammeButton, editProgrammeButton);
        programmeSection.getChildren().addAll(selectProgrammeSection, newProgrammeSection);

        this.getChildren().addAll(header, formSection, separator, programmeSection);
    }
}
