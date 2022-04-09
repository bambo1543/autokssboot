package de.promove.autokss.web.crud;

import de.promove.autokss.model.Bereich;
import de.promove.autokss.model.Kuehlschmierstoff;
import de.promove.autokss.service.InitDBService;
import de.promove.autokss.web.common.crud.AbstractCrudBean;
import de.promove.autokss.web.util.MessageFactory;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import org.hibernate.engine.jdbc.BlobProxy;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.util.SerializableConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.SQLException;

@Component
@Scope("view")
public class KuehlschmierstoffBean extends AbstractCrudBean<Kuehlschmierstoff> {

    Logger logger = LoggerFactory.getLogger(KuehlschmierstoffBean.class);

    public KuehlschmierstoffBean() {
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
