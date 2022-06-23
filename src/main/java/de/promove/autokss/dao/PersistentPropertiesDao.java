package de.promove.autokss.dao;

import de.promove.autokss.dao.generic.GenericDao;
import de.promove.autokss.dao.generic.QueryParameter;
import de.promove.autokss.dao.generic.QueryParameterEntry;
import de.promove.autokss.model.PersistentProperties;
import de.promove.autokss.model.PersistentProperties_;
import de.promove.autokss.service.encryption.Decrypt;
import de.promove.autokss.service.encryption.Encrypt;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Repository
public class PersistentPropertiesDao extends GenericDao {

    @Decrypt
    public Properties loadProperties(@Nullable String prefix) {
        Properties result = new Properties();

        List<PersistentProperties> persistentProperties;
        if(StringUtils.hasText(prefix)) {
            persistentProperties = list(PersistentProperties.class,
                    QueryParameter.with(PersistentProperties_.key, QueryParameterEntry.Operator.STARTS, prefix));
        } else {
            persistentProperties = listAll(PersistentProperties.class);
        }

        Map<String, String> collect = persistentProperties.stream().collect(Collectors.toMap(
                PersistentProperties::getKey,
                PersistentProperties::getValue));
        result.putAll(collect);
        return result;
    }

    @Encrypt
    public void persistProperties(Properties properties) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            PersistentProperties pp = find(PersistentProperties.class, QueryParameter.with(PersistentProperties_.key, entry.getKey()));
            if(pp == null) {
                pp = new PersistentProperties((String) entry.getKey(), (String) entry.getValue());
                persist(pp);
            } else {
                pp.setValue((String) entry.getValue());
                merge(pp);
            }
        }
    }

}
