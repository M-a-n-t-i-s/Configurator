package editor.main;

import editor.gui.GUI;

import java.awt.*;

public final class JTextEditor {

    public static void main(final String[] args) {

        EventQueue.invokeLater(() -> new GUI().setVisible(true));

    }

}
