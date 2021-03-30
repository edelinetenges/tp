package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.OPTION_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "the Option " + PREFIX_OPTION + OPTION_TAG + " can be used to find all persons that match at least"
            + " one of the tags.\n"
            + "Parameters: [" + PREFIX_OPTION + "OPTION]"
            + " KEYWORD [MORE_KEYWORDS]...\n"
            + "Option: " + OPTION_TAG + " (to find by tags)\n"
            + "Examples:\n"
            + COMMAND_WORD + " alice bob charlie\n"
            + COMMAND_WORD + " " + PREFIX_OPTION + OPTION_TAG + " " + PREFIX_TAG + "math "
            + PREFIX_TAG + "science " + PREFIX_TAG + "primary";

    private final Predicate<Person> predicate;

    /**
     * @param predicate Predicate to find the {@code Person} by name.
     */
    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
