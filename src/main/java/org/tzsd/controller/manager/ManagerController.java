package org.tzsd.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.controller.BaseController;
import org.tzsd.pojo.Goods;
import org.tzsd.pojo.Store;
import org.tzsd.pojo.StoreUser;
import org.tzsd.service.ManagerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 管理员处理器
 */
@Controller
@RequestMapping("/managers")
public class ManagerController extends BaseController{
    @Resource(name = "managerService")
    private ManagerService managerService;

    public ManagerService getManagerService() {
        return managerService;
    }

    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    /**
     * @description: 按页获取为审核的店铺list
     * @param request
     * @param response
     */
    @RequestMapping("/getNotCheckStoreUserList.do")
    public void getNotCheckStoreList(HttpServletRequest request, HttpServletResponse response){
        String pageStr = request.getParameter("page");
        Map<String, Object> jsonMap = new HashMap();
        if(pageStr == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            try {
                int page = Integer.valueOf(pageStr);
                List<StoreUser> list = getManagerService().getNotCheckStoreUserList(page);
                if(list != null && !list.isEmpty()){
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                    jsonMap.put(JSONProtocolConstance.STOREUSER_LIST, list);
                    long pageNum = getManagerService().getNotCheckStoreUserPage();
                    jsonMap.put(JSONProtocolConstance.PAGE, pageNum);
                }else {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                }
            }catch (Exception e){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                e.printStackTrace();
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 审核对应storeId的店铺
     * @param request
     * @param response
     */
    @RequestMapping("/checkStoreUser.do")
    public void checkStore(HttpServletRequest request, HttpServletResponse response){
        String storeIdStr = request.getParameter("storeId");
        Map<String, Object> jsonMap = new HashMap();
        if(storeIdStr == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            try {
                long storeId = Long.valueOf(storeIdStr);
                if(getManagerService().checkStore(storeId)){
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                }else {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                }
            }catch (Exception e){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                e.printStackTrace();
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 审核对应storeId的店铺
     * @param request
     * @param response
     */
    @RequestMapping("/noPassStoreUser.do")
    public void noPassStore(HttpServletRequest request, HttpServletResponse response){
        String storeIdStr = request.getParameter("storeId");
        Map<String, Object> jsonMap = new HashMap();
        if(storeIdStr == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            try {
                long storeId = Long.valueOf(storeIdStr);
                if(getManagerService().noPassStore(storeId)){
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                }else {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                }
            }catch (Exception e){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                e.printStackTrace();
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 按页获取为审核的店铺list
     * @param request
     * @param response
     */
    @RequestMapping("/getNotCheckGoodsList.do")
    public void getNotCheckGoodsList(HttpServletRequest request, HttpServletResponse response){
        String pageStr = request.getParameter("page");
        Map<String, Object> jsonMap = new HashMap();
        if(pageStr == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            try {
                int page = Integer.valueOf(pageStr);
                List<Goods> list = getManagerService().getNotCheckGoodsList(page);
                if(list != null && !list.isEmpty()){
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                    jsonMap.put(JSONProtocolConstance.GOODS_LIST, list);
                    long pageNum = getManagerService().getNotCheckGoodsPage();
                    jsonMap.put(JSONProtocolConstance.PAGE, pageNum);
                }else {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                }
            }catch (Exception e){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                e.printStackTrace();
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 审核对应storeId的店铺
     * @param request
     * @param response
     */
    @RequestMapping("/checkGoods.do")
    public void checkGoods(HttpServletRequest request, HttpServletResponse response){
        String goodsId = request.getParameter("goodsId");
        Map<String, Object> jsonMap = new HashMap();
        if(goodsId == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            if(getManagerService().checkGoods(goodsId)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 审核对应storeId的店铺
     * @param request
     * @param response
     */
    @RequestMapping("/noPassGoods.do")
    public void noPassGoods(HttpServletRequest request, HttpServletResponse response){
        String goodsId = request.getParameter("goodsId");
        Map<String, Object> jsonMap = new HashMap();
        if(goodsId == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            if(getManagerService().noPasskGoods(goodsId)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }
}
