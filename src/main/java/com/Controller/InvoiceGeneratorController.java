package com.Controller;

import com.Common;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalFileChooserUI;
import javax.swing.table.DefaultTableCellRenderer;
import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Integer.parseInt;

public class InvoiceGeneratorController {
    public void LoadFile() {
        JFileChooser loadFile = new JFileChooser();
        loadFile.setDialogTitle("Load File");
        loadFile.setSelectedFile(new File(""));
        loadFile.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\Saved Invoices"));
        loadFile.setAcceptAllFileFilterUsed(false);
        loadFile.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
        try {
            Field field = MetalFileChooserUI.class.getDeclaredField("fileNameTextField");
            field.setAccessible(true);
            JTextField tf = (JTextField) field.get(loadFile.getUI());
            tf.setEditable(false);
            loadFile.showDialog(null, "Open");
            Common.LoadedFilePath = loadFile.getSelectedFile().getAbsolutePath();
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(Common.LoadedFilePath));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFCell cell;
            int lastRowIndex = sheet.getLastRowNum();
            Common.InvoiceLoadModel.setNumRows(lastRowIndex);
            for (int i = 0; i < lastRowIndex; i++) {
                XSSFRow row = sheet.getRow(i + 1);
                int lastColumnIndex = row.getLastCellNum();
                for (int j = 0; j < lastColumnIndex; j++) {
                    cell = row.getCell(j);
                    Common.InvoiceLoad.setValueAt(cell.getStringCellValue(), i, j);
                    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                    renderer.setHorizontalAlignment(JLabel.CENTER);
                    Common.InvoiceLoad.getColumnModel().getColumn(j).setCellRenderer(renderer);
                }
            }
        } catch (IllegalAccessException e) {
            System.out.println("Cannot access Text Field");
            e.printStackTrace();
        } catch (NoSuchFieldException | IOException e) {
            System.out.println("File not found or IO failure.");
            e.printStackTrace();
        }
    }
    public void Save() {
        int userChoice = JOptionPane.showConfirmDialog(null, "Do you want to save the current invoice?", "Cancel", JOptionPane.YES_NO_CANCEL_OPTION);
        if (userChoice == JOptionPane.YES_OPTION) {
            SaveUtility();
        } else if (userChoice == JOptionPane.NO_OPTION) {
            Common.model.ClearInvoice();
        }
    }
    public static boolean checkIfInvoicesFileExist(String path) {
        File test = new File(path);
        return test.exists();
    }
    public void SaveToInvoicesSheet() {
        if (checkIfInvoicesFileExist(System.getProperty("user.dir") + "\\Invoices.xlsx")) {
            try {
                // 1) Read the Workbook
                XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("Invoices.xlsx"));
                // 2) Set style of new cells
                CellStyle style = workbook.createCellStyle();
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                // 3) Access the sheet in the workbook
                XSSFSheet sheet = workbook.getSheetAt(0);
                //4) Get the index of the last row in the sheet
                int lastRow = sheet.getLastRowNum();
                //5) Create a new row
                XSSFRow row = sheet.createRow(lastRow + 1);
                //6) Create cells in the new row and set values
                Cell cell0 = row.createCell(0);
                Cell cell1 = row.createCell(1);
                Cell cell2 = row.createCell(2);
                Cell cell3 = row.createCell(3);
                cell0.setCellValue(Common.InvoiceNumLbl.getText());
                cell0.setCellStyle(style);
                cell1.setCellValue(Common.InvoiceDateTF.getText());
                cell1.setCellStyle(style);
                cell2.setCellValue(Common.CustomerNameTF.getText());
                cell2.setCellStyle(style);
                cell3.setCellValue(Common.InvoiceTotalLbl2.getText());
                cell3.setCellStyle(style);
                //7) Output the new Excel file
                FileOutputStream out = new FileOutputStream("Invoices.xlsx");
                workbook.write(out);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("File not found!");
            }
        } else {
            // 1) Create a Workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            // 2) Creating cell styles
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(font);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            // 3) Create a spreadsheet
            XSSFSheet sheet = workbook.createSheet("Invoices");
            // 4) Create a row object
            XSSFRow row;
            // 5) Create cells and set values
            row = sheet.createRow(0);
            sheet.createFreezePane(4, 1); // Freezing the first row
            Cell cell0 = row.createCell(0);
            Cell cell1 = row.createCell(1);
            Cell cell2 = row.createCell(2);
            Cell cell3 = row.createCell(3);
            cell0.setCellValue("No.");
            cell1.setCellValue("Date");
            cell2.setCellValue("Customer");
            cell3.setCellValue("Total");
            cell0.setCellStyle(headerStyle);
            cell1.setCellStyle(headerStyle);
            cell2.setCellStyle(headerStyle);
            cell3.setCellStyle(headerStyle);
            // 6) Create cells and row for the data
            row = sheet.createRow(1);
            for (int i = 0; i < 4; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(style);
                if (cell.getColumnIndex() == 0) {
                    cell.setCellValue(Common.InvoiceNumLbl.getText());
                } else if (cell.getColumnIndex() == 1) {
                    cell.setCellValue(Common.InvoiceDateTF.getText());
                } else if (cell.getColumnIndex() == 2) {
                    cell.setCellValue(Common.CustomerNameTF.getText());
                } else if (cell.getColumnIndex() == 3) {
                    cell.setCellValue(Common.InvoiceTotalLbl2.getText());
                }
            }
            // 7) Auto resize columns
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }
            // 8) Write the created Excel file in project's folder
            try {
                FileOutputStream out = new FileOutputStream("Invoices.xlsx");
                workbook.write(out);
                out.close();
                System.out.println("Invoices file saved successfully!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Output file not found!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        UpdateInvoiceNumber();
        UpdateInvoicesTable(true);
    }
    public void UpdateInvoiceNumber() {
        try {
            File read = new File("Invoice Number.txt");
            Scanner sc = new Scanner(read);
            int n;
            if (sc.hasNextLine()) {
                n = parseInt(sc.nextLine());
                n++;
            } else {
                n = 0;
            }
            FileWriter Write = new FileWriter("Invoice Number.txt");
            Write.write(Integer.toString(n));
            Write.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("I/O Failure!");
        }
        Common.model.setInvoiceNumber();
    }
    public void UpdateInvoicesTable(boolean LoadMainFile) {
        try {
            if (LoadMainFile) {
                Common.LoadedFilePath = System.getProperty("user.dir") + "\\Invoices.xlsx";
            }
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(Common.LoadedFilePath));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFCell cell;
            int lastRowIndex = sheet.getLastRowNum();
            Common.InvoiceLoadModel.setNumRows(lastRowIndex);
            for (int i = 0; i < lastRowIndex; i++) {
                XSSFRow row = sheet.getRow(i + 1);
                int lastColumnIndex = row.getLastCellNum();
                for (int j = 0; j < lastColumnIndex; j++) {
                    cell = row.getCell(j);
                    Common.InvoiceLoad.setValueAt(cell.getStringCellValue(), i, j);
                    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                    renderer.setHorizontalAlignment(JLabel.CENTER);
                    Common.InvoiceLoad.getColumnModel().getColumn(j).setCellRenderer(renderer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SaveUtility() {
        if (InvoiceReadyToBeSaved()) {
            try {
                SaveToInvoicesSheet();
            } catch (Exception a) {
                System.out.println("Couldn't save invoice!");
                a.printStackTrace();
            }
            saveInvoicesDetails();
            printInvoiceDetails();
            printAllInvoicesDetails();
            Common.model.ClearInvoice();
        }
    }
    public boolean InvoiceReadyToBeSaved() {
        if (Common.model.isValidDate()) {
            if (Common.model.isValidName(Common.CustomerNameTF.getText())) {
                if (numberOfValidRows() > 0) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "There are no valid rows!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "The format of customer name is invalid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "The format of date is invalid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    public void SaveFile() {
        if (InvoiceReadyToBeSaved()) {
            // 1) Create a Workbook
            XSSFWorkbook workbook2 = new XSSFWorkbook();
            // 2) Creating cell styles
            XSSFFont font = workbook2.createFont();
            font.setBold(true);
            CellStyle headerStyle = workbook2.createCellStyle();
            headerStyle.setFont(font);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            CellStyle style = workbook2.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            // 3) Create a spreadsheet
            XSSFSheet sheet = workbook2.createSheet("Invoices");
            // 4) Create a row object
            XSSFRow row;
            // 5) Create cells and set values
            row = sheet.createRow(0);
            sheet.createFreezePane(4, 1); // Freezing the first row
            Cell cell0 = row.createCell(0);
            Cell cell1 = row.createCell(1);
            Cell cell2 = row.createCell(2);
            Cell cell3 = row.createCell(3);
            cell0.setCellValue("No.");
            cell1.setCellValue("Date");
            cell2.setCellValue("Customer");
            cell3.setCellValue("Total");
            cell0.setCellStyle(headerStyle);
            cell1.setCellStyle(headerStyle);
            cell2.setCellStyle(headerStyle);
            cell3.setCellStyle(headerStyle);
            // 6) Create cells and row for the data
            row = sheet.createRow(1);
            for (int i = 0; i < 4; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(style);
                if (cell.getColumnIndex() == 0) {
                    cell.setCellValue(Common.InvoiceNumLbl.getText());
                } else if (cell.getColumnIndex() == 1) {
                    cell.setCellValue(Common.InvoiceDateTF.getText());
                } else if (cell.getColumnIndex() == 2) {
                    cell.setCellValue(Common.CustomerNameTF.getText());
                } else if (cell.getColumnIndex() == 3) {
                    cell.setCellValue(Common.InvoiceTotalLbl2.getText());
                }
            }
            // 7) Auto resize columns
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }
            // 8) Save as separate file
            JFileChooser saveFile = new JFileChooser();
            saveFile.setDialogTitle("Save File");
            saveFile.setSelectedFile(new File("Invoice " + Common.InvoiceNumLbl.getText() + ".xlsx"));
            saveFile.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\Saved Invoices"));
            saveFile.setAcceptAllFileFilterUsed(false);
            saveFile.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
            if (saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                boolean OUT = true;
                if (saveFile.getSelectedFile().exists()) {
                    int userChoice = JOptionPane.showConfirmDialog(saveFile, saveFile.getSelectedFile().getName() + " already exists." +
                            "\nDo you want to replace it?", "Existing File With Same Name", JOptionPane.YES_NO_CANCEL_OPTION);
                    switch (userChoice) {
                        case JOptionPane.YES_OPTION:
                            break;
                        case JOptionPane.NO_OPTION:
                        case JOptionPane.CLOSED_OPTION:
                            OUT = false;
                            break;
                        case JOptionPane.CANCEL_OPTION:
                            saveFile.cancelSelection();
                            OUT = false;
                    }
                }
                if (OUT) {
                    File output = saveFile.getSelectedFile();
                    try (FileOutputStream out = new FileOutputStream(output)) {
                        workbook2.write(out);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("Output file not found!");
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Cannot output the file!");
                    }
                    UpdateInvoiceNumber();
                    saveInvoicesDetails();
                    printInvoiceDetails();
                    printAllInvoicesDetails();
                    Common.model.ClearInvoice();
                }
            }
        }
    }
    public void DeleteInvoice(int rowIndex) {
        try {
            // 1) Read the Workbook
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(Common.LoadedFilePath));
            // 2) Access the sheet in the workbook and delete the required row
            XSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowIndex = sheet.getLastRowNum();
            sheet.removeRow(sheet.getRow(rowIndex + 1));
            if (rowIndex + 1 < lastRowIndex) {
                sheet.shiftRows(rowIndex + 1 + 1, sheet.getLastRowNum(), -1);
            }
            //3) Output the new Excel file
            FileOutputStream out = new FileOutputStream(Common.LoadedFilePath);
            workbook.write(out);
            out.close();
            UpdateInvoicesTable(Objects.equals(Common.LoadedFilePath, System.getProperty("user.dir") + "\\Invoices.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File not found!");
        }
    }
    public void ValidRows() {
        int j = 0;
        InitializeArrayOfIndicesOfValidRowsByNegativeOnes();
        for (int i = 0; i < Common.InvoiceGenerator.getRowCount(); i++) {
            if (isValidRowItem(i)) {
                Common.arrayOfIndicesOfValidRows[j] = i;
                j = j + 1;
            }
        }
    }
    public void InitializeArrayOfIndicesOfValidRowsByNegativeOnes() {
        Common.arrayOfIndicesOfValidRows = new int[Common.InvoiceGenerator.getRowCount()];
        for (int i = 0; i < Common.InvoiceGenerator.getRowCount(); i++) {
            Common.arrayOfIndicesOfValidRows[i] = -1;
        }
    }
    public boolean isValidRowItem(int rowIndex) {
        int numOfColumns =  Common.InvoiceGenerator.getColumnCount();
        int[] validCells = new int[numOfColumns];
        for (int i = 0; i < numOfColumns; i++) {
            validCells[i] = -1;
        }
        if (Common.InvoiceGenerator.getValueAt(rowIndex, 0) != null
                && Common.model.isAPositiveNumber(Common.InvoiceGenerator.getValueAt(rowIndex, 0).toString())) {
            validCells[0] = 1;
        }
        if (Common.InvoiceGenerator.getValueAt(rowIndex, 1) != null
                && Common.model.isValidName(Common.InvoiceGenerator.getValueAt(rowIndex, 1).toString())) {
            validCells[1] = 1;
        }
        if (Common.InvoiceGenerator.getValueAt(rowIndex, 2) != null
                && Common.model.isAPositiveDouble(Common.InvoiceGenerator.getValueAt(rowIndex, 2).toString())) {
            validCells[2] = 1;
        }
        if (Common.InvoiceGenerator.getValueAt(rowIndex, 3) != null
                && Common.model.isAPositiveNumber(Common.InvoiceGenerator.getValueAt(rowIndex, 3).toString())) {
            validCells[3] = 1;
        }
        if (Common.InvoiceGenerator.getValueAt(rowIndex, 4) != null
                && Common.model.isAPositiveDouble(Common.InvoiceGenerator.getValueAt(rowIndex, 4).toString())) {
            validCells[4] = 1;
        }
        int sum = 0;
        for (int i = 0; i < numOfColumns; i++) {
            sum += validCells[i];
        }
        return sum == numOfColumns;
    }
    public int numberOfValidRows() {
        int number = 0;
        ValidRows();
        for (int i = 0; i < Common.InvoiceGenerator.getRowCount(); i++) {
            if (Common.arrayOfIndicesOfValidRows[i] != -1) {
                number++;
            }
        }
        return number;
    }
    public void printInvoiceDetails() {
        System.out.println("\n********** Details Of Current Invoice **********");
        System.out.println("Invoice Number: " + Common.InvoiceNumLbl.getText());
        System.out.println("{");
        System.out.println("Invoice Date (" + Common.InvoiceDateTF.getText() + ")" + ", Customer Name: " + Common.CustomerNameTF.getText());
        for (int indexOfValidRow : Common.arrayOfIndicesOfValidRows) {
            if (indexOfValidRow != -1) {
                System.out.println(Common.InvoiceGenerator.getValueAt(indexOfValidRow, 1) + ", "
                        + Common.InvoiceGenerator.getValueAt(indexOfValidRow, 2) + ", "
                        + Common.InvoiceGenerator.getValueAt(indexOfValidRow, 3));
            }
        }
        System.out.println("}");
    }
    public void saveInvoicesDetails() {
        XSSFWorkbook workbook = null;
        XSSFSheet sheet;
        if (checkIfInvoicesFileExist(System.getProperty("user.dir") + "\\Invoices Details.xlsx")) {
            //1) Defining the workbook, and getting the first sheet if file exists.
            try {
                workbook = new XSSFWorkbook(new FileInputStream(System.getProperty("user.dir") + "\\Invoices Details.xlsx"));
            } catch (IOException e) {
                System.out.println("I/O Failure!");
                e.printStackTrace();
            }
            assert workbook != null;
            sheet = workbook.getSheetAt(0);
        } else {
            //1) Creating a new workbook if file is not exist, and create a new sheet.
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Invoices Details");
        }
        // 2) Set style of new cells
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(font);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //3) Create 6 new rows:
        // row1 -> exact saving time,
        // row2 -> Invoice Date
        // row3 -> Customer Name
        // row4 -> Invoice Number
        // row5 -> Invoice Total
        // row6 -> Header(Count, Item Name, Price of one unit)
        XSSFRow[] row = new XSSFRow[6];
        for (int i = 0; i < 6; i++) {
            row[i] = sheet.createRow(sheet.getLastRowNum() + 1);
        }
        //4) Create cells in the new 6 rows, set values, and set their style
        XSSFCell cell0;
        XSSFCell cell1;
        XSSFCell cell2;
        // Create first row (0) (exact saving time)
        cell0 = row[0].createCell(0);
        cell1 = row[0].createCell(1);
        cell0.setCellValue("Saving Time:");
        cell0.setCellStyle(headerStyle);
        String timeFormat = "EEEE, dd-MMM-yyyy, HH:mm:ss Z";
        String currentTime = new SimpleDateFormat(timeFormat).format(Calendar.getInstance().getTime());
        cell1.setCellValue(currentTime);
        cell1.setCellStyle(style);
        // Create second row (1) (Invoice Date)
        cell0 = row[1].createCell(0);
        cell1 = row[1].createCell(1);
        cell0.setCellValue("Invoice Date:");
        cell0.setCellStyle(headerStyle);
        cell1.setCellValue(Common.InvoiceDateTF.getText());
        cell1.setCellStyle(style);
        // Create third row (2) (Customer Name)
        cell0 = row[2].createCell(0);
        cell1 = row[2].createCell(1);
        cell0.setCellValue("Customer Name:");
        cell0.setCellStyle(headerStyle);
        cell1.setCellValue(Common.CustomerNameTF.getText());
        cell1.setCellStyle(style);
        // Create fourth row (3) (Invoice Number)
        cell0 = row[3].createCell(0);
        cell1 = row[3].createCell(1);
        cell0.setCellValue("Invoice Number:");
        cell0.setCellStyle(headerStyle);
        cell1.setCellValue(Common.InvoiceNumLbl.getText());
        cell1.setCellStyle(style);
        // Create fifth row (4) (Invoice Total)
        cell0 = row[4].createCell(0);
        cell1 = row[4].createCell(1);
        cell0.setCellValue("Invoice Total:");
        cell0.setCellStyle(headerStyle);
        cell1.setCellValue(Common.InvoiceTotalLbl2.getText());
        cell1.setCellStyle(style);
        // Create sixth row (5) (Header [Count, Item Name, Price])
        cell0 = row[5].createCell(0);
        cell1 = row[5].createCell(1);
        cell2 = row[5].createCell(2);
        cell0.setCellValue("Count");
        cell0.setCellStyle(headerStyle);
        cell1.setCellValue("Item Name");
        cell1.setCellStyle(headerStyle);
        cell2.setCellValue("Price");
        cell2.setCellStyle(headerStyle);
        //5) Create new row object and set values
        for (int indexOfValidRow : Common.arrayOfIndicesOfValidRows) {
            if (indexOfValidRow != -1) {
                XSSFRow rowInfo = sheet.createRow(sheet.getLastRowNum() + 1);
                cell0 = rowInfo.createCell(0);
                cell1 = rowInfo.createCell(1);
                cell2 = rowInfo.createCell(2);
                cell0.setCellValue(Common.InvoiceGenerator.getValueAt(indexOfValidRow, 3).toString());
                cell0.setCellStyle(style);
                cell1.setCellValue(Common.InvoiceGenerator.getValueAt(indexOfValidRow, 1).toString());
                cell1.setCellStyle(style);
                cell2.setCellValue(Common.InvoiceGenerator.getValueAt(indexOfValidRow, 2).toString());
                cell2.setCellStyle(style);
            }
        }
        //6) Justify columns
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
        //7) Output the new Excel file
        try {
            FileOutputStream out = new FileOutputStream(System.getProperty("user.dir") + "\\Invoices Details.xlsx");
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("I/O Failure!");
        }
    }
    public void getIndicesOfSections() {
        if (checkIfInvoicesFileExist(System.getProperty("user.dir") + "\\Invoices Details.xlsx")) {
            XSSFWorkbook workbook = null;
            try {
                workbook = new XSSFWorkbook(new FileInputStream(System.getProperty("user.dir") + "\\Invoices Details.xlsx"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert workbook != null;
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;
            ArrayList<Integer> indices = new ArrayList<>();
            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                row = sheet.getRow(i);
                cell = row.getCell(0);
                if (cell.getStringCellValue().contains("Saving Time")) {
                    indices.add(i);
                }
            }
            Common.arrayOfIndicesOfSections = new int[indices.size()];
            for (int i = 0; i < indices.size(); i++) {
                Common.arrayOfIndicesOfSections[i] = indices.get(i);
            }
        } else {
            System.out.println("File is not found");
        }
    }
    public void printAllInvoicesDetails() {
        getIndicesOfSections();
        if (Common.arrayOfIndicesOfSections != null) {
            XSSFWorkbook workbook = null;
            try {
                workbook = new XSSFWorkbook(new FileInputStream(System.getProperty("user.dir") + "\\Invoices Details.xlsx"));
            } catch (IOException e) {
                System.out.println("I/O Failure!");
                e.printStackTrace();
            }
            assert workbook != null;
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row;
            XSSFCell cellInvoiceNumber;
            XSSFCell cellInvoiceDate;
            XSSFCell cellCustomerName;
            System.out.println("\n********** Details Of All Invoices **********");
            for (int i = 0; i < Common.arrayOfIndicesOfSections.length; i++) {
                row = sheet.getRow(Common.arrayOfIndicesOfSections[i] + 3);
                cellInvoiceNumber = row.getCell(1);
                System.out.println("Invoice Number: " + cellInvoiceNumber);
                System.out.println("{");
                row = sheet.getRow(Common.arrayOfIndicesOfSections[i] + 1);
                cellInvoiceDate = row.getCell(1);
                row = sheet.getRow(Common.arrayOfIndicesOfSections[i] + 2);
                cellCustomerName = row.getCell(1);
                System.out.println("Invoice Date (" + cellInvoiceDate + "), Customer Name: " + cellCustomerName);
                if (i == Common.arrayOfIndicesOfSections.length - 1) {
                    for (int j = Common.arrayOfIndicesOfSections[i] + 6; j < sheet.getLastRowNum() + 1; j++) {
                        printCellsNamePriceCount(sheet,j);
                    }
                } else {
                    for (int j = Common.arrayOfIndicesOfSections[i] + 6; j < Common.arrayOfIndicesOfSections[i + 1]; j++) {
                        printCellsNamePriceCount(sheet,j);
                    }
                }
                System.out.println("}\n");
            }
        }
    }
    public void printCellsNamePriceCount(XSSFSheet sheet, int rowIndex) {
        XSSFRow row = sheet.getRow(rowIndex);
        XSSFCell cellItemCount = row.getCell(0);
        XSSFCell cellItemName = row.getCell(1);
        XSSFCell cellItemPrice = row.getCell(2);
        System.out.println(cellItemName + ", " + cellItemPrice + ", " + cellItemCount);
    }
}