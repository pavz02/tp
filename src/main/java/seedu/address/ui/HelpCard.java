package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class HelpCard extends UiPart<Region> {

    private static final String FXML = "HelpListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox helpPane;
    @FXML
    private Label commandDescription;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public HelpCard(String command) {
        super(FXML);
        commandDescription.setText(command);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HelpCard)) {
            return false;
        }

        // state check -> do I need this?
        HelpCard card = (HelpCard) other;
        return commandDescription.getText().equals(card.commandDescription.getText());
    }
}
