package view;

import domain.compression.Compressor;
import domain.compression.Decompressor;
import domain.compression.counter.CharacterEntry;
import domain.compression.counter.DefaultFrequencyCounter;
import domain.compression.shannonfano.ShannonFanoCompressor;
import domain.compression.shannonfano.ShannonFanoDecompressor;
import domain.compression.shannonfano.codegenerator.ShannonFanoSequentialCodeGenerator;
import domain.io.validation.DefaultFileValidator;

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

    private JButton compress;
    private JTextField locToSaveCom;
    private JTextField fileToCom;
    private JButton browseFileCom;
    private JButton browseLocCom;
    private JTextField nameCom;

    private JTabbedPane tabbedPane1;
    private JTextField fileToDec;
    private JTextField locToSaveDec;
    private JTextField nameDec;
    private JButton browseFileDec;
    private JButton browseLocDec;
    private JButton decompress;
    private JComboBox comboBox1;


    public CompressView(){

        final Compressor compressor = new ShannonFanoCompressor(
                new DefaultFileValidator(),
                new DefaultFrequencyCounter(new ArrayList<CharacterEntry>()),
                new ShannonFanoSequentialCodeGenerator()
        );
        final Decompressor decompressor = new ShannonFanoDecompressor(new DefaultFileValidator());

        final JFileChooser fileToCompress = new JFileChooser();
        final JFileChooser fileToDecompress = new JFileChooser();

        final JFileChooser folderToSaveCom = new JFileChooser();
        final JFileChooser folderToSaveDec = new JFileChooser();

        FileNameExtensionFilter filterTxt = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileToCompress.setFileFilter(filterTxt);

        FileNameExtensionFilter filterBin = new FileNameExtensionFilter("BINARY FILES", "bin", "binary");
        fileToDecompress.setFileFilter(filterBin);

        folderToSaveCom.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderToSaveDec.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        browseFileCom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileToCompress.showOpenDialog(panel1);
                if(fileToCompress.getSelectedFile()!=null && returnVal!=JFileChooser.CANCEL_OPTION)
                    fileToCom.setText(fileToCompress.getSelectedFile().getAbsolutePath());
            }
        });

        browseFileDec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileToDecompress.showOpenDialog(panel1);
                if(fileToDecompress.getSelectedFile()!=null && returnVal!=JFileChooser.CANCEL_OPTION)
                    fileToDec.setText(fileToDecompress.getSelectedFile().getAbsolutePath());
            }
        });

        browseLocCom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = folderToSaveCom.showOpenDialog(panel1);
                if(fileToCompress.getSelectedFile()!=null && returnVal!=JFileChooser.CANCEL_OPTION)
                    locToSaveCom.setText(folderToSaveCom.getSelectedFile().getAbsolutePath());
            }
        });

        browseLocDec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = folderToSaveDec.showOpenDialog(panel1);
                if(fileToDecompress.getSelectedFile()!=null && returnVal!=JFileChooser.CANCEL_OPTION)
                    locToSaveDec.setText(folderToSaveDec.getSelectedFile().getAbsolutePath());
            }
        });

        compress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print(comboBox1.getSelectedIndex());
                File text = new File(fileToCom.getText());
                File bin = new File(locToSaveCom.getText() + "/" + nameCom.getText() + ".bin");

                try {
                    bin.createNewFile();
                    switch(comboBox1.getSelectedIndex()){
                        case 0:
                            compressor.compress(text, bin);
                            break;
                        case 1:
                            compressor.compress(text, bin);
                            break;
                        case 2:
                            compressor.compress(text, bin);
                            break;
                    }


                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                    bin.delete();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        decompress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File bin = new File(fileToDec.getText());
                File text = new File(locToSaveDec.getText() + "/" + nameDec.getText() + ".txt");


                try {
                    text.createNewFile();

                    decompressor.decompress(bin, text);

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                    text.delete();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }


}
