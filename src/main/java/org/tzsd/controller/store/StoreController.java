package org.tzsd.controller.store;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.controller.BaseController;
import org.tzsd.manager.LoginUserManager;
import org.tzsd.pojo.Store;
import org.tzsd.pojo.User;
import org.tzsd.service.StoreService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 处理商店相关操作
 */
@Controller
@RequestMapping("/store")
public class StoreController extends BaseController{

    @Resource(name = "storeService")
    private StoreService storeService;

    public StoreService getStoreService() {
        return storeService;
    }

    public void setStoreService(StoreService storeService) {
        this.storeService = storeService;
    }

    /**
     * @description: 请求店铺信息
     * @param request
     * @param response
     */
    @RequestMapping("/loadStoreInfo.do")
    public void loadStoreInfo(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);

        if(user == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            String username = user.getName(); //用户名

            if (username == null) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            } else {
                //操作
                Store store = getStoreService().getStore(username);
                if(store == null){
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                }else {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                    jsonMap.put(JSONProtocolConstance.STORE_INFO, store);
                }
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 修改店铺信息
     * @param request
     * @param response
     */
    @RequestMapping("updateStoreInfo.do")
    public void updateStoreInfo(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);

        if(user == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            String username = user.getName(); //用户名
            String name = request.getParameter("name");
            String addr = request.getParameter("addr");
            String descs = request.getParameter("descs");

            if (username == null) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            } else {
                //操作
                int result = getStoreService().updateStoreInfo(username, name, addr, descs);
                jsonMap.put(JSONProtocolConstance.RESULT, result);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 更新店铺头像信息
     * @param request
     * @param response
     */
    @RequestMapping("/updateStoreHead.do")
    public void updateUserHead(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);
        if (user == null) {
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        } else {
            if (ServletFileUpload.isMultipartContent(request)) {
                String username = user.getName();
                DiskFileItemFactory fac = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(fac);
                upload.setHeaderEncoding("utf-8");
                List fileList = null;
                try {
                    //从request中获取文件列表
                    fileList = upload.parseRequest(request);
                    int result = getStoreService().updateStoreHeadInfo(username, fileList);
                    //常规方式获取结果
                    jsonMap.put(JSONProtocolConstance.RESULT, result);
                } catch (FileUploadException e) {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                    e.printStackTrace();
                }
            } else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }
}
