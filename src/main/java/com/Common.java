package com;

import com.Controller.InvoiceGeneratorController;
import com.Model.InvoiceGeneratorModel;
import com.View.InvoiceGeneratorUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// This class is created to store all common variables and objects (as static) between classes and use them instead of
// passing the same variables to all methods.
public class Common {
    // General properties of the application
    public final static String title = "Sales Invoice Generator";
    private final static int widthOfApplication = 1015;
    private final static int heightOfApplication = 650;
    public final static Dimension dimensionsOfApplication = new Dimension(widthOfApplication,heightOfApplication);
    private final static String pathToIcon = "icon.jpg";
    public final static ImageIcon icon = new ImageIcon(pathToIcon);
    public final static String dateFormat = DateTimeFormatter.ofPattern("dd-MMM-yy").format(LocalDateTime.now());


    // A font with same style and font name but smaller, it will be used in some buttons and warning messages
    public final static Font smallerFont = new Font(new JButton().getFont().getName(), new JButton().getFont().getStyle(), 11);

    // Buttons
    public final static JButton btn = new JButton("Create New Invoice");
    public final static JButton btn2 = new JButton("Delete Invoice");
    public final static JButton btn3 = new JButton("Save");
    public final static JButton btn4 = new JButton("Cancel");
    public final static JButton btn5 = new JButton("Calculate");
    public final static JButton btn6 = new JButton("Clear");
    public final static JButton btn7 = new JButton("Update");
    public final static JButton btn8 = new JButton("Add");

    // Menu bar
    public final static JMenuBar menuBar = new JMenuBar();
    public final static JMenuItem loadFile = new JMenuItem("Load File");
    public final static JMenuItem saveFile = new JMenuItem("Save File");
    public final static JMenu fileMenu = new JMenu("File");
    public final static JMenu shortcuts = new JMenu("Shortcuts");

    // Panels
    public final static JPanel leftPanel = new JPanel();
    public final static JPanel rightPanel = new JPanel();
    public final static JPanel invoiceItemsPanel = new JPanel();


    // Invoice Info (The upper part in the right panel)
    public final static JLabel InvoiceNumLbl = new JLabel("Num");
    public final static JTextField InvoiceDateTF = new JTextField();
    public final static int maxNumOfCharOfDate = 9;
    public final static JLabel ExceedMaxLengthOfDateLbl = new JLabel("The length of date cannot exceed " + maxNumOfCharOfDate + " characters!");
    public final static JTextField CustomerNameTF = new JTextField("Name");
    public final static int maxNumOfCharOfName = 25;
    public final static JLabel ExceedMaxLengthOfNameLbl = new JLabel("The length of name cannot exceed " + maxNumOfCharOfName + " characters!");
    public final static JLabel InvoiceTotalLbl2 = new JLabel("total");
    public final static Border defaultTFBorder = new JTextField().getBorder();
    public final static Color defaultTFColor = new JTextField().getBackground();
    public final static Cursor defaultCursor = new JTextField().getCursor();
    public final static JLabel invoiceLbl = new JLabel("Invoice Number");
    public final static JLabel invoiceDateLbl = new JLabel("Invoice Date");
    public final static JLabel invoiceTotalLbl = new JLabel("Invoice Total");
    public final static JLabel customerNameLbl = new JLabel("Customer Name");

    // Invoice Generator Table (table in the right panel)
    public final static String[] InvoiceGeneratorCols = {"No.", "Item Name", "Item Price", "Count", "Item Total"};
    public final static String[][] InvoiceGeneratorData = {{}};
    public final static DefaultTableModel InvoiceGeneratorModel = new DefaultTableModel(InvoiceGeneratorData, InvoiceGeneratorCols);
    public final static JTable InvoiceGenerator = new JTable(InvoiceGeneratorModel) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column != 4;
        }
    };

    // Invoice Load Table (table in the left panel)
    public final static String[] InvoiceLoadCols = {"No.", "Date", "Customer", "Total"};
    public final static String[][] InvoiceLoadData = {{}};
    public final static DefaultTableModel InvoiceLoadModel = new DefaultTableModel(InvoiceLoadData, InvoiceLoadCols);
    public final static JTable InvoiceLoad = new JTable(InvoiceLoadModel);

    // ScrollPanes
    public final static JScrollPane InvoiceGeneratorTableScroll = new JScrollPane(InvoiceGenerator);
    public final static JScrollPane InvoiceLoadTableScroll = new JScrollPane(InvoiceLoad);

    // Variables used to validate data before execution
    public static int[] arrayOfIndicesOfValidRowsForCalculation;
    public static int[] arrayOfIndicesOfValidRows;
    public static int[] arrayOfIndicesOfSections;
    public static String LoadedFilePath = "";


    // Actions used in key bindings
    public final static Action updateAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InvoiceGeneratorUI.UpdateButton();
        }
    };
    public final static Action loadAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InvoiceGeneratorUI.loadFileMenuItem();
        }
    };
    public final static Action saveFileAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InvoiceGeneratorUI.saveFileMenuItem();
        }
    };
    public final static Action createNewInvoiceAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InvoiceGeneratorUI.CreateNewInvoiceButton();
        }
    };
    public final static Action deleteAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InvoiceGeneratorUI.DeleteInvoiceButton();
        }
    };
    public final static Action addAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InvoiceGeneratorUI.AddButton();
        }
    };
    public final static Action saveAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InvoiceGeneratorUI.SaveButton();
        }
    };
    public final static Action cancelAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InvoiceGeneratorUI.CancelButton();
        }
    };
    public final static Action clearAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InvoiceGeneratorUI.ClearButton();
        }
    };
    public final static Action calculateAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InvoiceGeneratorUI.CalculateButton();
        }
    };
    public final static Action closeAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InvoiceGeneratorUI.frame.dispatchEvent(new WindowEvent(InvoiceGeneratorUI.frame, WindowEvent.WINDOW_CLOSING));
        }
    };

    // Create a root pane will be used to assign actions to shortcuts
    public final static JRootPane rootPane = InvoiceGeneratorUI.frame.getRootPane();

    // Objects from model and controller classes
    public final static com.Model.InvoiceGeneratorModel model = new InvoiceGeneratorModel();
    public final static InvoiceGeneratorController controller = new InvoiceGeneratorController();
}