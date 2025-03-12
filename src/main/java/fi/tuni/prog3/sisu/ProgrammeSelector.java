package fi.tuni.prog3.sisu;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Prints programmes, and offers ways to pick it
 */
public class ProgrammeSelector extends VBox {

    private final ListView<Programme> listView;
    private Programme selectedPrg;

    /**
     * Inizializes the drawable listview
     */
    public ProgrammeSelector() {
        listView = new ListView<>();
        makeElement();
    }

    /**
     * saves the clicked programme
     * @param prg saves the clicked programme
     */
    private void setProgramme(Programme prg) {
        this.selectedPrg = prg;
    }

    /**
     * clicked program from view
     * @return clicked program from view
     */
    public Programme getSelectedProgramme() { return selectedPrg; }

    /**
     * Draws all Programmes, Sets clicked Programme as selected
     */

    public void makeElement() {
        List<Programme> programmes = UniversityData.getProgrammes();

        assert programmes != null;
        listView.getItems().addAll(programmes);

        listView.setCellFactory(cell -> new ListCell<>() {

            @Override
            protected void updateItem(Programme prg, boolean empty) {
                super.updateItem(prg, empty);

                if (prg == null || empty) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(prg.toString());

                    WebView web = new WebView();
                    WebEngine webEngine = web.getEngine();
                    webEngine.loadContent(prg.getLearningOutcomes());

                    Tooltip  tip = new Tooltip();
                    tip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    tip.setGraphic(web);

                    tip.setPrefWidth(300);
                    tip.setWrapText(true);
                    setTooltip(tip);
                }
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Programme>() {
            /**
             * Fires when the programme in the list has been changed.
             * @param observableValue the ObservableValue which value changed.
             * @param programme the previous programme.
             * @param t1 the selected programme.
             */
            @Override
            public void changed(ObservableValue<? extends Programme> observableValue, Programme programme, Programme t1) {
                System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
                // :D ehk tää toimiii
                setProgramme(programmes.stream().filter(a -> Objects.equals(a.getId(), listView.getSelectionModel().getSelectedItem().getId())).collect(Collectors.toList()).get(0));
            }
        });

        this.getChildren().add(listView);
    }

    /**
     * Returns the list view element used in the selector.
     * @return the listview element used in the selector.
     */
    public ListView<Programme> getListView() {return listView; }
}