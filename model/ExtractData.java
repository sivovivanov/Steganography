package model;

import static java.lang.Math.pow;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ExtractData {
    static String extention;
    // will take in a stego-image and output the payload
    public ExtractData(){

    }

    public static byte[] readImage(String img) {
        File f = new File(img);
        InputStream input = null;
        byte[] mule = null;
        try {
            input = new BufferedInputStream(new FileInputStream(f));
            mule = input.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mule;
    }

    public static byte[] extractData(byte[] input_array){
        int size = 0;
        int apos = 54;
        for(int a = 31; a >= 0; a--){
            int t = input_array[apos]%2;
            if((t*t) == 1){
                size += pow(2, a);
            }
            apos++;
        }

        byte[] ans = new byte[size/8];
        int f=86;
        int payloadPos=0;
        String extension = "";
        while (f<size+150){
            byte b = 0;
            for(int i = 7; i >= 0; i--){
                int t = input_array[f]%2;
                if((t*t) == 1){
                    b += pow(2,i);
                }
                f++;
            }
            if(f<=150) {
                if(b != 0){
                    byte[] tb = new byte[1];
                    tb[0] = b;
                    extension = extension +  new String(tb, StandardCharsets.UTF_8);
                }
            } else {
                ans[payloadPos] = b;
                payloadPos++;
            }
        }
        setExt(extension);

        return ans;
    }

    public static void rebuildPayload(byte[] bytes, String ext){
        try {
            FileOutputStream f = new FileOutputStream("extracted_data." + ext);
            f.write(bytes);
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setExt(String e){
        extention = e;
    }

    public static String getExtenstion(){
        return extention;
    }
}
