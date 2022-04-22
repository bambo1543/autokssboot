package de.promove.autokss.web.util.exporter;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.api.UITable;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.export.DataTableExcelExporter;
import org.primefaces.component.datatable.export.DataTableExcelXExporter;
import org.primefaces.model.ColumnMeta;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MyExcelExporter extends DataTableExcelExporter {

    protected List<UIColumn> getExportableColumns(UITable table) {
        return ExporterUtil.getExportableColumns(table);
    }

}
