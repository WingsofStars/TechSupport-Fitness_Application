package delware.apps.techsupport_scampermobile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

//    public static String getSha256Hash(String password){
//        try {
//            MessageDigest digest = null;
//            try {
//                digest = MessageDigest.getInstance("SHA-256");
//            } catch (NoSuchAlgorithmException e1) {
//                e1.printStackTrace();
//            }
//            digest.reset();
//            return bin2hex(digest.digest(password.getBytes()));
//        } catch (Exception ignored) {
//            System.out.println("OH NO");
//            return null;
//        }
//    }
//
//    public static String bin2hex(byte[] data){
//        StringBuilder hex = new StringBuilder(data.length * 2);
//        for (byte b : data)
//            hex.append(String.format("%02", b & 0xFF));
//        return hex.toString();
//    }
}