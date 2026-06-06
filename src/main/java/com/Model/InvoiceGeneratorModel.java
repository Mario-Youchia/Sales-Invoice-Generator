package com.Model;

import com.Common;
import com.View.InvoiceGeneratorUI;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import static java.lang.Character.isLetter;
import static java.lang.Character.isSpaceChar;

public class InvoiceGeneratorModel {
    public void ClearSelections() {
        Common.InvoiceGenerator.clearSelection();
        Common.InvoiceLoad.clearSelection();
    }
    public void ClearInvoiceGeneratorTable() {
        for (int i = 0; i < Common.InvoiceGenerator.getRowCount(); i++) {
            for (int j = 0; j < Common.InvoiceGenerator.getColumnCount(); j++) {
                Common.InvoiceGenerator.setValueAt("", i, j);
            }
        }
    }
    public void ClearInvoiceInfo() {
        Common.InvoiceDateTF.setText(DateTimeFormatter.ofPattern("dd-MMM-yy").format(LocalDateTime.now()));
        Common.CustomerNameTF.setText("Name");
        Common.InvoiceTotalLbl2.setText("total");
    }
    public void ClearInvoice() {
        ClearInvoiceGeneratorTable();
        ClearInvoiceInfo();
        ClearSelections();
        InvoiceGeneratorUI.reEnableTF(Common.InvoiceDateTF, Common.ExceedMaxLengthOfDateLbl);
        InvoiceGeneratorUI.reEnableTF(Common.CustomerNameTF, Common.ExceedMaxLengthOfNameLbl);
    }
    public boolean isValidDate() {
        try {
            new SimpleDateFormat("dd-MMM-yy").setLenient(false);
            new SimpleDateFormat("dd-MMM-yy").parse(Common.InvoiceDateTF.getText());
        } catch (ParseException e) {
            System.out.println("Date format should be dd-MMM-yy.");
            return false;
        }
        return true;
    }
    public boolean isValidName(String Name) {
        for (int i = 0; i < Name.length(); i++) {
            if (!isLetter(Name.toCharArray()[i]) && !isSpaceChar(Name.toCharArray()[i])) {
                return false;
            }
        }
        return true;
    }
    public boolean isAPositiveNumber(String str) {
        try {
            if (isAValidString(str)) {
                int n = Integer.parseInt(str);
                if (n > 0) {
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Parsing empty string!");
        }
        return false;
    }
    public boolean isAValidString(String str) {
        if (str != null) {
            if (!str.equals("")) {
                return !str.trim().equals("");
            }
        }
        return false;
    }
    public boolean isAPositiveDouble(String str) {
        try {
            if (isAValidString(str)) {
                double n = Double.parseDouble(str);
                if (n > 0) {
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Parsing empty string!");
        }
        return false;
    }
    public void setInvoiceNumber() {
        String InvoiceNumber = "";
        try {
            Scanner InvoiceNum = new Scanner(new FileInputStream("Invoice Number.txt"));
            while (InvoiceNum.hasNextLine()) {
                InvoiceNumber = InvoiceNum.nextLine();
            }
            InvoiceNum.close();
        } catch (IOException e) {
            System.out.println("File not found!");
            System.exit(404);
        }
        Common.InvoiceNumLbl.setText(InvoiceNumber);
    }
}
