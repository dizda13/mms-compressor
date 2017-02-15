package domain.compression.shannonfano.codegenerator;

import domain.compression.counter.CharacterEntry;

import java.util.List;
import java.util.Map;

/**
 * Created by ensar on 14/02/17.
 */
public class SequentialShannonFanoCodeGenerator implements CodeGenerator {

    public Map<Character, String> generateCodes(List<CharacterEntry> characterEntryList, Map<Character, String> codeMap) {
        return recursiveListSplit(characterEntryList, codeMap);
    }

    private Map<Character, String> recursiveListSplit(List<CharacterEntry> characterFrequencies, Map<Character, String> codeMap) {
        Long sum = getTotalCount(characterFrequencies);

        Long leftSum = (long) 0;
        Long diff = (long) -1;
        int position = 0;
        for(CharacterEntry characterEntry : characterFrequencies) {
            leftSum += characterEntry.getFrequency();
            if(Math.abs(sum/2-leftSum) >= diff && diff != -1) {
                break;
            }
            diff = Math.abs(sum/2-leftSum);
            position += 1;
        }

        List<CharacterEntry> leftList = characterFrequencies.subList(0, position);
        List<CharacterEntry> rightList = characterFrequencies.subList(position, characterFrequencies.size());

        for(CharacterEntry entry : leftList) {
            codeMap.put(entry.getCharacter(), codeMap.get(entry.getCharacter()).concat("0"));
        }

        for(CharacterEntry entry : rightList) {
            codeMap.put(entry.getCharacter(), codeMap.get(entry.getCharacter()).concat("1"));
        }

        if(leftList.size() > 1) {
            recursiveListSplit(leftList, codeMap);
        }

        if(rightList.size() > 1) {
            recursiveListSplit(rightList, codeMap);
        }

        return codeMap;
    }

    private Long getTotalCount(List<CharacterEntry> values) {
        Long sum = (long) 0;
        for(CharacterEntry characterEntry : values) {
            sum += characterEntry.getFrequency();
        }
        return sum;
    }
}
