package org.tzsd.controller.shopcar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.controller.BaseController;
import org.tzsd.manager.LoginUserManager;
import org.tzsd.pojo.ShopCar;
import org.tzsd.pojo.User;
import org.tzsd.service.SessionService;
import org.tzsd.service.ShopCarService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 购物车相关处理
 */
@Controller
@RequestMapping("/shopcar")
public class ShopCarController extends BaseController {

    @Resource(name = "shopCarService")
    private ShopCarService shopCarService;

    public ShopCarService getShopCarService() {
        return shopCarService;
    }

    public void setShopCarService(ShopCarService shopCarService) {
        this.shopCarService = shopCarService;
    }

    /**
     * @description: 获取购物车列表信息
     * @param request
     * @param response
     */
    @RequestMapping("/getShopCars.do")
    public void getShopCarList(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);
        if(user == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            String username = user.getName();
            String pageStr = request.getParameter("page");
            try {
                int page = Integer.valueOf(pageStr);
                List shopCarList = getShopCarService().searchShopCarList(username, page);
                long pageNum = getShopCarService().getShopCarListSize(username);
                if(shopCarList == null || shopCarList.isEmpty()){
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                }else {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                    jsonMap.put(JSONProtocolConstance.SHOPCAR_LIST, shopCarList);
                    jsonMap.put(JSONProtocolConstance.PAGE, pageNum);
                }
            }catch (Exception e){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                e.printStackTrace();
            }

        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 删除购物车条目
     * @param request
     * @param response
     */
    @RequestMapping("/deleteShopCar.do")
    public void deleteShopCar(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);
        String shopCarId = request.getParameter("shopCarId");
        String username = user.getName();
        if(user == null || shopCarId == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            try {
                if (getShopCarService().deleteShopCar(username, shopCarId)) {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                }
            }catch (Exception e){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                e.printStackTrace();
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 添加购物车条目
     * @param request
     * @param response
     */
    @RequestMapping("/addShopCar.do")
    public void addShopCar(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);
        if (user == null) {
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            String username = user.getName();
            String goods_id = request.getParameter("goodsId");
            String numStr = request.getParameter("num");

            if (goods_id == null || numStr == null) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            } else {
                try {
                    int number = Integer.valueOf(numStr);
                    if (getShopCarService().addShopCar(username, goods_id, number)) {
                        jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                    }
                } catch (Exception e) {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                    e.printStackTrace();
                }
            }
        }
        writeJSONProtocol(response, jsonMap);
    }
}
