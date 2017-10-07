import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

/**
 * Created by agustin on 07/10/17.
 */
public class SHA256 implements HashFunction {


    public String hashData(String data) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            return bytesToHex(hash); // make it printable
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private String bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
}