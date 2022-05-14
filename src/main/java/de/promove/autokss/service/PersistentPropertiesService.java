package de.promove.autokss.service;

import de.promove.autokss.dao.QueryParameter;
import de.promove.autokss.dao.QueryParameterEntry;
import de.promove.autokss.model.PersistentProperties;
import de.promove.autokss.model.PersistentProperties_;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersistentPropertiesService extends GenericService {

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
