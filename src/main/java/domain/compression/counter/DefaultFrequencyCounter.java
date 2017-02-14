package domain.compression.counter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ensar on 13/02/17.
 */
public class DefaultFrequencyCounter implements FrequencyCounter {

    private List<CharacterEntry> frequencyList;

    /**
     * @param frequencyList This map should be empty,
     *                  only used to decide on {@link List} implementation
     */
    public DefaultFrequencyCounter(List<CharacterEntry> frequencyList) {
        this.frequencyList = frequencyList;
    }

    public List<CharacterEntry> countCharacterFrequency(String inputText) {
        frequencyList.clear();
        Map<Character, CharacterEntry> map = new HashMap<Character, CharacterEntry>();
        for(Character c : inputText.toCharArray()) {
            if(map.containsKey(c)) {
                map.put(c, new CharacterEntry(c, map.get(c).getFrequency()+1));
            } else {
                map.put(c, new CharacterEntry(c, (long) 1));
            }
        }

        frequencyList.addAll(map.values());
        Collections.sort(frequencyList, CharacterEntry.COMPARATOR_FREQUENCY_DESCENDING);

        return frequencyList;
    }
}
