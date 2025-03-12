package fi.tuni.prog3.sisu;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * An interface for viewing study progress with data from Sisu system.
 */
public class Sisu extends Application {

    /**
     * Launches the start-up dialog and determines the structure and behavior that is constant in the program
     * @param stage The main javafx container.
     */
    @Override
    public void start(Stage stage) {

        // Open student number asking dialog
        String studentNumber = StudentDialog.getNumberFromDialog();
        User user = UserData.getUser(studentNumber);

        // Status text in the bottom of the page
        Label statusLabel = new Label();
        StatusDisplayer.setStatusLabel(statusLabel);

        // Tabs
        TabPane tabPane = new TabPane();

        Tab studentTab = new Tab();
        studentTab.setText("Opiskelijan tiedot");
        tabPane.getTabs().add(studentTab);
        studentTab.setContent(new StudentView(user));

        Tab studyTab = new Tab();
        studyTab.setText("Opintojen rakenne");
        tabPane.getTabs().add(studyTab);
        tabPane.setMaxHeight(480);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            /**
             * Determines what happens when the selection has changed.
             * @param observableValue the ObservableValue which value changed.
             * @param tab the previous tab.
             * @param t1 the opened tab.
             */
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab t1) {
                if (t1 == studyTab)
                {
                    studyTab.setContent(new ProgrammeView(user));
                }
            }
        });


        // Status info area
        VBox statusBox = new VBox();
        Separator statusSeparator = new Separator();
        statusLabel.setPadding(new Insets(5));
        StatusDisplayer.setStatus("Sivu ladattu");
        statusBox.getChildren().addAll(statusSeparator, statusLabel);

        BorderPane root = new BorderPane();
        root.setTop(tabPane);
        root.setBottom(statusBox);

        var scene = new Scene(root, 640, 576);
        stage.setMinWidth(640);
        stage.setMinHeight(576);
        stage.setTitle("Sisu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main function of the program.
     * @param args start-up parameters, not used by the program.
     */
    public static void main(String[] args) {
        launch();
    }

}