package seedu.cakecollate.logic.parser;

import static seedu.cakecollate.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.cakecollate.commons.core.Messages.MESSAGE_NEGATIVE_INDEX;
import static seedu.cakecollate.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.cakecollate.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.cakecollate.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_AMY;
import static seedu.cakecollate.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_BOB;
import static seedu.cakecollate.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.cakecollate.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.cakecollate.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.cakecollate.logic.commands.CommandTestUtil.INVALID_DELIVERY_DATE_DESC2;
import static seedu.cakecollate.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.cakecollate.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.cakecollate.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.cakecollate.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.cakecollate.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.cakecollate.logic.commands.CommandTestUtil.ORDER_DESC_AMY;
import static seedu.cakecollate.logic.commands.CommandTestUtil.ORDER_DESC_BOB;
import static seedu.cakecollate.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.cakecollate.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.cakecollate.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.cakecollate.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.cakecollate.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.cakecollate.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.cakecollate.logic.commands.CommandTestUtil.VALID_BERRY_ORDER;
import static seedu.cakecollate.logic.commands.CommandTestUtil.VALID_CHOCOLATE_ORDER;
import static seedu.cakecollate.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_AMY;
import static seedu.cakecollate.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_BOB;
import static seedu.cakecollate.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.cakecollate.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.cakecollate.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.cakecollate.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.cakecollate.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.cakecollate.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.cakecollate.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.cakecollate.logic.parser.CliSyntax.PREFIX_ORDER_DESCRIPTION;
import static seedu.cakecollate.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.cakecollate.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.cakecollate.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.cakecollate.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.cakecollate.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.cakecollate.testutil.TypicalIndexes.INDEX_THIRD_ORDER;

import org.junit.jupiter.api.Test;

import seedu.cakecollate.commons.core.index.Index;
import seedu.cakecollate.logic.commands.EditCommand;
import seedu.cakecollate.logic.commands.EditCommand.EditOrderDescriptor;
import seedu.cakecollate.model.order.Address;
import seedu.cakecollate.model.order.DeliveryDate;
import seedu.cakecollate.model.order.Email;
import seedu.cakecollate.model.order.Name;
import seedu.cakecollate.model.order.OrderDescription;
import seedu.cakecollate.model.order.Phone;
import seedu.cakecollate.model.tag.Tag;
import seedu.cakecollate.testutil.EditOrderDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;
    private static final String ORDER_DESC_EMPTY = " " + PREFIX_ORDER_DESCRIPTION;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_NEGATIVE_INDEX);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_EMPTY); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag
        assertParseFailure(parser, "1" + INVALID_DELIVERY_DATE_DESC2,
                String.format(DeliveryDate.MESSAGE_CONSTRAINTS_VALUE, "01/01/2000")); // invalid delivery date

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Order} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // order description
        assertParseFailure(parser, "1" + ORDER_DESC_AMY + ORDER_DESC_BOB + ORDER_DESC_EMPTY,
                OrderDescription.MESSAGE_EMPTY);
        assertParseFailure(parser, "1" + ORDER_DESC_AMY + ORDER_DESC_EMPTY + ORDER_DESC_BOB,
                OrderDescription.MESSAGE_EMPTY);
        assertParseFailure(parser, "1" + ORDER_DESC_EMPTY + ORDER_DESC_AMY + ORDER_DESC_BOB,
                OrderDescription.MESSAGE_EMPTY);
        assertParseFailure(parser, "1" + ORDER_DESC_EMPTY, OrderDescription.MESSAGE_EMPTY);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ORDER;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY
                + ORDER_DESC_AMY + ORDER_DESC_BOB + DELIVERY_DATE_DESC_AMY
                + TAG_DESC_FRIEND;

        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withOrderDescriptions(VALID_CHOCOLATE_ORDER, VALID_BERRY_ORDER)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withDeliveryDate(VALID_DELIVERY_DATE_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_ORDER;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditOrderDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditOrderDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // cakecollate
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditOrderDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // order description
        userInput = targetIndex.getOneBased() + ORDER_DESC_AMY;
        descriptor = new EditOrderDescriptorBuilder().withOrderDescriptions(VALID_CHOCOLATE_ORDER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditOrderDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // delivery date
        userInput = targetIndex.getOneBased() + DELIVERY_DATE_DESC_AMY;
        descriptor = new EditOrderDescriptorBuilder().withDeliveryDate(VALID_DELIVERY_DATE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + DELIVERY_DATE_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + DELIVERY_DATE_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB
                + DELIVERY_DATE_DESC_BOB + TAG_DESC_HUSBAND;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .withDeliveryDate(VALID_DELIVERY_DATE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditCommand.EditOrderDescriptor descriptor =
                new EditOrderDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditOrderDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_ORDER;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_duplicateOrderDescriptions_allAdded() {
        Index targetIndex = INDEX_THIRD_ORDER;
        String userInput = targetIndex.getOneBased() + ORDER_DESC_AMY + ORDER_DESC_AMY;

        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderDescriptions(VALID_CHOCOLATE_ORDER, VALID_CHOCOLATE_ORDER).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
