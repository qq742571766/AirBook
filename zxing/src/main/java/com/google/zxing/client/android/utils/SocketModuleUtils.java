package com.google.zxing.client.android.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;

public class SocketModuleUtils {
    private Socket socket;
    private PrintStream out;
    private boolean flag = true;
    private String strUTF8 = null;
    private OnButtonClickListener listener = null;

    public SocketModuleUtils(String data) {
        try {
            strUTF8 = URLDecoder.decode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.1.11", 20006);
                    socket.setSoTimeout(10000);
                    out = new PrintStream(socket.getOutputStream());
                    InputStream is = socket.getInputStream();
                    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
                    while (flag) {
                        if (!socket.isClosed()) {
                            //发送数据到服务端
                            out.println(strUTF8);
                            out.flush();
                            if ("bye".equals(strUTF8)) {
                                flag = false;
                            } else {
                                try {
                                    //从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常
                                    String echo = buf.readLine();
                                    listener.OnButtonClick(echo);
                                    if (is.available() == 0) {
                                        socket.close();
                                        out.close();
                                        buf.close();
                                        flag = false;
                                        Log.e("SocketModuleUtils", "-------------stop:" + strUTF8);
                                    }
                                } catch (SocketTimeoutException e) {
                                    listener.OnButtonClick("-1");
                                    if (is.available() == 0) {
                                        socket.close();
                                        out.close();
                                        buf.close();
                                        flag = false;
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface OnButtonClickListener {
        void OnButtonClick(String file);
    }

    public void setOnButtonClickListener(OnButtonClickListener l) {
        this.listener = l;
    }
}
