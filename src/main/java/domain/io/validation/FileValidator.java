package domain.io.validation;

import java.io.File;
import java.io.IOException;

/**
 * Created by ensar on 13/02/17.
 */
public interface FileValidator {
    void validateInputFile(File inputFile) throws IOException;
    void validateOutputFile(File outputFile) throws IOException;
    void validateIOFile(File ioFile) throws IOException;
}
