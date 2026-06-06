package com.View;

import com.Common;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class InvoiceGeneratorUI extends JFrame implements ActionListener, KeyListener, MouseListener, MenuListener {
    // Get a handler (pointer) to the current frame to be used in the close operation
    public static JFrame frame;

    public InvoiceGeneratorUI() {
        super(Common.title);
        frame = this;
        setLayout(null);
        setSize(Common.dimensionsOfApplication);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent WE) {
                int userChoice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (userChoice == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                } else {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
        });
        setResizable(false);
        setIconImage(Common.icon.getImage());

        Common.btn.setBounds(100, 540, 150, 30);
        add(Common.btn);
        Common.btn.addActionListener(this);
        Common.btn.setToolTipText("This button is used to create a new invoice and cancel the current one.");

        Common.btn2.setBounds(300, 540, 120, 30);
        add(Common.btn2);
        Common.btn2.addActionListener(this);
        Common.btn2.setToolTipText("This button is used to delete the selected invoice.");

        Common.btn3.setBounds(640, 540, 80, 30);
        add(Common.btn3);
        Common.btn3.addActionListener(this);
        Common.btn3.setToolTipText("This button is used to save the current invoice to the invoices file.");

        Common.btn4.addActionListener(this);
        Common.btn4.setBounds(770, 540, 80, 30);
        add(Common.btn4);
        Common.btn4.setToolTipText("This button is used to ignore the entered invoice.");

        Common.btn5.setFont(Common.smallerFont);
        Common.btn5.addActionListener(this);
        Common.btn5.setBounds(880, 520, 90, 20);
        add(Common.btn5);
        Common.btn5.setToolTipText("This button is used to calculate the total price of each item and the total price of the invoice.");

        Common.btn6.setFont(Common.smallerFont);
        Common.btn6.addActionListener(this);
        Common.btn6.setBounds(880, 550, 90, 20);
        add(Common.btn6);
        Common.btn6.setToolTipText("This button is used to clear all user inputs including the selection of any row.");

        Common.btn7.setFont(Common.smallerFont);
        Common.btn7.addActionListener(this);
        Common.btn7.setBounds(20, 520, 75, 20);
        add(Common.btn7);
        Common.btn7.setToolTipText("This button is used to reload the invoices file.");

        Common.btn8.setFont(Common.smallerFont);
        Common.btn8.addActionListener(this);
        Common.btn8.setBounds(550, 520, 75, 20);
        add(Common.btn8);
        Common.btn8.setToolTipText("This button is add new rows to the invoices table.");

        setJMenuBar(Common.menuBar);
        Common.menuBar.add(Common.fileMenu);
        Common.menuBar.add(Common.shortcuts);
        Common.fileMenu.add(Common.loadFile);
        Common.fileMenu.add(Common.saveFile);
        Common.saveFile.addActionListener(this);
        Common.loadFile.addActionListener(this);
        Common.shortcuts.addMenuListener(this);

        Common.leftPanel.setLayout(null);
        Common.leftPanel.setBounds(10, 20, 480, 500);
        add(Common.leftPanel);

        Common.InvoiceLoad.setDefaultEditor(Object.class, null);
        Common.InvoiceLoad.setRowSelectionAllowed(true);
        Common.InvoiceLoad.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Common.leftPanel.add(Common.InvoiceLoadTableScroll);
        Common.InvoiceLoadTableScroll.setBounds(5, 30, 470, 460);
        Common.InvoiceLoadModel.setNumRows(5);

        Common.rightPanel.setLayout(null);
        Common.rightPanel.setBounds(510, 20, 480, 500);
        add(Common.rightPanel);

        Common.rightPanel.add(Common.invoiceLbl);
        Common.invoiceLbl.setBounds(10, 10, 90, 10);

        Common.rightPanel.add(Common.InvoiceNumLbl);
        Common.InvoiceNumLbl.setBounds(120, 10, 90, 10);

        Common.rightPanel.add(Common.invoiceDateLbl);
        Common.invoiceDateLbl.setBounds(10, 50, 90, 10);

        Common.rightPanel.add(Common.InvoiceDateTF);
        Common.InvoiceDateTF.setBounds(120, 45, 250, 20);
        Common.InvoiceDateTF.setText(Common.dateFormat);
        Common.InvoiceDateTF.addKeyListener(this);
        Common.InvoiceDateTF.addMouseListener(this);

        Common.rightPanel.add(Common.ExceedMaxLengthOfDateLbl);
        Common.ExceedMaxLengthOfDateLbl.setForeground(Color.RED);
        Common.ExceedMaxLengthOfDateLbl.setFont(Common.smallerFont);
        Common.ExceedMaxLengthOfDateLbl.setBounds(120, 65, 270, 20);
        Common.ExceedMaxLengthOfDateLbl.setVisible(false);

        Common.rightPanel.add(Common.customerNameLbl);
        Common.customerNameLbl.setBounds(10, 95, 100, 12);

        Common.rightPanel.add(Common.CustomerNameTF);
        Common.CustomerNameTF.setBounds(120, 90, 250, 20);
        Common.CustomerNameTF.addKeyListener(this);
        Common.CustomerNameTF.addMouseListener(this);

        Common.rightPanel.add(Common.ExceedMaxLengthOfNameLbl);
        Common.ExceedMaxLengthOfNameLbl.setForeground(Color.RED);
        Common.ExceedMaxLengthOfNameLbl.setFont(Common.smallerFont);
        Common.ExceedMaxLengthOfNameLbl.setBounds(120, 110, 270, 20);
        Common.ExceedMaxLengthOfNameLbl.setVisible(false);

        Common.rightPanel.add(Common.invoiceTotalLbl);
        Common.invoiceTotalLbl.setBounds(10, 140, 90, 10);

        Common.rightPanel.add(Common.InvoiceTotalLbl2);
        Common.InvoiceTotalLbl2.setBounds(120, 140, 90, 10);

        Common.rightPanel.add(Common.invoiceItemsPanel);
        Common.invoiceItemsPanel.setBorder(BorderFactory.createTitledBorder("Invoice Items"));
        Common.invoiceItemsPanel.setBounds(10, 170, 455, 320);

        Common.invoiceItemsPanel.setLayout(null);
        Common.invoiceItemsPanel.add(Common.InvoiceGeneratorTableScroll);
        Common.InvoiceGeneratorTableScroll.setBounds(15, 20, 430, 285);
        Common.InvoiceGeneratorModel.setNumRows(10);

        Common.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK), "updateAction");
        Common.rootPane.getActionMap().put("updateAction", Common.updateAction);
        Common.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK), "loadAction");
        Common.rootPane.getActionMap().put("loadAction", Common.loadAction);
        Common.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK), "saveFileAction");
        Common.rootPane.getActionMap().put("saveFileAction", Common.saveFileAction);
        Common.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK), "createNewInvoiceAction");
        Common.rootPane.getActionMap().put("createNewInvoiceAction", Common.createNewInvoiceAction);
        Common.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0), "deleteAction");
        Common.rootPane.getActionMap().put("deleteAction", Common.deleteAction);
        Common.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK), "addAction");
        Common.rootPane.getActionMap().put("addAction", Common.addAction);
        Common.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK), "saveAction");
        Common.rootPane.getActionMap().put("saveAction", Common.saveAction);
        Common.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelAction");
        Common.rootPane.getActionMap().put("cancelAction", Common.cancelAction);
        Common.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.SHIFT_MASK), "clearAction");
        Common.rootPane.getActionMap().put("clearAction", Common.clearAction);
        Common.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK | InputEvent.CTRL_MASK), "calculateAction");
        Common.rootPane.getActionMap().put("calculateAction", Common.calculateAction);
        Common.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, InputEvent.SHIFT_MASK), "closeAction");
        Common.rootPane.getActionMap().put("closeAction", Common.closeAction);

        Common.controller.UpdateInvoicesTable(true);
        Common.model.setInvoiceNumber();
    }
    public static boolean InvoiceItemsIsNotEmpty() {
        for (int i = 0; i < Common.InvoiceGenerator.getRowCount(); i++) {
            for (int j = 0; j < 5; j++) {
                if (Common.InvoiceGenerator.getValueAt(i, j) != null
                        && Common.InvoiceGenerator.getValueAt(i, j).toString().length() != 0) {
                    return true;
                }
            }
        }
        return false;
    }
    public static int numberOfValidRowsForCalculation(boolean isCalculationBtn) {
        int number = 0;
        int j = 0;
        InitializeArrayOfIndicesOfValidRowsForCalculationByNegativeOnes();
        for (int i = 0; i < Common.InvoiceGenerator.getRowCount(); i++) {
            if (Common.InvoiceGenerator.getValueAt(i, 2) != null
                    && Common.InvoiceGenerator.getValueAt(i, 3) != null
                    && Common.model.isAPositiveDouble(Common.InvoiceGenerator.getValueAt(i, 2).toString())
                    && Common.model.isAPositiveNumber(Common.InvoiceGenerator.getValueAt(i, 3).toString())) {
                Common.arrayOfIndicesOfValidRowsForCalculation[j] = i;
                j = j + 1;
            }
        }
        for (int i = 0; i < Common.InvoiceGenerator.getRowCount(); i++) {
            if (Common.arrayOfIndicesOfValidRowsForCalculation[i] != -1) {
                number++;
            }
        }
        if (number == 0 && isCalculationBtn) {
            JOptionPane.showConfirmDialog(null, "No valid rows for calculation!", "Calculation Error", JOptionPane.DEFAULT_OPTION);
        }
        return number;
    }
    public static void InitializeArrayOfIndicesOfValidRowsForCalculationByNegativeOnes() {
        Common.arrayOfIndicesOfValidRowsForCalculation = new int[Common.InvoiceGenerator.getRowCount()];
        for (int i = 0; i < Common.InvoiceGenerator.getRowCount(); i++) {
            Common.arrayOfIndicesOfValidRowsForCalculation[i] = -1;
        }
    }
    public static boolean SelectedRowIsNotEmptyInInvoiceLoadTable(int rowIndex) {
        for (int i = 0; i < Common.InvoiceLoad.getColumnCount(); i++) {
            if (!Objects.equals(Common.InvoiceLoad.getValueAt(rowIndex, i).toString(), "")) {
                return true;
            }
        }
        return false;
    }
    public static void Calculate() {
        if (numberOfValidRowsForCalculation(true) > 0) {
            double TOTAL = 0;
            for (int i = 0; i < Common.InvoiceGenerator.getRowCount(); i++) {
                if (Common.arrayOfIndicesOfValidRowsForCalculation[i] > -1) {
                    double Total = Double.parseDouble(Common.InvoiceGenerator.getValueAt(Common.arrayOfIndicesOfValidRowsForCalculation[i], 2).toString()) *
                            Integer.parseInt(Common.InvoiceGenerator.getValueAt(Common.arrayOfIndicesOfValidRowsForCalculation[i], 3).toString());
                    int rowNum = Common.arrayOfIndicesOfValidRowsForCalculation[i];
                    try {
                        Common.InvoiceGenerator.setValueAt(Double.toString(Total), rowNum, 4);
                        if (!Objects.equals(Common.InvoiceGenerator.getValueAt(Common.arrayOfIndicesOfValidRowsForCalculation[i], 4).toString(), "")) {
                            TOTAL = TOTAL + Double.parseDouble(Common.InvoiceGenerator.getValueAt(Common.arrayOfIndicesOfValidRowsForCalculation[i], 4).toString());
                        }
                        Common.InvoiceTotalLbl2.setText(String.format("%.2f", TOTAL));
                    } catch (Exception a) {
                        System.out.println("Error: Parsing empty string!");
                    }
                }
            }
        }
    }
    public void limitTextField(KeyEvent e, JTextField TF, JLabel warningLbl, int maxNumOfChar) {
        int LengthOfCustomerName = TF.getText().length();
        if (e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
            reEnableTF(TF, warningLbl);
        } else if (LengthOfCustomerName > maxNumOfChar) {
            TF.setEditable(false);
            TF.setBackground(Color.WHITE);
            TF.setBorder(BorderFactory.createLineBorder(Color.RED));
            warningLbl.setVisible(true);
            TF.setCursor(Common.defaultCursor);
        }
    }
    public static void reEnableTF(JTextField TF, JLabel warningLbl) {
        TF.setEditable(true);
        TF.setBackground(Common.defaultTFColor);
        TF.setBorder(Common.defaultTFBorder);
        warningLbl.setVisible(false);
    }
    public static void CreateNewInvoiceButton() {
        if (Common.controller.numberOfValidRows() > 0 || numberOfValidRowsForCalculation(false) > 0) {
            int userChoice = JOptionPane.showConfirmDialog(null, "There exists at least one valid row. \nDo you want to discard the current invoice and create new one?", "Create New Invoice", JOptionPane.YES_NO_OPTION);
            if (userChoice == JOptionPane.YES_OPTION) {
                Common.model.ClearInvoice();
            }
        } else {
            Common.model.ClearInvoice();
        }
    }
    public static void DeleteInvoiceButton() {
        if (Common.InvoiceLoad.getSelectedRow() > -1) {
            if (SelectedRowIsNotEmptyInInvoiceLoadTable(Common.InvoiceLoad.getSelectedRow())) {
                Common.controller.DeleteInvoice(Common.InvoiceLoad.getSelectedRow());
            } else {
                JOptionPane.showConfirmDialog(null, "Error: \nNo Invoice file is loaded or selected row is empty.", "Delete Error 2", JOptionPane.DEFAULT_OPTION);
            }
        } else
            JOptionPane.showConfirmDialog(null, "Select an invoice from the table", "Delete Error 1", JOptionPane.DEFAULT_OPTION);
    }
    public static void SaveButton() {
        if (!Common.CustomerNameTF.getText().matches("Name") || Common.InvoiceDateTF.getText().matches(Common.InvoiceDateTF.getText()) || InvoiceItemsIsNotEmpty()) {
            Common.controller.SaveUtility();
        } else {
            JOptionPane.showConfirmDialog(null, "Invoice is empty!", "Saving Failure", JOptionPane.DEFAULT_OPTION);
        }
    }
    public static void CancelButton() {
        if (!Common.CustomerNameTF.getText().matches("Name") || Common.InvoiceDateTF.getText().matches(Common.InvoiceDateTF.getText()) || InvoiceItemsIsNotEmpty()) {
            Common.controller.Save();
        }
    }
    public static void CalculateButton() {
        Calculate();
    }
    public static void ClearButton() {
        Common.model.ClearInvoice();
    }
    public static void UpdateButton() {
        Common.controller.UpdateInvoicesTable(true);
    }
    public static void AddButton() {
        Common.InvoiceGeneratorModel.addRow(new Object[]{});
    }
    public static void saveFileMenuItem() {
        try {
            Common.controller.SaveFile();
        } catch (Exception ex) {
            System.out.println("Couldn't save invoice!");
            ex.printStackTrace();
        }
    }
    public static void loadFileMenuItem() {
        Common.controller.LoadFile();
    }
    public static void showShortcutsMenuItem() {
        String ShortcutsInfo = "Shortcut\tFunction\n" +
                "Ctrl + u\tUpdate invoices table\n" +
                "Ctrl + o\tLoad a saved invoice\n" +
                "Ctrl + Shift + s\tSave invoice as separate file\n" +
                "Ctrl + Shift + c\tCreate a new invoice\n" +
                "Delete\tdelete an invoice from the table\n" +
                "Ctrl + Shift + a\tAdd a new row\n" +
                "Ctrl + s\tSave current invoice to invoices sheet \n" +
                "Esc\tCancel the current invoice\n" +
                "Shift + c\tClear the current invoice\n" +
                "Ctrl + Alt + c\tCalculate the invoice\n" +
                "Shift + Esc\tClose the program";
        JOptionPane.showConfirmDialog(null, new JTextArea(ShortcutsInfo), "List of shortcuts", JOptionPane.DEFAULT_OPTION);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Create New Invoice Button
        if (e.getSource().equals(Common.btn)) {
            CreateNewInvoiceButton();
        }
        // Delete Invoice Button
        else if (e.getSource().equals(Common.btn2)) {
            DeleteInvoiceButton();
        }
        // Save Button
        else if (e.getSource().equals(Common.btn3)) {
            SaveButton();
        }
        // Cancel Button
        else if (e.getSource().equals(Common.btn4)) {
            CancelButton();
        }
        // Calculate Button
        else if (e.getSource().equals(Common.btn5)) {
            CalculateButton();
        }
        // Clear Button
        else if (e.getSource().equals(Common.btn6)) {
            ClearButton();
        }
        // Update Button
        else if (e.getSource().equals(Common.btn7)) {
            UpdateButton();
        }
        // Add Button
        else if (e.getSource().equals(Common.btn8)) {
            AddButton();
        }
        // saveFile Menu Item
        else if (e.getSource().equals(Common.saveFile)) {
            saveFileMenuItem();
        }
        // loadFile Menu Item
        else if (e.getSource().equals(Common.loadFile)) {
            loadFileMenuItem();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource().equals(Common.CustomerNameTF)) {
            limitTextField(e, Common.CustomerNameTF, Common.ExceedMaxLengthOfNameLbl, Common.maxNumOfCharOfName);
        } else if (e.getSource().equals(Common.InvoiceDateTF)) {
            limitTextField(e, Common.InvoiceDateTF, Common.ExceedMaxLengthOfDateLbl, Common.maxNumOfCharOfDate);
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource().equals(Common.CustomerNameTF)) {
            Common.CustomerNameTF.getCaret().setVisible(true);
        } else if (e.getSource().equals(Common.InvoiceDateTF)) {
            Common.InvoiceDateTF.getCaret().setVisible(true);
        }
    }
    @Override
    public void menuSelected(MenuEvent e) {
        showShortcutsMenuItem();
    }

    // Not used listeners
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void menuDeselected(MenuEvent e) {
    }
    @Override
    public void menuCanceled(MenuEvent e) {
    }
}