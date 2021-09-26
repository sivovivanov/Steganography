import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class HideData {
    // will take in a cover image and payload and output the stego-image
    public HideData(){

    }

    public static byte[] readImage(String img) {
        File f = new File(img);
        InputStream stegg = null;
        byte[] mule = null;
        try {
            stegg = new BufferedInputStream(new FileInputStream(f));
            mule = stegg.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mule;
    }

    public static byte[] readPayload(String pload) {
        File f = new File(pload);
        byte[] payload_bytes = new byte[(int) f.length()];

        try {
            FileInputStream fis = new FileInputStream(f);
            fis.read(payload_bytes);
            fis.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return payload_bytes;
    }

    public static byte[] hideData(String c, String p){
        byte[] cover = readImage(c);
        byte[] payload = readPayload(p);
        byte[] steggo = readImage(c);

        /*  f = number of bytes in cover image. initial value is after header
            i = number of bits in payload
            j = keep tracking of which bit in current byte in payload
            f is 86: 54 + 32
         */

        int dot=p.length()-1;
        while (dot>=0){
            if(p.charAt(dot) == '.'){
                break;
            }
            dot--;
        }
        String extString = p.substring(dot+1);
        byte[] extBytesZeros = new byte[8];
        Arrays.fill(extBytesZeros, (byte) 0);
        byte[] extBytes = extString.getBytes();
        for(int xl = 8-extString.length(); xl<8; xl++){
            extBytesZeros[xl] = extBytes[xl-8+extString.length()];
        }

        int f=86;
        for(int i=0; i<payload.length+8; i++){
            String current = "";
            if(i<8){
                current = String.format("%8s", Integer.toBinaryString(extBytesZeros[i] & 0xFF)).replace(' ', '0');
            } else {
                current = String.format("%8s", Integer.toBinaryString(payload[i-8] & 0xFF)).replace(' ', '0');
            }

            for(int j=0; j<8; j++){
                int t = cover[f]%2;
                if ((t*t) == 0 && current.charAt(j) == '1'){
                    steggo[f] = (byte) (steggo[f]+1);
                } else if ((t*t) == 1 && current.charAt(j) == '0'){
                    steggo[f] = (byte) (steggo[f]-1);
                }
                f++;
            }
        }
        f-=150;

        String payloadSize = Integer.toBinaryString(f);
        String white = "";
        for(int h=0; h< 32-payloadSize.length(); h++){
            white = white + "0";
        }
        payloadSize = white + payloadSize;

        int current_bit = 0;
        int coverbit = 54;
        while(current_bit < payloadSize.length()){
            int t = cover[coverbit]%2;
            if ((t*t) == 0 && payloadSize.charAt(current_bit) == '1'){
                steggo[coverbit] = (byte) (steggo[coverbit]+1);
            } else if ((t*t) == 1 && payloadSize.charAt(current_bit) == '0'){
                steggo[coverbit] = (byte) (steggo[coverbit]-1);
            }
            current_bit++;
            coverbit++;
        }
        return steggo;
    }

    public static void writeImage(byte[] steggo){
        try {
            FileOutputStream f = new FileOutputStream("output.bmp");
            f.write(steggo);
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input name of cover image");
        String cover = scanner.nextLine();
        while(!cover.contains(".bmp")){
            System.out.println("Enter valid input");
            cover = scanner.nextLine();
        }
        System.out.println("Input name of payload");
        String payload = scanner.nextLine();
        byte[] ans = hideData(cover.trim(), payload.trim());
        writeImage(ans);
    }
}

