package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

// Represents the action to be taken when the user wants to load from file
class LoadAction extends AbstractAction {
    private final CarSimGUI carSimGUI;

    // MODIFIES: this
    // EFFECTS: constructs labels and fields necessary for displayPanel
    LoadAction(CarSimGUI carSimGUI) {
        super("LOAD");
        this.carSimGUI = carSimGUI;
    }

    // MODIFIES: this, carSimGUI.saveAndLoadImageDisplayPanel, carSimGUI.saveLoadLabel, carSimGUI.saveAndLoadImagePanel
    //           carSimGUI.styleList
    // EFFECTS: performs the action necessary to load from file
    @Override
    public void actionPerformed(ActionEvent evt) {
        carSimGUI.saveAndLoadImageDisplayPanel.removeAll();
        carSimGUI.saveAndLoadImageDisplayPanel.revalidate();
        carSimGUI.saveAndLoadImageDisplayPanel.repaint();

        try {
            carSimGUI.styleList = carSimGUI.jsonReader.read();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        carSimGUI.saveLoadLabel.setText("   Loading Successful!");
        carSimGUI.saveAndLoadImageDisplayPanel.add(carSimGUI.loadingImageLabel);
        carSimGUI.saveAndLoadImagePanel.add(carSimGUI.saveAndLoadImageDisplayPanel, BorderLayout.CENTER);
        carSimGUI.saveAndLoadImagePanel.revalidate();
    }
}
