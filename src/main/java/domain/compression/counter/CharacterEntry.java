package domain.compression.counter;

import java.util.Comparator;

/**
 * Created by ensar on 13/02/17.
 */
public class CharacterEntry {

    private Character character;
    private Long frequency;

    public CharacterEntry(Character character, Long frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    public static final Comparator<CharacterEntry> COMPARATOR_FREQUENCY_DESCENDING = new Comparator<CharacterEntry>() {
        public int compare(CharacterEntry o1, CharacterEntry o2) {
            return o1.getFrequency().compareTo(o2.getFrequency());
        }
    };
}
