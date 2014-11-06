package com.as.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author Nick Mukhin
 */
public class Utils {
    public static byte[] createByteArray(InputStream inputStream) throws IOException {
        ArrayList<Byte> byteList = new ArrayList<Byte>(2048);
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            for (int i = 0; i < read; i++) {
                byteList.add(new Byte(bytes[i]));
            }
        }
        inputStream.close();
        byte[] arr = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            arr[i] = byteList.get(i).byteValue();
        }
        return arr;
    }
}

