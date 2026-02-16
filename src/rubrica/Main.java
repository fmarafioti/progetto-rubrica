package rubrica;

import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> new RubricaFrame().setVisible(true));

    }
}
