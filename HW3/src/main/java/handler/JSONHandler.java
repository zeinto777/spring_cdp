package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import domain.Auditorium;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */

public class JSONHandler {
    private static final Logger LOG = LoggerFactory.getLogger(JSONHandler.class);
    private Map<String, Auditorium> commonStorage = new LinkedHashMap<>();
    private String filename;

    public Map<String, Auditorium> getCommonStorage() {
        return commonStorage;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void parse() {
        Map<String, Object> buffer = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            Gson gson = new GsonBuilder().create();
            buffer.putAll(gson.fromJson(br, commonStorage.getClass()));
            fillStorage(buffer, commonStorage, gson);
        } catch (IOException | ParseException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void fillStorage(Map<String, Object> buffer, Map<String, Auditorium> storage, Gson gson) throws ParseException {
        for (Map.Entry<String, Object> bufEntry : buffer.entrySet()) {
            String key = bufEntry.getKey();
            String prefix = key.split(":")[0];
            LinkedTreeMap values = (LinkedTreeMap) bufEntry.getValue();
            storage.put(key, gson.fromJson(gson.toJson(values),Auditorium.class));
        }
    }

}
