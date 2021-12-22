package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;

// Represents the action to be taken when the user wants to save to file
class SaveAction extends AbstractAction {
    private final CarSimGUI carSimGUI;

    // MODIFIES: this
    // EFFECTS: constructs labels and fields necessary for displayPanel
    SaveAction(CarSimGUI carSimGUI) {

        super("SAVE");
        this.carSimGUI = carSimGUI;
    }

    // MODIFIES: carSimGUI.saveAndLoadImageDisplayPanel, carSimGUI.jsonWriter, carSimGUI.saveAndLoadImagePanel
    //           carSimGUI.saveLoadLabel
    // EFFECTS: performs the action necessary to save to file
    @Override
    public void actionPerformed(ActionEvent evt) {
        carSimGUI.saveAndLoadImageDisplayPanel.removeAll();
        carSimGUI.saveAndLoadImageDisplayPanel.revalidate();
        carSimGUI.saveAndLoadImageDisplayPanel.repaint();

        try {
            carSimGUI.jsonWriter.open();
            carSimGUI.jsonWriter.write(carSimGUI.styleList);
            carSimGUI.jsonWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        carSimGUI.saveLoadLabel.setText("    Saving Successful!");
        carSimGUI.saveAndLoadImageDisplayPanel.add(carSimGUI.savingImageLabel);
        carSimGUI.saveAndLoadImagePanel.add(carSimGUI.saveAndLoadImageDisplayPanel, BorderLayout.CENTER);
        carSimGUI.saveAndLoadImagePanel.revalidate();
    }
}
