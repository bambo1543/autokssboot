package de.promove.autokss.web.util.exporter;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import de.promove.autokss.model.NamedEntity;
import de.promove.autokss.web.util.MessageFactory;
import de.promove.autokss.web.view.report.FilteredView;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlOutputLabel;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import lombok.AllArgsConstructor;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.api.UITable;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.datatable.export.DataTablePDFExporter;
import org.primefaces.component.export.ExportConfiguration;
import org.primefaces.component.export.ExporterOptions;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

@AllArgsConstructor
public class MyColoredPdfExporter extends DataTablePDFExporter {

    private String reportName;
    private FilteredView filteredView;

    protected Document createDocument() {
        return new Document(PageSize.A4, 15.0F, 15.0F, 15.0F, 15.0F);
    }

    protected List<UIColumn> getExportableColumns(UITable table) {
        return ExporterUtil.getExportableColumns(table);
    }

    @Override
    protected void preExport(FacesContext context, ExportConfiguration exportConfiguration) throws IOException {
        super.preExport(context, exportConfiguration);

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.getDefaultCell().setBorder(0);

        headerTable.addCell(new Phrase(reportName));
        headerTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(new Phrase(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime())));
        headerTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

        headerTable.addCell("");
        headerTable.addCell("");

        Map<String, FilterMeta> filterMap = filteredView.getFilterMap();
        for (Map.Entry<String, FilterMeta> entry : filterMap.entrySet()) {
            headerTable.addCell(new Phrase(filteredView.getI18nFieldName(entry.getKey())));
            Object filterValue = entry.getValue().getFilterValue();
            if(filterValue instanceof NamedEntity namedEntity) {
                headerTable.addCell(new Phrase(namedEntity.getName()));
            } else {
                headerTable.addCell(new Phrase(filterValue.toString()));
            }
        }
        headerTable.addCell("");
        headerTable.addCell("");


        getDocument().add(headerTable);
    }

    @Override
    protected void doExport(FacesContext context, DataTable table, ExportConfiguration exportConfiguration, int index) throws IOException {
        try {
            // Add empty paragraph between each exported tables
            if (index > 0) {
                Paragraph preface = new Paragraph();
                addEmptyLine(preface, 3);
                getDocument().add(preface);
            }

            PdfPTable pdfPTable = exportTable(context, table, exportConfiguration);
            pdfPTable.setWidthPercentage(100);
            getDocument().add(pdfPTable);
        }
        catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }

    protected void addColumnValue(DataTable table, PdfPTable pdfTable, List<UIComponent> components, Font font, UIColumn column) {
        FacesContext context = FacesContext.getCurrentInstance();
        final Font finalFont = new Font(font);

        String style = getStyle(components);
        Color color = getColor(style);
        if(color != null) {
            finalFont.setColor(color);
        }
        exportColumn(context, table, column, components, true, (s) -> {
            PdfPCell cell = createCell(column, new Paragraph(s, finalFont));
            pdfTable.addCell(cell);
        });
    }

    private String getStyle(List<UIComponent> components) {
        String style = null;
        if(components.size() == 1) {
            UIComponent uiComponent = components.get(0);
            if(uiComponent instanceof HtmlOutputText output) {
                style = output.getStyle();
            } else if(uiComponent instanceof HtmlOutputLabel output) {
                style = output.getStyle();
            }
        }
        return style;
    }

    private Color getColor(String style) {
        Color color = null;
        if (StringUtils.hasText(style)) {
            String[] split = style.split(";");
            Optional<String> colorTagString = Arrays.stream(split).filter(s -> s.startsWith("color:")).findFirst();
            if (colorTagString.isPresent()) {
                String[] colorTag = colorTagString.get().split(":");
                String colorString = colorTag[1].trim();
                if (colorString.contains("#")) {
                    color = Color.decode(colorString);
                }
            }
        }
        return color;
    }

}
