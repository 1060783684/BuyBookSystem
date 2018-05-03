package org.tzsd.service;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Service;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.constance.TestConstance;
import org.tzsd.dao.UserDAO;
import org.tzsd.dao.UserDetailsInfoDAO;
import org.tzsd.pojo.User;
import org.tzsd.pojo.UserDetailsInfo;

import javax.annotation.Resource;
import java.io.File;
import java.util.Iterator;
import java.util.List;;

/**
 * @description: 管理用户信息的服务类
 */
@Service("userInfoService")
public class UserInfoService {
    public static final int MAN = 1;
    public static final int WOMAN = 2;

    public static final String USERHEAD_IMG_PATH = "/userimg/head/"; //客户端请求头像的根目录
    public static final String[] IMGS = new String[]{".jpg",".jpeg",".gif",".png"}; //头像支持的文件类型
    public static final int _4M = 4 * 1024 *1024; //文件大小上限

    @Resource(name = "userDao")
    private UserDAO userDAO;

    @Resource(name = "userDetailsInfoDAO")
    private UserDetailsInfoDAO userDetailsInfoDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDetailsInfoDAO getUserDetailsInfoDAO() {
        return userDetailsInfoDAO;
    }

    public void setUserDetailsInfoDAO(UserDetailsInfoDAO userDetailsInfoDAO) {
        this.userDetailsInfoDAO = userDetailsInfoDAO;
    }

    /**
     * @param username 用户输入的用户名
     * @param password 用户输入的密码
     * @return
     * @description: 验证用户名密码
     */
    public User validateUser(final String username, final String password) {
        if (username == null || password == null) {
            return null;
        }
        User user = userDAO.getUserByName(username);
        if (user == null) {
            return null;
        } else {
            String u = user.getName(); //数据库中获取的用户名
            String p = user.getPassword(); //数据库中获取的密码
            if (username.equals(u) && password.equals(p)) {
                return user;
            }
        }
        return null;
    }

    /**
     * @param username 输入的用户名
     * @return
     * @description: 通过用户名获取用户实例
     */
    public User getUser(String username) {
        if (username == null) {
            return null;
        }
        return userDAO.getUserByName(username);
    }

    /**
     * @description: 通过用户名密码创建新的用户实例并保存
     * @param username 用户名
     * @param password 密码
     * @return 保存成功后返回用户id
     */
    public long regist(final String username, final String password) {
        long id = Long.valueOf(username);
        User user = new User(id, username, password);
        UserDetailsInfo userExt = new UserDetailsInfo(id, null, null, null, username, null);
        return (long) userDAO.saveUser(user, userExt);
    }

    /**
     * @description: 修改密码
     * @param username 用户名
     * @param newPassword 新密码
     * @return 操作状态
     */
    public int updatePassword(String username, String password, String newPassword){
        if(username == null || newPassword == null){
            return JSONProtocolConstance.UPDATE_PW_FAIL; //操作错误
        }
        if(newPassword.length() < 6){
            return JSONProtocolConstance.UPDATE_PW_TOSHORT; //密码太短
        }
        if(newPassword.length() > 16){
            return JSONProtocolConstance.UPDATE_PW_TOLONG; //密码太长
        }
        if(newPassword.indexOf(" ") >= 0){
            return JSONProtocolConstance.UPDATE_PW_FOUL; //含有非法字符
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return JSONProtocolConstance.UPDATE_PW_FAIL; //操作错误
        }
        if(!user.getPassword().equals(password)){
            return JSONProtocolConstance.UPDATE_PW_PWFAIL; //原密码不正确
        }
        if(user.getPassword().equals(newPassword)){
            return JSONProtocolConstance.UPDATE_PW_SAME; //新旧密码相同
        }
        user.setPassword(newPassword);
        getUserDAO().merge(user);
        return JSONProtocolConstance.UPDATE_PW_SUCCESS;
    }

    /**
     * @description: 保存用户附加信息
     * @param id        用户id
     * @param name      名字
     * @param phone     电话号
     * @param sex       性别
     * @return
     */
    public void updateUserDetailsInfo(long id, String name, String phone, int sex) {
        UserDetailsInfo userExt = getUserDetailsInfoDAO().getUserDetailsInfoById(id);

        if (name != null && name.length() < 20) {
            userExt.setName(name);
        }
        if (phone != null && phone.length() <= 11) {
            userExt.setPhone(phone);
        }
        if (sex == MAN) {
            userExt.setSex("MAN");
        }
        if (sex == WOMAN) {
            userExt.setSex("WOMAN");
        }

        getUserDetailsInfoDAO().merge(userExt);
    }

    /**
     * @description: 保存用户头像信息
     * @param id 用户id
     * @param fileList 文件list
     * @return 操作结果
     */
    public int updateUserHeadInfo(long id, List<FileItem> fileList){
        if(fileList == null || fileList.isEmpty()){
            System.out.println("没有图片数组");
            return JSONProtocolConstance.UPLOAD_FAIL;
        }
        UserDetailsInfo userExt = getUserDetailsInfoDAO().getUserDetailsInfoById(id);
        Iterator<FileItem> it = fileList.iterator();
        String name = ""; //在服务端保存的文件名
        String extName = ""; //文件本身的扩展名

        String savePath = TestConstance.TEST_USERHEAD_PATH + USERHEAD_IMG_PATH; //在服务端的存储路径
        String savePath2 = TestConstance.TEST_USERHEAD_PATH2 + USERHEAD_IMG_PATH; //在服务端的存储路径

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
                    userExt.setHeadSrc(USERHEAD_IMG_PATH + name + extName);
                    getUserDetailsInfoDAO().merge(userExt);
                    return JSONProtocolConstance.UPLOAD_SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("没有图片");
        return JSONProtocolConstance.UPLOAD_FAIL;
    }

    /**
     * @param username 用户名
     * @return 若采用非正规手段可能产生异常, 在上一层处理
     * @description: 通过用户名获取用户详细信息实例
     */
    public UserDetailsInfo searchUserDetailsInfo(String username) {
        if (username == null) {
            return null;
        }
        User user = getUserDAO().getUserByName(username);
        if (user == null) {
            return null;
        }
        return userDetailsInfoDAO.getUserDetailsInfoById(user.getId());
    }
}
