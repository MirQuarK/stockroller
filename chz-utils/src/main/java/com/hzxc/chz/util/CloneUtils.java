package com.hzxc.chz.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by hdwang on 2017/11/6.
 * 复制工具
 */
public class CloneUtils {

    /**
     * 深复制
     * @param obj 源对象
     * @param <T> 对象类型
     * @return 新对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepClone(T obj){
        T clonedObj = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            clonedObj = (T) ois.readObject();
            ois.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return clonedObj;
    }
}
