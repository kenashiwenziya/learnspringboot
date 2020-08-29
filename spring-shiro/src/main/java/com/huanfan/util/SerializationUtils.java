package com.huanfan.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationUtils {
    /**
     * 序列化对象
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;

        } catch (Exception e) {


            e.printStackTrace();
        }
        return null;

    }

    /**
     * 反序列化对象
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object deserialize(byte[] bytes) throws Exception {
        ByteArrayInputStream bais = null;

        bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();


    }
}
