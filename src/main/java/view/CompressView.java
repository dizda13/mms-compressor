package view;

import domain.compression.Compressor;
import domain.compression.counter.CharacterEntry;
import domain.compression.counter.DefaultFrequencyCounter;
import domain.compression.shannonfano.ShannonFanoCompressor;
import domain.compression.shannonfano.codegenerator.ShannonFanoSequentialCodeGenerator;
import domain.io.validation.DefaultFileValidator;
import groovy.ui.Console;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;


/**
 * Created by admin on 13/02/2017.
 */
public class CompressView {
    public JPanel panel1;

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    private JButton compressButton;
    private JTextField textField2;
    private JTextField textField3;
    private JButton browseButton;
    private JButton browseButton1;
    private JTextField textField1;


    public CompressView(){

        final Compressor compressor = new ShannonFanoCompressor(
                new DefaultFileValidator(),
                new DefaultFrequencyCounter(new ArrayList<CharacterEntry>()),
                new ShannonFanoSequentialCodeGenerator()
        );

        final JFileChooser fileToCompress = new JFileChooser();
        final JFileChooser folderToSave = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileToCompress.setFileFilter(filter);

        folderToSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileToCompress.showOpenDialog(panel1);
                if(fileToCompress.getSelectedFile()!=null)
                    textField3.setText(fileToCompress.getSelectedFile().getAbsolutePath());
            }
        });

        browseButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = folderToSave.showOpenDialog(panel1);
                if(fileToCompress.getSelectedFile()!=null)
                    textField2.setText(folderToSave.getSelectedFile().getAbsolutePath());
            }
        });

        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File text = new File(textField3.getText());
                File bin = new File(textField2.getText() + "/" + textField1.getText() + ".bin");

                try {
                    bin.createNewFile();
                    compressor.compress(text, bin);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                    bin.delete();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }


}
