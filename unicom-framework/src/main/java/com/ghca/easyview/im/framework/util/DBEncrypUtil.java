package com.ghca.easyview.im.framework.util;

import com.alibaba.druid.filter.config.ConfigTools;


/**
 * Description 数据库加密Utils
 *
 * Created by Administrator on 2017/3/29.
 */
public class DBEncrypUtil extends ConfigTools {


    public static void main(String[] args) throws Exception {

        //密码明文
        String password = "root";

        System.out.println("密码[ "+password+" ]的加密信息如下：\n");

        String [] keyPair = ConfigTools.genKeyPair(512);
        //私钥
        String privateKey = keyPair[0];
        //公钥
        String publicKey = keyPair[1];
        //用私钥加密后的密文
        password = ConfigTools.encrypt(privateKey, password);

        System.out.println("privateKey:"+privateKey);
        System.out.println("publicKey:"+publicKey);
        System.out.println("password:"+password);
        String decryptPassword=ConfigTools.decrypt(publicKey, password);
        System.out.println("decryptPassword："+decryptPassword);
    }
}
