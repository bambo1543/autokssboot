package de.promove.autokss.web.common.crud;

import de.promove.autokss.dao.generic.QueryFetch;
import de.promove.autokss.model.Upload;
import de.promove.autokss.model.UploadEntity;
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

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Optional;

public class AbstractUploadCrudView<T extends UploadEntity> extends AbstractCrudView<T> {

    Logger logger = LoggerFactory.getLogger(AbstractUploadCrudView.class);

    public AbstractUploadCrudView(Class<T> clazz) {
        super(clazz);
    }

    public AbstractUploadCrudView(Class<T> clazz, QueryFetch[] editItemQueryFetch) {
        super(clazz, editItemQueryFetch);
    }

    public AbstractUploadCrudView(Class<T> clazz, QueryFetch[] itemsQueryFetch, QueryFetch[] editItemQueryFetch) {
        super(clazz, itemsQueryFetch, editItemQueryFetch);
    }


    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (file != null) {
            Optional<Upload> optionalUpload = editItem.getUploads().stream().filter(u -> u.getName().equals(file.getFileName())).findFirst();
            if(optionalUpload.isEmpty()) {
                Upload upload = new Upload(file.getFileName(), file.getContentType(), BlobProxy.generateProxy(file.getContent()));
                this.editItem.getUploads().add(upload);

                FacesMessage message = new FacesMessage(MessageFactory.getMessage("action.upload.growl.summary"),
                        MessageFactory.getMessage("action.upload.growl.detail", file.getFileName()));
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public StreamedContent getUpload(String name) {
        StreamedContent file = null;
        try {
            Optional<Upload> optionalUpload = editItem.getUploads().stream().filter(u -> u.getName().equals(name)).findFirst();
            if (optionalUpload.isPresent()) {
                InputStream binaryStream = optionalUpload.get().getContent().getBinaryStream();
                file = DefaultStreamedContent.builder()
                        .name(optionalUpload.get().getName())
                        .contentType(optionalUpload.get().getContentType())
                        .stream(() -> binaryStream)
                        .build();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return file;
    }

    public void removeUpload(String name) {
        Optional<Upload> optionalUpload = editItem.getUploads().stream().filter(u -> u.getName().equals(name)).findFirst();
        optionalUpload.ifPresent(upload -> editItem.getUploads().remove(upload));

        FacesMessage message = new FacesMessage(MessageFactory.getMessage("action.upload.remove.growl.summary"),
                MessageFactory.getMessage("action.upload.remove.growl.detail", name));
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
