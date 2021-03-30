package seedu.cakecollate.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.cakecollate.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.cakecollate.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.cakecollate.testutil.TestUtil.stringify;
import static seedu.cakecollate.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.cakecollate.testutil.TypicalIndexes.INDEX_THIRD_ORDER;
import static seedu.cakecollate.testutil.TypicalOrderItems.getTypicalOrderItemsModel;
import static seedu.cakecollate.testutil.TypicalOrders.getTypicalCakeCollate;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.cakecollate.commons.core.Messages;
import seedu.cakecollate.commons.core.index.IndexList;
import seedu.cakecollate.logic.commands.exceptions.CommandException;
import seedu.cakecollate.model.Model;
import seedu.cakecollate.model.ModelManager;
import seedu.cakecollate.model.OrderItems;
import seedu.cakecollate.model.UserPrefs;
import seedu.cakecollate.model.order.Order;
import seedu.cakecollate.model.orderitem.OrderItem;
import seedu.cakecollate.testutil.AddOrderDescriptorBuilder;
import seedu.cakecollate.testutil.OrderBuilder;
import seedu.cakecollate.testutil.OrderItemBuilder;
import seedu.cakecollate.testutil.TypicalOrderItems;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private IndexList nonNullIndexList;
    // todo should i pass in empty index list instead of null? is there a good practice

    @BeforeEach
    public void setUp() {
        nonNullIndexList = new IndexList(new ArrayList<>());
        nonNullIndexList.add(INDEX_FIRST_ORDER);
        nonNullIndexList.add(INDEX_THIRD_ORDER);
        model = new ModelManager(getTypicalCakeCollate(), new UserPrefs(),
                TypicalOrderItems.getTypicalOrderItemsModel());
    }

    @Test
    public void execute_newOrder_success() {
        Order validOrder = new OrderBuilder().build();
        AddCommand.AddOrderDescriptor descriptor = new AddOrderDescriptorBuilder(validOrder).build();

        Model expectedModel = new ModelManager(model.getCakeCollate(), new UserPrefs(), model.getOrderItems());
        expectedModel.addOrder(validOrder);

        assertCommandSuccess(new AddCommand(null, descriptor), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validOrder), expectedModel);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() {
        Order orderInList = model.getCakeCollate().getOrderList().get(0);
        AddCommand.AddOrderDescriptor descriptor = new AddOrderDescriptorBuilder(orderInList).build();
        assertCommandFailure(new AddCommand(null, descriptor), model, AddCommand.MESSAGE_DUPLICATE_ORDER);
    }
    // todo index list related

    // todo with order index

    // todo with both



    // ======== TESTS ADDED AFTER ORDER ITEM FEATURE ========


    // === INDEX LIST RELATED ===

    @Test
    public void invalidListIndex_uhh() throws CommandException {
        Model emptyModel = new ModelManager();
        AddCommand.AddOrderDescriptor descriptor = new AddOrderDescriptorBuilder(new OrderBuilder().withOrderDescriptions().build()).build();

        Command addCommand = new AddCommand(nonNullIndexList, descriptor);

        assertCommandFailure(addCommand, emptyModel, Messages.MESSAGE_INVALID_ORDER_ITEM_INDEX);
    }

    @Test
    public void execute_singleValidIndexList_addedToOrder() {

        IndexList indexList = new IndexList(new ArrayList<>());
        indexList.add(INDEX_FIRST_ORDER);

        OrderItems orderItems = getTypicalOrderItemsModel(); // model.getOrderItems();
        OrderItem toAdd = orderItems.getOrderItemList().get(0);

        Order validOrder = new OrderBuilder().withOrderDescriptions().build();
        AddCommand.AddOrderDescriptor descriptor = new AddOrderDescriptorBuilder(validOrder).build();

        // add order item 0 to expected order
        // the naming makes order items and order descriptions look really unrelated but they aren't
        Order finalOrderToAddToModel = new OrderBuilder(validOrder).withOrderDescriptions(stringify(toAdd)).build();

        // construct expected model
        Model expectedModel = new ModelManager(model.getCakeCollate(), new UserPrefs(), model.getOrderItems()); // todo change all/most 2nd arg
        expectedModel.addOrder(finalOrderToAddToModel);

        assertCommandSuccess(new AddCommand(indexList, descriptor), model,
                String.format(AddCommand.MESSAGE_SUCCESS, finalOrderToAddToModel), expectedModel);
    }

    @Test
    public void execute_multipleIndexList_AllAddedToOrder() {

    }






    // === ===


    @Test
    public void execute_inputExistingOrderDesc_doesNotAddToModelAgain() {
        String value = "cake that already exists in model";
        Order order = new OrderBuilder().withOrderDescriptions(value).build(); // cry names don't make sense
        AddCommand.AddOrderDescriptor descriptor = new AddOrderDescriptorBuilder(order).build();

        OrderItem existingOrderItem = new OrderItemBuilder().withType(value).build();
        // does this stuff go into dg, e.g. how to test - use builders

        model.hasOrderItem(existingOrderItem);

        // int initialSize = model.getOrderItemLists.size();

        Command addCommand = new AddCommand(null, descriptor);

        Model expectedModel = new ModelManager(model.getCakeCollate(), new UserPrefs(), model.getOrderItems());

        expectedModel.addOrder(order);
        expectedModel.addOrderItem(existingOrderItem);
        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, order);

        assertCommandSuccess(addCommand, model, expectedMessage, expectedModel);
        // assertTrue()
        // assertEquals size has remained the same

        // as long as model doesn't accept duplicate items, it should be okay
        // todo don't duplicate stuff in the model that's same content, but in a different case
    }

    @Test
    public void execute_inputNewOrderDescription_addedToOrderItemModel() {
        String value = "cake that does not exist in model yet";
        Order order = new OrderBuilder().withOrderDescriptions(value).build(); // cry names don't make sense
        AddCommand.AddOrderDescriptor descriptor = new AddOrderDescriptorBuilder(order).build();

        OrderItem newOrderItemToAdd = new OrderItemBuilder().withType(value).build();
        // does this stuff go into dg, e.g. how to test - use builders

        assert !model.hasOrderItem(newOrderItemToAdd)
                : "corresponding order description shouldn't be in the order item model";

        // int initialSize = model.getOrderItemLists.size();

        Command addCommand = new AddCommand(null, descriptor);

        Model expectedModel = new ModelManager(model.getCakeCollate(), new UserPrefs(), model.getOrderItems());

        expectedModel.addOrder(order);
        expectedModel.addOrderItem(newOrderItemToAdd);
        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, order);

        assertCommandSuccess(addCommand, model, expectedMessage, expectedModel);
        assertTrue(model.hasOrderItem(newOrderItemToAdd));
        // assertTrue()
        // assertEquals size has increased by one
    }


    @Test
    public void execute_bothOrderDescAndIndexList_AllOrder() {



    }
}
