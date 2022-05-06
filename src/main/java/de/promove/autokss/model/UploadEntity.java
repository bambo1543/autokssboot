package de.promove.autokss.model;

import java.util.List;
import java.util.Set;

public interface UploadEntity extends NamedEntity {

    public Set<Upload> getUploads();
    public void setUploads(Set<Upload> uploads);

    public List<Upload> getUploadList();

}
