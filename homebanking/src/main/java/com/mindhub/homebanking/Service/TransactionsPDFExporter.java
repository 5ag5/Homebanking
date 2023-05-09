package com.mindhub.homebanking.Service;

import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Transaction;

public class TransactionsPDFExporter {
    private Set<Transaction> transactions;
    private Account account;
    public TransactionsPDFExporter(Set<Transaction> setTransactions, Account account){
        this.transactions = setTransactions;
        this.account = account;
    }

    private void writeTableHeaders(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("User ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Description", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Balance Account", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for(Transaction transaction:transactions){
            table.addCell(String.valueOf(transaction.getId()));
            table.addCell(String.valueOf(transaction.getDate().format(formatter)));
            table.addCell(transaction.getDescription());
            table.addCell("$ " + String.valueOf(transaction.getAmount()));
            table.addCell("$ " + String.valueOf(transaction.getCurrentBalance()));
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DecimalFormat decFormatter = new DecimalFormat("0.00");

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font1.setSize(15);
        font1.setColor(Color.BLACK);

        Paragraph p = new Paragraph("List of Transactions", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);

        /*Image logo = new Image("/url.com");
        logo.setAlignment(Image.);*/

        Paragraph numberP = new Paragraph("Account Number: " + account.getNumber(), font1);
        document.add(numberP);

        Paragraph balanceP = new Paragraph("Balance: " + "$" + decFormatter.format(account.getBalance()), font1);
        document.add(balanceP);

        Paragraph creationDateP = new Paragraph("Creation Date: " + account.getCreationDate().format(formatter), font1);
        document.add(creationDateP);

        Paragraph AccountTypeP = new Paragraph("Account Type: " + account.getAccountType(), font1);
        document.add(AccountTypeP);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeaders(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
