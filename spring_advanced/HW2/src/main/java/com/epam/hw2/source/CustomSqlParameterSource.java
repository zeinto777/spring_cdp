package com.epam.hw2.source;

import com.epam.hw2.bean.DefaultDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomSqlParameterSource implements SqlParameterSource {
    private static final Logger LOG = LoggerFactory.getLogger(CustomSqlParameterSource.class);

    @Autowired
    private DefaultDataProvider defaultDataProvider;

    private final Map<String, Object> values = new LinkedHashMap<String, Object>();
    private final Map<String, Integer> sqlTypes = new HashMap<String, Integer>();
    private final Map<String, String> typeNames = new HashMap<String, String>();

    public CustomSqlParameterSource addValue(String paramName, Object value) {
        this.values.put(paramName, value);
        if (value instanceof SqlParameterValue) {
            registerSqlType(paramName, ((SqlParameterValue) value).getSqlType());
        }
        return this;
    }

    private void registerSqlType(String paramName, int sqlType) {
        LOG.debug(String.format("Register sql type - paramName = %s, sqlType = %d", paramName, sqlType));
        this.sqlTypes.put(paramName, sqlType);
    }


    public void registerTypeName(String paramName, String typeName) {
        LOG.debug(String.format("Register type name - paramName = %s, typeName = %s", paramName, typeName));
        this.typeNames.put(paramName, typeName);
    }

    public int getSqlType(String paramName) {
        LOG.debug(String.format("Get sql by type - paramName = %s", paramName));
        Integer sqlType = this.sqlTypes.get(paramName);
        if (sqlType != null) {
            LOG.debug(String.format("Result get sql by type - paramName = %s, value = %d", paramName, sqlType));
            return sqlType;
        }
        return TYPE_UNKNOWN;
    }

    public String getTypeName(String paramName) {
        LOG.debug(String.format("Get type by name - paramName = %s", paramName));
        return this.typeNames.get(paramName);
    }

    public Map<String, Object> getValues() {
        return this.values;
    }

    public Object getValue(String paramName) {
        if (paramName.equals("user_id") && (defaultDataProvider.getDefaultUser() != null)) {
            LOG.debug(String.format("Get value. Using default user - user_id = %d", defaultDataProvider.getDefaultUser().getId()));
            return defaultDataProvider.getDefaultUser().getId();
        }
        if (paramName.equals("event_id") && (defaultDataProvider.getDefaultEvent() != null)) {
            LOG.debug(String.format("Get value. Using default event - event_id = %d", defaultDataProvider.getDefaultEvent().getId()));
            return defaultDataProvider.getDefaultEvent().getId();
        }
        if (!hasValue(paramName)) {
            throw new IllegalArgumentException("No value registered for key '" + paramName + "'");
        }
        return this.values.get(paramName);
    }

    public boolean hasValue(String paramName) {
        return this.values.containsKey(paramName);
    }
}
