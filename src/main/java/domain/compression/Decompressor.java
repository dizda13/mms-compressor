package domain.compression;

import java.io.File;
import java.io.IOException;

/**
 * Created by ensar on 13/02/17.
 */
public interface Decompressor {
    void decompress(File inputFile, File outputFile) throws IOException;
}
