package domain.compression.shannonfano;

import domain.compression.Decompressor;
import domain.compression.shannonfano.codegenerator.FrequencyBasedCompressionConstants;
import domain.io.validation.FileValidator;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ensar on 13/02/17.
 */
public class FrequencyBasedDecompressor implements Decompressor {

    private FileValidator fileValidator;

    public FrequencyBasedDecompressor(FileValidator fileValidator) {
        this.fileValidator = fileValidator;
    }

    public void decompress(File inputFile, File outputFile) throws IOException {
        fileValidator.validateInputFile(inputFile);
        fileValidator.validateOutputFile(outputFile);

        Map<String, Character> codeMap = new HashMap<String, Character>();

        InputStream is = new FileInputStream(inputFile);
        DataInputStream buf = new DataInputStream(is);

        String line = buf.readUTF();
        while(line != null && !line.contains(FrequencyBasedCompressionConstants.TABLE_END)) {
            String[] strings = line.split(FrequencyBasedCompressionConstants.TABLE_DELIMITER);
            if(strings.length == 2) {
                if(strings[0].equals(FrequencyBasedCompressionConstants.NEW_LINE_CHARACTER_TABLE_ALIAS)) {
                    codeMap.put(strings[1], '\n');
                } else {
                    codeMap.put(strings[1], strings[0].charAt(0));
                }
            } else if (strings.length == 3) {
                codeMap.put(strings[2], FrequencyBasedCompressionConstants.TABLE_DELIMITER.toCharArray()[0]);
            }
            line = buf.readUTF();
        }

        for(Map.Entry<String, Character> characterEntry : codeMap.entrySet()) {
            System.out.println(characterEntry.getKey() + " - " + characterEntry.getValue());
        }

        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        DataOutputStream bufferedWriter = new DataOutputStream(fileOutputStream);

        byte[] bytesRead = new byte[2];
        String code = "";
        while(buf.read(bytesRead) != -1) {
            for(int i = 0; i < 8; i++) {
                code += (bytesRead[0] & (0x80 >> i)) == 0 ? "0" : "1";
                if(codeMap.containsKey(code)) {
                    bufferedWriter.write(codeMap.get(code));
                    code = "";
                }
            }
            for(int i = 0; i < 8; i++) {
                code += (bytesRead[1] & (0x80 >> i)) == 0 ? "0" : "1";
                if(codeMap.containsKey(code)) {
                    bufferedWriter.write(codeMap.get(code));
                    code = "";
                }
            }
        }

        if(!code.equals("") && codeMap.containsKey(code)) {
            bufferedWriter.write(codeMap.get(code));
        }

    }
}
