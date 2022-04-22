package de.promove.autokss.web.util.exporter;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.FacesEvent;
import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.api.UIData;
import org.primefaces.component.api.UITable;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.datatable.export.DataTablePDFExporter;
import org.primefaces.component.export.ExportConfiguration;
import org.primefaces.model.ColumnMeta;
import org.primefaces.util.LangUtils;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MyPdfExporter extends DataTablePDFExporter {

    protected Document createDocument() {
        return new Document(PageSize.A4, 15.0F, 15.0F, 15.0F, 15.0F);
    }

    protected List<UIColumn> getExportableColumns(UITable table) {
        return ExporterUtil.getExportableColumns(table);
    }

}
