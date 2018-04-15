package org.tzsd.controller;

import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @description: Controller基类
 */
abstract public class BaseController {

    /**
     * @description: 发送json信息
     * @param response
     * @param jsonMap json键值对
     */
    protected void writeJSONProtocol(HttpServletResponse response, Map<String, Object> jsonMap){
        JSONObject json = new JSONObject(jsonMap);
        try {
            Writer writer = response.getWriter();
            writer.write(json.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
