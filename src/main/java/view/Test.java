package view;

import domain.compression.Compressor;
import domain.compression.Decompressor;
import domain.compression.shannonfano.opencl.OpenCLShannonFanoCompressor;
import domain.compression.shannonfano.ShannonFanoDecompressor;
import domain.io.validation.DefaultFileValidator;

import java.io.File;
import java.io.IOException;

/**
 * Created by ensar on 14/02/17.
 */
public class Test {
    public static void main(String [] args) throws IOException
    {
        Compressor compressor = new OpenCLShannonFanoCompressor(new DefaultFileValidator());
        Decompressor decompressor = new ShannonFanoDecompressor(new DefaultFileValidator());

        File file = new File("/Users/ensar/Projects/Fakultet/MMS/mms-compressor/src/main/java/dummy_510.txt");
        File outputFile = new File("out.txt");
        outputFile.createNewFile();
        File outputFile2 = new File("outDecompressed.txt");
        outputFile2.createNewFile();
//        compressor.compress(file, outputFile);
        decompressor.decompress(outputFile, outputFile2);
    }
}
