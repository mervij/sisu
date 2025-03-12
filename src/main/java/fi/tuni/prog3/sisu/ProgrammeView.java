package fi.tuni.prog3.sisu;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import static fi.tuni.prog3.sisu.CalculateCredits.calculateCredits;
import static fi.tuni.prog3.sisu.CourseSelector.buildCourseSelector;

/**
 * prints programme, with completed courses, and course picker
 */
public class ProgrammeView extends VBox{

    private final TreeView<StudyElement<?>> treeView;
    private TreeView<Course> courseView = null;
    private User user = null;
    private TreeItem<StudyElement<?>> openModule = null;

    /**
     * Inizializes Programme treeview
     * @param student The student whose programme to display.
     */
    public ProgrammeView(User student) {
        // Make the tab more aesthetically pleasing
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        HeaderLabel header = new HeaderLabel("Opiskelijan tiedot");
        this.getChildren().add(header);

        treeView = new TreeView<>();
        user = student;
        StudyPlan plan = user.getPrimaryStudyPlan();
        if (plan != null)
        {
            makeElement(ProgrammeData.readBasicInformation(plan.getProgrammeId()));
        }
        else
        {
            Label label = new Label("Valitse opintosuunnitelma selataksesi tuntkinto-ohjelmaa.");
            label.setPadding(new Insets(10));
            this.getChildren().add(label);
        }
    }

    /**
     * Prints Programme modules
     * @param mod Array of modules
     * @param root parent element
     * @return returns parent root with the child modules
     */
    private TreeItem<StudyElement<?>> printHelp(ArrayList<Module> mod, TreeItem<StudyElement<?>> root) {
        if (mod != null) {
            for (Module c : mod) {
                TreeItem<StudyElement<?>> d = new TreeItem<>(c);

                for (Course r : c.getCourses()) {
                    if (user.isCourseCompleted(r.getId())) {
                        TreeItem<StudyElement<?>> temp = new TreeItem<>(r);
                        d.getChildren().add(temp);
                    }
                }
                root.getChildren().add(printHelp(c.getSubmodules(), d));
            }
        }
        root.setExpanded(true);
        return root;
    }

    /**
     * updates view when course is completed
     */
    private void updateProgrammeCoursesAndCompleted()  {
        for(TreeItem<Course> child : courseView.getRoot().getChildren()) {
            CheckBoxTreeItem<Course> temp = (CheckBoxTreeItem<Course>) child;

            if (temp.isSelected()) {
                if (!user.isCourseCompleted(temp.getValue().getId())) {
                    user.addCompletedCourse(temp.getValue().getId());
                    StatusDisplayer.setStatus(String.format("Kurssi %s suoritettu", temp.getValue().toString()));

                    UserData.saveUserData(user);

                    Module tempModule = (Module) openModule.getValue();

                    TreeItem<StudyElement<?>> completedCourse = new TreeItem<>(tempModule.getCourses().stream().filter(a -> Objects.equals(a.getId(), temp.getValue().getId())).collect(Collectors.toList()).get(0));
                    openModule.getChildren().add(completedCourse);
                }
            }
        }
    }

    /**
     * Creates modules course picker
     * @param a MOdule who's courses will be printed as pickable
     */
    private void addCourseView(Module a) {
        courseView = buildCourseSelector(a, user);
        courseView.getRoot().addEventHandler(CheckBoxTreeItem.checkBoxSelectionChangedEvent(), new EventHandler<CheckBoxTreeItem.TreeModificationEvent<Object>>() {
            /**
             * Handles checkbox tick event
             * @param objectTreeModificationEvent event fiered
             */
            @Override
            public void handle(CheckBoxTreeItem.TreeModificationEvent<Object> objectTreeModificationEvent) {
                updateProgrammeCoursesAndCompleted();
            }
        });

        this.getChildren().add(courseView);
    }

    /**
     * removes the previous modules course picker
     */
    private void removePreviousCourseview() {
        if (courseView != null) {
            this.getChildren().remove(courseView);
        }
    }

    /**
     * adds event listeners to treeView, helps with code structure could be done in addCourseView
     */
    private void connectListeners() {
        treeView.setCellFactory(cell -> new TreeCell<>() {
            /**
             * Event to update and prints modules with tooltips
             *
             * @param prg   Prg element to be printed
             * @param empty basic java event thingy
             */
            protected void updateItem(StudyElement<?> prg, boolean empty) {
                super.updateItem(prg, empty);

                if (prg == null || empty) {
                    setText(null);
                    setTooltip(null);
                } else {
                    if (prg instanceof Module) {
                        setText(prg.toString(calculateCredits((Module) prg, user.getCompletedCourses())));
                    } else if(prg instanceof Course) {
                        setText(prg.toString(0));
                    }else {
                        setText(prg.toString());
                    }
                    WebView web = new WebView();
                    WebEngine webEngine = web.getEngine();
                    webEngine.loadContent(prg.getLearningOutcomes());

                    Tooltip tip = new Tooltip();
                    tip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    tip.setGraphic(web);

                    tip.setPrefWidth(300);
                    tip.setWrapText(true);
                    setTooltip(tip);
                }
            }
        });

        treeView.setOnMouseClicked(new EventHandler<>() {
            /**
             * Get programme pick event
             * @param event event mouse stuff
             */
            @Override
            public void handle(MouseEvent event) {
                if (treeView.getSelectionModel().getSelectedItem() != null && treeView.getSelectionModel().getSelectedItem().getValue() instanceof Module) {
                    Module temp = (Module) treeView.getSelectionModel().getSelectedItem().getValue();
                    openModule = treeView.getSelectionModel().getSelectedItem();
                    System.out.println(temp.getNameEN());
                    
                    removePreviousCourseview();
                    if (!temp.getCourses().isEmpty()) {
                        addCourseView(temp);
                    }

                }
            }
        });}

    /**
     * Draws the Programmes Module structure in TreeView
     * @param a Programme to be written
     */
    private void makeElement(Programme a) {
        TreeItem<StudyElement<?>> rootItem = new TreeItem<>(a);

        // for troubleshooting purposes
        //a = UniversityData.getProgrammes().get(8);

        TreeItem<StudyElement<?>> prg = new TreeItem<>(a);
        rootItem.getChildren().add(printHelp(a.getModules(), prg));

        treeView.setRoot(rootItem);
        rootItem.setExpanded(true);
        treeView.setShowRoot(false);

        connectListeners();

        this.getChildren().add(treeView);
    }
}