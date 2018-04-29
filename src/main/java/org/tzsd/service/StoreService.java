package org.tzsd.service;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Service;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.constance.TestConstance;
import org.tzsd.dao.StoreDAO;
import org.tzsd.dao.UserDAO;
import org.tzsd.pojo.Store;
import org.tzsd.pojo.User;
import org.tzsd.pojo.UserDetailsInfo;

import javax.annotation.Resource;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * @description: 店铺相关操作
 */
@Service("storeService")
public class StoreService {
    @Resource(name = "userDao")
    private UserDAO userDAO;

    @Resource(name = "storeDao")
    private StoreDAO storeDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public StoreDAO getStoreDAO() {
        return storeDAO;
    }

    public void setStoreDAO(StoreDAO storeDAO) {
        this.storeDAO = storeDAO;
    }

    public static final String STOREHEAD_IMG_PATH = "/userimg/storeHead/"; //客户端请求头像的根目录
    public static final String[] IMGS = new String[]{".jpg",".jpeg",".gif",".png"}; //头像支持的文件类型
    public static final int _4M = 4 * 1024 *1024; //文件大小上限

    /**
     * @description: 通过用户名获取商店实例
     * @param username 用户名
     * @return 商店实例
     */
    public Store getStore(String username){
        if(username == null){
            return null;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return null;
        }
        return getStoreDAO().getStoreByUserId(user.getId());
    }

    /**
     * @description: 店铺申请业务
     * @param name 名字
     * @param idNumber 身份证号
     * @param storeName 商店名
     * @param type 经营类型
     * @param business 营业执照号码
     * @param tax 税务
     * @return
     */
    public int storeApply(String username, String name, String idNumber, String storeName, String type, String business, String tax){
        if(name == null || idNumber == null || storeName == null || type == null || business == null || tax == null) {
            return JSONProtocolConstance.STORE_APPLY_INFO_NOTFULL;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return JSONProtocolConstance.STORE_APPLY_FAIL;
        }

        //生成审核信息


        //生成商店
        long userId = user.getId(); //用户id
        long stordId = userId * 10 + 1; //用户id * 10 +1
        Store store = getStoreDAO().getStoreById(stordId);
        if(store != null){
            return JSONProtocolConstance.STORE_APPLY_EXIST;
        }

        Store newStore = new Store(stordId, null, storeName, null, null, userId, 0, Store.NO);
        getStoreDAO().save(newStore);
        return JSONProtocolConstance.STORE_APPLY_SUCCESS;
    }

    /**
     * @description: 修改店铺基本信息
     * @param username 用户名
     * @param name 店铺昵称
     * @param addr 店铺地址
     * @param descs 店铺描述
     * @return 状态
     */
    public int updateStoreInfo(String username, String name, String addr, String descs){
        if(username == null){
            return JSONProtocolConstance.RESULT_FAIL;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return JSONProtocolConstance.RESULT_FAIL;
        }
        long userId = user.getId();
        Store store = getStoreDAO().getStoreByUserId(userId);
        if(store == null){
            return JSONProtocolConstance.RESULT_FAIL;
        }
        if(name != null){
            store.setName(name);
        }
        if(addr != null){
            store.setAddr(addr);
        }
        if(descs != null){
            store.setDescs(descs);
        }
        getStoreDAO().merge(store);
        return JSONProtocolConstance.RESULT_SUCCESS;
    }

    /**
     * @description: 保存店铺头像信息
     * @param username 用户名
     * @param fileList 文件list
     * @return 操作结果
     */
    public int updateStoreHeadInfo(String username, List<FileItem> fileList){
        if(fileList == null || fileList.isEmpty()){
            System.out.println("没有图片数组");
            return JSONProtocolConstance.UPLOAD_FAIL;
        }
        if(username == null){
            return JSONProtocolConstance.UPLOAD_FAIL;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return JSONProtocolConstance.UPLOAD_FAIL;
        }
        Store store = getStoreDAO().getStoreByUserId(user.getId());
        if(store == null){
            return JSONProtocolConstance.UPLOAD_FAIL;
        }
        long id = store.getId(); //店铺id
        Iterator<FileItem> it = fileList.iterator();
        String name = ""; //在服务端保存的文件名
        String extName = ""; //文件本身的扩展名

        String savePath = TestConstance.TEST_USERHEAD_PATH + STOREHEAD_IMG_PATH; //在服务端的存储路径
        String savePath2 = TestConstance.TEST_USERHEAD_PATH2 + STOREHEAD_IMG_PATH; //在服务端的存储路径

        //判断文件夹是否存在，若不存在则重新创建
        File dir = new File(savePath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        //头像图片只有一个
        if (it.hasNext()) {
            FileItem item = it.next();
            //判断该表单项是否是普通类型,不是就不保存
            if (!item.isFormField()) {
                name = item.getName();
                long size = item.getSize();
                String type = item.getContentType();
                System.out.println(size + " " + type);
                //超过文件大小上限
                if(item.getSize() > _4M){
                    return JSONProtocolConstance.UPLOAD_TOBIG;
                }
                if (name == null || name.trim().equals("")) {
                    System.out.println("没有名字");
                    return JSONProtocolConstance.UPLOAD_FAIL;
                }
                System.out.println(name);
                // 扩展名格式： extName就是文件的后缀,例如 .txt
                if (name.lastIndexOf(".") >= 0) {
                    extName = name.substring(name.lastIndexOf("."));
                }else {
                    System.out.println("没有扩展名");
                    return JSONProtocolConstance.UPLOAD_FAIL;
                }

                boolean isExt = false;
                //判断是否是支持的扩展类型
                for (String ext:IMGS){
                    if(ext.equals(extName)){
                        isExt = true;
                    }
                }
                if(!isExt){
                    System.out.println("不支持的类型");
                    return JSONProtocolConstance.UPLOAD_TYPE_ERROR;
                }
                File file = null;

                // 生成文件名：
                name = id+"head";
                file = new File(savePath + name + extName);

                if(file.exists()){
                    file.delete();
                }

                File saveFile = new File(savePath + name + extName);
                System.out.println(savePath + name + extName);
                File saveFile2 = new File(savePath2 + name + extName);
                System.out.println(saveFile2.getAbsolutePath());
                try {
                    item.write(saveFile);
//                    item.write(saveFile2);
                    //写入成功才保存
                    store.setImgsrc(STOREHEAD_IMG_PATH + name + extName);
                    System.out.println(STOREHEAD_IMG_PATH + name + extName);
                    getStoreDAO().merge(store);
                    return JSONProtocolConstance.UPLOAD_SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("没有图片");
        return JSONProtocolConstance.UPLOAD_FAIL;
    }
}
