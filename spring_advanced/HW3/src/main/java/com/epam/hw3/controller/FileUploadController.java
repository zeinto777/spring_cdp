package com.epam.hw3.controller;

import com.epam.hw3.storage.DBStorage;
import com.epam.hw3.handler.JSONHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Andrii_Pinchuk on 12/5/2015.
 */
@Controller
public class FileUploadController {
    private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);
    @Autowired
    private JSONHandler jsonHandler;
    @Autowired
    private DBStorage storage;
    private Map<String, Object> data = new LinkedHashMap<>();
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody String uploadFileHandler(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            LOG.debug("File is empty!");
            try {
                byte[] bytes = file.getBytes();
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists()){
                    dir.mkdirs();
                }
                File fileName = new File(dir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileName));
                stream.write(bytes);
                stream.close();
                LOG.debug("Upload file to server! FileName:" + fileName);

                jsonHandler.parse(fileName, data);
                storage.write(data);
                return data.toString();
            } catch (Exception e) {
                LOG.error(e.getMessage());
                return "Failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            String message = "Failed to upload " + name + "File is empty.";
            LOG.debug(message);
            return message;
        }
    }


}
