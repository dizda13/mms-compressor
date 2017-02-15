import view.CompressView;

import javax.swing.*;

/**
 * Created by admin on 13/02/2017.
 */
public class main {

    public static void main(String[] args){

        JFrame jFrame= new JFrame("MMS kompresija");
        jFrame.setContentPane(new CompressView().getPanel1());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);

    }
}
