package com.epam.hw2.view;

import com.epam.hw2.model.Event;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class PdfReportView extends AbstractPdfView {
    private static final String ID = "ID";
    private static final String TITLE = "TITLE";
    private static final String DATE = "DATE";
    private static final String PRICE = "PRICE";
    public static final String EVENT_PDF = "eventPdf";

    @Override
    protected void buildPdfDocument(Map model, Document document,
                                    PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        List<Event> events = (List<Event>) model.get(EVENT_PDF);
        Table table = initTable();

        events.forEach(e -> {
            try {
                table.addCell(valueOf(e.getId()));
                table.addCell(e.getTitle());
                table.addCell(valueOf(e.getDate()));
                table.addCell(valueOf(e.getTicketPrice()));
            } catch (BadElementException ex) {
            }
        });
        document.add(table);
    }

    private Table initTable() throws BadElementException {
        Table table = new Table(4);
        table.addCell(ID);
        table.addCell(TITLE);
        table.addCell(DATE);
        table.addCell(PRICE);
        return table;
    }
}