package domain.compression.counter;

import java.util.List;
import java.util.Map;

/**
 * Created by ensar on 13/02/17.
 */
public interface FrequencyCounter {
    List<CharacterEntry> countCharacterFrequency(String inputText);
}
