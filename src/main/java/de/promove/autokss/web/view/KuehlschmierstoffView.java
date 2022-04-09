package de.promove.autokss.web.view;

import de.promove.autokss.configuration.JsfConfiguration;
import de.promove.autokss.model.Kuehlschmierstoff;
import de.promove.autokss.web.common.crud.AbstractCrudView;
import de.promove.autokss.web.util.MessageFactory;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.hibernate.engine.jdbc.BlobProxy;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.SQLException;

@Component
@Scope(JsfConfiguration.VIEW_SCOPE)
public class KuehlschmierstoffView extends AbstractCrudView<Kuehlschmierstoff> {

    Logger logger = LoggerFactory.getLogger(KuehlschmierstoffView.class);

    public KuehlschmierstoffView() {
        super(Kuehlschmierstoff.class);
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if(file != null) {
            this.editItem.setDatanblattName(file.getFileName());
            this.editItem.setDatenblatt(BlobProxy.generateProxy(file.getContent()));

            FacesMessage message = new FacesMessage(MessageFactory.getMessage("action.upload.growl.summary"),
                    MessageFactory.getMessage("action.upload.growl.detail", file.getFileName()));
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public StreamedContent getDatenblattFile() {
        StreamedContent file = null;
        try {
            InputStream binaryStream = editItem.getDatenblatt().getBinaryStream();
            file = DefaultStreamedContent.builder()
                    .name(editItem.getDatanblattName())
                    .contentType("application/pdf")
                    .stream(() -> binaryStream)
                    .build();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return file;
    }

    public void removeDatenblatt() {
        editItem.setDatanblattName(null);
        editItem.setDatenblatt(null);
    }
}
