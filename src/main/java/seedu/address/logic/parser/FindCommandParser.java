package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.OPTION_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.OPTION_EMAIL;
import static seedu.address.logic.parser.CliSyntax.OPTION_NAME;
import static seedu.address.logic.parser.CliSyntax.OPTION_PHONE;
import static seedu.address.logic.parser.CliSyntax.OPTION_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.AnyContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagsMatchKeywordPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_OPTION);
        Optional<String> argsString = argMultimap.getValue(PREFIX_OPTION);
        if (argsString.isPresent()) {
            // split args into option and remaining optionArgs
            String unboxedArgsString = argsString.get();
            String[] optionArgsArray = unboxedArgsString.split("\\s+", 2);
            String option = optionArgsArray[0];
            String optionArgs = optionArgsArray[1];
            return parseFindOptions(option, optionArgs);
        } else { // find by all fields
            return parseFindAll(trimmedArgs);
        }
    }

    /**
     * Parses args in find by options context
     * @param option option to determine the option selected
     * @param optionArgs {@code optionArgs} for the rest of the args
     * @return {@code FindCommand}
     */
    public FindCommand parseFindOptions(String option, String optionArgs) throws ParseException {
        // get keywords
        List<String> keywords = Arrays.asList(optionArgs.split("\\s+"));
        if (option.equals(OPTION_NAME)) { // find by name
            return new FindCommand(new NameContainsKeywordsPredicate(keywords));
        } else if (option.equals(OPTION_ADDRESS)) { // find by address
            return new FindCommand(new AddressContainsKeywordsPredicate(keywords));
        } else if (option.equals(OPTION_PHONE)) { // find by phone
            return new FindCommand(new PhoneContainsKeywordsPredicate(keywords));
        } else if (option.equals(OPTION_EMAIL)) { // find by email
            return new FindCommand(new EmailContainsKeywordsPredicate(keywords));
        } else if (option.equals(OPTION_TAG)) { // find by tag
            // get tags
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(optionArgs, PREFIX_OPTION);
            Set<Tag> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            return new FindCommand(new TagsMatchKeywordPredicate(tagSet));
        }  else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses args in find by all context
     * @param trimmedArgs
     * @return
     */
    public FindCommand parseFindAll(String trimmedArgs) {
        String[] keywords = trimmedArgs.split("\\s+");
        assert keywords.length > 0 : "FindCommand keywords are empty";
        return new FindCommand(new AnyContainsKeywordsPredicate(Arrays.asList(keywords)));
    }


}
