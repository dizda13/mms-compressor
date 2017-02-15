package view;

import domain.compression.Decompressor;
import domain.compression.shannonfano.FrequencyBasedDecompressor;
import domain.io.validation.DefaultFileValidator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by admin on 15/02/2017.
 */
public class DecompressView {


    private JPanel panel1;
    private JButton decompressButton;
    private JTextField textField2;
    private JTextField textField3;
    private JButton browseButton;
    private JButton browseButton1;
    private JTextField textField1;

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public DecompressView(){

        final Decompressor decompressor = new FrequencyBasedDecompressor(new DefaultFileValidator());


        final JFileChooser fileToDecompress = new JFileChooser();
        final JFileChooser folderToSave = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("BINARY FILES", "bin", "binary");
        fileToDecompress.setFileFilter(filter);

        folderToSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileToDecompress.showOpenDialog(panel1);
                if(fileToDecompress.getSelectedFile()!=null)
                    textField3.setText(fileToDecompress.getSelectedFile().getAbsolutePath());
            }
        });

        browseButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = folderToSave.showOpenDialog(panel1);
                if(fileToDecompress.getSelectedFile()!=null)
                    textField2.setText(folderToSave.getSelectedFile().getAbsolutePath());
            }
        });

        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File bin = new File(textField3.getText());
                File text = new File(textField2.getText() + "/" + textField1.getText() + ".txt");


                try {
                    text.createNewFile();

                    decompressor.decompress(bin, text);

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                    text.delete();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }
}
