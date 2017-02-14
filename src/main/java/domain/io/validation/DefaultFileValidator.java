package domain.io.validation;

import java.io.File;
import java.io.IOException;

/**
 * Created by ensar on 13/02/17.
 */
public class DefaultFileValidator implements FileValidator {

    public static final String NULL_ARGUMENT = "Arguments cannot be null";
    public static final String CANNOT_WRITE = "Cannot write to file %s";
    public static final String CANNOT_READ = " Cannot read from file %s";

    public void validateInputFile(File inputFile) throws IOException {
        if(inputFile == null) {
            throw new NullPointerException(NULL_ARGUMENT);
        }

        if(!inputFile.canRead()) {
            throw new IOException(String.format(CANNOT_READ, inputFile.getName()));
        }
    }

    public void validateOutputFile(File outputFile) throws IOException {
        if(outputFile == null) {
            throw new NullPointerException(NULL_ARGUMENT);
        }

        if(!outputFile.canWrite()) {
            throw new IOException(String.format(CANNOT_WRITE, outputFile.getName()));
        }
    }

    public void validateIOFile(File ioFile) throws IOException {
        if(ioFile == null) {
            throw new NullPointerException(NULL_ARGUMENT);
        }

        if(!ioFile.canRead()) {
            throw new IOException(String.format(CANNOT_READ, ioFile.getName()));
        }

        if(!ioFile.canWrite()) {
            throw new IOException(String.format(CANNOT_WRITE, ioFile.getName()));
        }
    }
}
