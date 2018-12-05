package com.xujing.miaosha.test;

import java.io.File;

public class SimpleSerial {

    public static void main(String[] args) {
        File file = new File("person.out");

        /*ObjectOutputStream oout = new ObjectOutputStream(new OutputStream(file));
        User user = new User(1000,"徐晶");
        oout.writeObject(user);
        oout.close();

        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
        Object newPerson = oin.readObject(); // 没有强制转换到Person类型
        oin.close();
        System.out.println(newPerson);
*/
    }
}
