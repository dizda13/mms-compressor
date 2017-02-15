import view.CompressView;
import view.DecompressView;

import javax.swing.*;

/**
 * Created by admin on 13/02/2017.
 */
public class main {

    public static void main(String[] args){
        if(args[0].equals("-c")){
        JFrame jFrame= new JFrame("MMS kompresija");
        jFrame.setContentPane(new CompressView().getPanel1());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
        }
        if(args[0].equals("-d")){
            JFrame jFrame= new JFrame("MMS dekompresija");
            jFrame.setContentPane(new DecompressView().getPanel1());
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.pack();
            jFrame.setVisible(true);
        }

    }
}
