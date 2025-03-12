package fi.tuni.prog3.sisu;

import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Creates CheckBox list (tree) to pick which courses are completed
 *
 * Done'dTODO completed courses disabled/something like that
 * Courses that are picked won't be shown next time module is opened
 * Completed courses wont be shown in checkboxlist
 */
public class CourseSelector {
    /**
     * creates course checkbox list
     * @param a module who's courses will be printed
     * @return course checkbox treeview
     */
    public static TreeView buildCourseSelector(Module a, User student) {
        CheckBoxTreeItem<StudyElement<?>> rootItem = new CheckBoxTreeItem<>(a);
        TreeView courseTree = new TreeView<>(rootItem);
        courseTree.setEditable(true);
        courseTree.setCellFactory(CheckBoxTreeCell.<Course>forTreeView());

        for (Course course : a.getCourses()) {
            if (!student.isCourseCompleted(course.getId())) {
                CheckBoxTreeItem chk = new CheckBoxTreeItem<>(course);
                rootItem.getChildren().add(chk);
            }
        }

        courseTree.setRoot(rootItem);
        rootItem.setExpanded(true);
        courseTree.setShowRoot(false);

        courseTree.setCellFactory(cell -> new CheckBoxTreeCell<Course>() {
            /**
             * Basic event to add the checkbox cell with text and tooltip
             * @param crs course object to be made into checkbox list element
             * @param empty basic javafx event thingy
             */
            private void updateCheckBoxItem(Course crs, boolean empty) {
                super.updateItem(crs, empty);

                if (crs == null || empty) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(crs.toString());

                    WebView web = new WebView();
                    WebEngine webEngine = web.getEngine();
                    webEngine.loadContent(crs.getLearningOutcomes());

                    Tooltip  tip = new Tooltip();
                    tip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    tip.setGraphic(web);

                    tip.setPrefWidth(300);
                    tip.setWrapText(true);
                    setTooltip(tip);
                }
            }
        });

        return courseTree;
    }
}
