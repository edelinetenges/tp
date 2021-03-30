package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.OPTION_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.FindAppointmentCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.ApptNameContainsKeywordsPredicate;

public class FindAppointmentCommandParser implements Parser<FindAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code FindAppointmentCommand}
     * and returns a FindAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_OPTION);
        Optional<String> argsString = argMultimap.getValue(PREFIX_OPTION);
        if (argsString.isPresent()) { // find with options

            // split args into option and remaining optionArgs
            String unboxedArgsString = argsString.get();
            String[] optionArgsArray = unboxedArgsString.split("\\s+", 2);
            String option = optionArgsArray[0];
            String optionArgs = optionArgsArray[1];

            return parseFindOptions(new Option(option), optionArgs);
        } else { // find by all fields
            return parseFindAll(trimmedArgs);
        }
    }

    /**
     * Parses other fields in find by options context
     * @param option {@code Option} to determine the option selected
     * @param optionArgs {@code optionArgs} for the rest of the args
     * @return {@code FindCommand}
     */
    public FindAppointmentCommand parseFindOptions(Option option, String optionArgs) throws ParseException {
        if (option.equals(OPTION_NAME)) { // find by name
            List<String> names;

            if (optionArgs.contains(PREFIX_NAME.getPrefix())) {
                ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(optionArgs, PREFIX_NAME);
                names = argMultimap.getAllValues(PREFIX_NAME);
            } else {
                names = Arrays.asList(optionArgs.split("\\s+"));
            }

            return new FindAppointmentCommand(new ApptNameContainsKeywordsPredicate(names));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses others in find by all context
     * @param trimmedArgs
     * @return
     */
    public FindAppointmentCommand parseFindAll(String trimmedArgs) throws ParseException {
        String[] nameKeywords = trimmedArgs.split("\\s+");
        assert nameKeywords.length > 0 : "FindCommand keywords are empty";
        return new FindAppointmentCommand(new ApptNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}