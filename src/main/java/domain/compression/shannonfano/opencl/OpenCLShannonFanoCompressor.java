package domain.compression.shannonfano.opencl;

import domain.compression.Compressor;
import domain.compression.shannonfano.codegenerator.FrequencyBasedCompressionConstants;
import domain.io.validation.FileValidator;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by ensar on 14/02/17.
 */
public class OpenCLShannonFanoCompressor implements Compressor {
    private FileValidator fileValidator;

    public OpenCLShannonFanoCompressor(FileValidator fileValidator) {
        this.fileValidator = fileValidator;
    }

    public void compress(File inputFile, File outputFile) throws IOException {
        fileValidator.validateInputFile(inputFile);
        fileValidator.validateOutputFile(outputFile);

        ProcessBuilder p = new ProcessBuilder();
        System.out.println("Started EXE");
        URL clPath = getClass().getClassLoader().getResource("openCLCompressor");
        URL kernel = getClass().getClassLoader().getResource("prs_kernel_code.cl");
        p.command(clPath.getPath(),
                inputFile.getPath(),
                outputFile.getPath(),
                FrequencyBasedCompressionConstants.TABLE_DELIMITER,
                FrequencyBasedCompressionConstants.TABLE_END,
                kernel.getPath());
        System.out.println(p.command());
        p.redirectErrorStream(true);
        Process openClProcess = p.start();
        System.out.println("Started Running");
        try {
            int something;
            while ((something = openClProcess.getInputStream().read()) != -1) {
                System.out.print((char)something);
            }
            System.out.println("Process Finished with code " + openClProcess.waitFor());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
