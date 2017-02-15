package domain.compression.shannonfano.codegenerator;

import domain.compression.counter.CharacterEntry;

import java.util.List;
import java.util.Map;

/**
 * Created by ensar on 14/02/17.
 */
public interface CodeGenerator {
    Map<Character, String> generateCodes(List<CharacterEntry> characterEntryList, Map<Character, String> codeMap);
}
