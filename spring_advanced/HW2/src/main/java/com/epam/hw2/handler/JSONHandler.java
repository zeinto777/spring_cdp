package com.epam.hw2.handler;

import com.epam.hw2.constants.Constants;
import com.epam.hw2.model.impl.EventImpl;
import com.epam.hw2.model.impl.TicketImpl;
import com.epam.hw2.model.impl.UserAccountImpl;
import com.epam.hw2.model.impl.UserImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
public class JSONHandler {
    private static final Logger LOG = LoggerFactory.getLogger(JSONHandler.class);

    public JSONHandler() {
    }

    public void parse(File filename, Map<String, Object> storage) {
        Map<String, Object> buffer = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            Gson gson = new GsonBuilder().create();
            buffer.putAll(gson.fromJson(br, storage.getClass()));
            fillStorage(buffer, storage, gson);
        } catch (IOException ioe) {
            LOG.error(ioe.getMessage(), ioe);
        }
    }

    private void fillStorage(Map<String, Object> buffer, Map<String, Object> storage, Gson gson) {
        for (Map.Entry<String, Object> bufEntry : buffer.entrySet()) {
            String key = bufEntry.getKey();
            String prefix = key.split(":")[0];
            LinkedTreeMap values = (LinkedTreeMap) bufEntry.getValue();
            storage.put(key, gson.fromJson(gson.toJson(values), getClass(prefix)));
        }
    }

    private Class getClass(String prefix) {
        switch (prefix) {
            case Constants.EVENT:
                return EventImpl.class;
            case Constants.USER:
                return UserImpl.class;
            case Constants.USER_ACCOUNT:
                return UserAccountImpl.class;
            case Constants.TICKET:
                return TicketImpl.class;
            default:
                return null;
        }
    }
}
