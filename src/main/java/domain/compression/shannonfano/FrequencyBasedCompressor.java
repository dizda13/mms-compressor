package domain.compression.shannonfano;

import domain.compression.Compressor;
import domain.compression.counter.CharacterEntry;
import domain.compression.counter.FrequencyCounter;
import domain.compression.shannonfano.codegenerator.CodeGenerator;
import domain.compression.shannonfano.codegenerator.FrequencyBasedCompressionConstants;
import domain.io.validation.FileValidator;

import java.io.*;
import java.util.*;

/**
 * Created by ensar on 13/02/17.
 */
public class FrequencyBasedCompressor implements Compressor {

    private FileValidator fileValidator;
    private FrequencyCounter frequencyCounter;
    private CodeGenerator codeGenerator;

    private Map<Character, String> codeMap;

    public FrequencyBasedCompressor(FileValidator fileValidator,
                                    FrequencyCounter frequencyCounter,
                                    CodeGenerator codeGenerator) {
        this.fileValidator = fileValidator;
        this.frequencyCounter = frequencyCounter;
        this.codeGenerator = codeGenerator;
    }

    public void compress(File inputFile, File outputFile) throws IOException {
        codeMap = initEmptyCodeMap();

        fileValidator.validateInputFile(inputFile);
        fileValidator.validateOutputFile(outputFile);

        List<CharacterEntry> characterFrequencies = countFrequencies(inputFile);

        initializeCodeMap(characterFrequencies);

        codeMap = codeGenerator.generateCodes(characterFrequencies, codeMap);

        FileOutputStream fileOutputStreamUnbuffered = new FileOutputStream(outputFile);

        BufferedOutputStream fileOutputStream = new BufferedOutputStream(fileOutputStreamUnbuffered);

        // header
//        PrintWriter printWriter = new PrintWriter(fileOutputStream);
//        BufferedWriter bufferedWriter = new BufferedWriter(printWriter);
        DataOutputStream bufferedWriter = new DataOutputStream(fileOutputStream);
        for(Map.Entry<Character, String> characterStringEntry : codeMap.entrySet()) {
            String key = characterStringEntry.getKey().toString();
            String value = characterStringEntry.getValue();

            if(key.equals("\n")) {
                key = FrequencyBasedCompressionConstants.NEW_LINE_CHARACTER_TABLE_ALIAS;
            }
            bufferedWriter.writeUTF(key + FrequencyBasedCompressionConstants.TABLE_DELIMITER + value);
            bufferedWriter.writeUTF("\n");
            System.out.println(key + FrequencyBasedCompressionConstants.TABLE_DELIMITER + value);
        }
        bufferedWriter.writeUTF(FrequencyBasedCompressionConstants.TABLE_END);
        bufferedWriter.writeUTF("\n");


        // file contents
        InputStream is = new FileInputStream(inputFile);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        byte aByte = 0x00;
        int counter = 0;

        String line = buf.readLine();
        while(line != null) {
            line = line.concat("\n");

            for(Character character : line.toCharArray()) {
                String code = codeMap.get(character);

                for(Character codeCharacter : code.toCharArray()) {
                    if(codeCharacter == '1') {
                        aByte |= (byte) (0x80 >> counter++);
                    } else {
                        counter++;
                    }
                    if(counter == 8) {
                        counter = 0;
                        bufferedWriter.write(aByte);
                        aByte = 0x00;
                    }
                }

            }

            line = buf.readLine();
        }

        if(counter > 0) {
            bufferedWriter.write(aByte);
            bufferedWriter.flush();
        }

        bufferedWriter.close();
        fileOutputStream.close();
    }

    protected Map<Character, String> initEmptyCodeMap() {
        return new HashMap<Character, String>();
    }

    protected List<CharacterEntry> countFrequencies(File inputFile) throws IOException {
        InputStream is = new FileInputStream(inputFile);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while(line != null) {
            sb.append(line).append("\n");
            line = buf.readLine();
        }

        String fileAsString = sb.toString();

        return frequencyCounter.countCharacterFrequency(fileAsString);
    }

    protected void initializeCodeMap(List<CharacterEntry> characterEntries) {
        for(CharacterEntry character : characterEntries) {
            codeMap.put(character.getCharacter(), "");
        }
    }
}
