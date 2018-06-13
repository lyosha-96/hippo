package com.oybek.hippo.lake;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oybek.hippo.things.Message;
import jdk.nashorn.api.scripting.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Hippo {
    private static final Logger logger = LoggerFactory.getLogger(MegaHippo.class);

    ScriptEngine engine;
    Invocable invocable;

    public Hippo(String brainFile) {
        engine = new ScriptEngineManager().getEngineByName("nashorn");

        try {
            engine.eval(new FileReader(brainFile));
        } catch (FileNotFoundException e) {
            logger.error("File not found: " + brainFile);
        } catch (ScriptException e) {
            logger.error("Script parse exception " + brainFile + "\n" );
            e.printStackTrace();
        }

        invocable = (Invocable) engine;
    }

    public void work(Message message) { try {
        ObjectMapper mapper = new ObjectMapper();

        engine.put("message", mapper.writeValueAsString(message));

        JSObject messageObj = (JSObject) engine.eval("JSON.parse(message)");

        invocable.invokeFunction("think", messageObj);
    } catch (Exception e) {
        logger.error("Error: ");
        e.printStackTrace();
    } }
}
