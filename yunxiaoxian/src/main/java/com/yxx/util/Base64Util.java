package com.yxx.util;

import com.yxx.conversion.BASE64DecodedMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.IOException;

public class Base64Util {
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrings = base64.split("base64+");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrings[1].substring(1));

            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            return new BASE64DecodedMultipartFile(b, baseStrings[0].substring(0,baseStrings[0].length()-1));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
