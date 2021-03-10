package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the list of persons.
 */
public class HelpCommandPanel extends UiPart<Region> {
    private static final String FXML = "HelpListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(HelpCommandPanel.class);
    private MainWindow mw;

    @FXML
    private ListView<String> helpListView;
    @FXML
    private Button button;


    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public HelpCommandPanel(ObservableList<String> commandDescriptionList, MainWindow mw) {
        super(FXML);
        this.mw = mw;
        helpListView.setItems(commandDescriptionList);
        helpListView.setCellFactory(listView -> new HelpCommandViewCell());
    }

    public Button getButton() {
        return button;
    }

    @FXML
    public void buttonAction(Event event) {
        mw.resetMainWindow();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class HelpCommandViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String command, boolean empty) {
            super.updateItem(command, empty);

            if (empty || command == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HelpCard(command).getRoot());
            }
        }
    }

}
