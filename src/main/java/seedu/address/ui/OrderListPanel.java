package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class OrderListPanel extends Panel {
    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);

    @FXML
    private ListView<Person> listView;

    /**
     * Creates a {@code OrderListPanel} with the given {@code ObservableList}.
     */

    public OrderListPanel(ObservableList<Person> orderList) {
        super();
        listView.setItems(orderList);
        listView.setCellFactory(lv -> new ListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code OrderCard}.
     */
    static class ListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);
            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OrderCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
