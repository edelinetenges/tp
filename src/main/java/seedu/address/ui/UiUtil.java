package seedu.address.ui;

import javafx.scene.control.Label;
import seedu.address.model.tag.ChildTag;
import seedu.address.model.tag.Tag;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Contains utility methods used for generating UI elements.
 */
public class UiUtil {
    /**
     * Generates a label of different colors based on whether the tag is a ChildTag.
     * @param tag the tag to create a label from.
     * @return Label with the tagName as text and different colors based on the tag type.
     */
    public static Label generateTagLabel(Tag tag) {
        Label label = new Label(tag.tagName);
        if (tag instanceof ChildTag) {
            label.setStyle("-fx-background-color: #ff5050");
        }
        return label;
    }

    public static Stream<Tag> streamTags(Set<Tag> tagSet) {
        return tagSet.stream()
                .sorted(new TagComparator());
    }

    public static class TagComparator implements Comparator<Tag> {

        @Override
        public int compare(Tag tag1, Tag tag2) {
            if (tag1 instanceof ChildTag) {
                if (tag2 instanceof ChildTag) {
                    return String.CASE_INSENSITIVE_ORDER.compare(tag1.tagName, tag2.tagName);
                } else {
                    return -1;
                }
            } else {
                if (tag2 instanceof ChildTag) {
                    return 1;
                } else {
                    return String.CASE_INSENSITIVE_ORDER.compare(tag1.tagName, tag2.tagName);
                }
            }
        }
    }
}
