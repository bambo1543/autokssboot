package de.promove.autokss.web.util.exporter;

import org.primefaces.component.api.UIColumn;
import org.primefaces.component.api.UITable;
import org.primefaces.component.column.Column;
import org.primefaces.model.ColumnMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExporterUtil {

    public static List<UIColumn> getExportableColumns(UITable table) {
        List<UIColumn> exportColumns = new ArrayList<>();

        table.forEachColumn(col -> {
            if (col.isRendered() && col.isExportable()) {
                if(col instanceof Column column) {
                    Optional<Map.Entry<String, ColumnMeta>> columnMeta = table.getColumnMeta().entrySet().stream().filter(c -> c.getKey().endsWith(column.getId())).findFirst();
                    if(columnMeta.isEmpty() || columnMeta.get().getValue().getVisible()) {
                        exportColumns.add(col);
                    }
                }
            }
            return true;
        });

        return exportColumns;
    }

}
