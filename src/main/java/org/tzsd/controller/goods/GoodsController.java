package org.tzsd.controller.goods;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.controller.BaseController;
import org.tzsd.pojo.EvaluateInfo;
import org.tzsd.pojo.Goods;
import org.tzsd.service.EvaluateInfoService;
import org.tzsd.service.GoodsService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 物品相关处理
 */
@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController {

    @Resource(name = "goodsService")
    GoodsService goodsService;

    @Resource(name = "evaluateInfoService")
    private EvaluateInfoService evaluateInfoService;

    public GoodsService getGoodsService() {
        return goodsService;
    }

    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    public EvaluateInfoService getEvaluateInfoService() {
        return evaluateInfoService;
    }

    public void setEvaluateInfoService(EvaluateInfoService evaluateInfoService) {
        this.evaluateInfoService = evaluateInfoService;
    }

    /**
     * @description: 物品搜索
     * @param request
     * @param response
     */
    @RequestMapping("/searchGoodsList")
    public void searchGoods(HttpServletRequest request, HttpServletResponse response){
        String typeStr = request.getParameter("type");
        String costStr = request.getParameter("cost");
        String keywords = request.getParameter("keywords");
        String pageStr = request.getParameter("page");
        int cost = -1;
        int page = 0;
        int type = -1;

        if(costStr != null){
            try{
                cost = Integer.valueOf(costStr);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(pageStr != null){
            try{
                page = Integer.valueOf(pageStr);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(typeStr != null){
            try{
                type = Integer.valueOf(typeStr);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        List<Goods> goodses = getGoodsService().searchGoods(type, cost, keywords, page);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(goodses != null && !goodses.isEmpty()){
            jsonMap.put(JSONProtocolConstance.RESULT,  JSONProtocolConstance.RESULT_SUCCESS);
            jsonMap.put(JSONProtocolConstance.GOODS_LIST, goodses);
        }else {
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }

        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 获取单个商品的详细信息
     * @param request
     * @param response
     */
    @RequestMapping("/getGoodsInfo")
    public void getGoodsInfo(HttpServletRequest request, HttpServletResponse response){
        String goodsId = request.getParameter("goodsId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(goodsId == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            Goods goods = getGoodsService().getGoodsInfo(goodsId);
            if (goods == null) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            } else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                jsonMap.put(JSONProtocolConstance.GOODS_INFO, goods);
            }
        }
    }

    /**
     * @description: 获取一个物品的评论信息
     * @param request
     * @param response
     */
    @RequestMapping("/getEvaluateInfoList")
    public void getEvaluateInfoList(HttpServletRequest request, HttpServletResponse response){
        String goodsId = request.getParameter("goodsId");
        Map<String, Object> jsonMap = new HashMap();
        if(goodsId == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            List<EvaluateInfo> list = getEvaluateInfoService().getEvaluateList(goodsId);
            if(list == null || list.isEmpty()){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                jsonMap.put(JSONProtocolConstance.EVALUATE_LIST, list);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }
}
