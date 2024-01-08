package com.joker.tank.netsystem.server;

import com.joker.tank.netsystem.InUtil;
import com.joker.tank.netsystem.Message;
import com.joker.tank.netsystem.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author 燧枫
 * @date 2022/12/28 10:04
*/
public class SendNewsToAllService implements Runnable {

    private String sendContent = "";

    boolean flag = false;

    public void send(String content) {
        sendContent = content;
        flag = true;
    }

    @Override
    public void run() {
        while (true) {
           if (flag == true) {
               Message ms = new Message();
               ms.setSender("服务器");
               ms.setMesType(MessageType.MESSAGE_TO_ALL_MES);
               ms.setContent(sendContent);
               ms.setSendTime(new Date().toString());
               HashMap<String, SerConClieThread> hm = ManageClientThreads.getHm();
               Iterator<String> iterator = hm.keySet().iterator();
               while (iterator.hasNext()) {
                   String onLineUserId = iterator.next().toString();
                   SerConClieThread serConClieThread = hm.get(onLineUserId);
                   try {
                       ObjectOutputStream oos = new ObjectOutputStream(hm.get(onLineUserId).getSk().getOutputStream());
                       oos.writeObject(ms);
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               }
               flag = false;
           }
        }
    }
}
