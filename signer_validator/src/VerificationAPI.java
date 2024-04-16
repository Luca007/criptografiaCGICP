import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class VerificationAPI {

    public static PublicKey loadPublicKey(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        String publicKeyBase64 = new String(keyBytes);
        byte[] decodedKey = Base64.getDecoder().decode(publicKeyBase64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    public static boolean verifySignature(byte[] data, byte[] signature, PublicKey publicKey) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(data);
        return sig.verify(signature);
    }

    public static void main(String[] args) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select the original file to verify");
            int result = fileChooser.showOpenDialog(null);
            if (result != JFileChooser.APPROVE_OPTION) {
                System.out.println("No file selected.");
                return;
            }
            File selectedFile = fileChooser.getSelectedFile();
            FileInputStream fis = new FileInputStream(selectedFile);
            byte[] dataBytes = fis.readAllBytes();
            fis.close();

            JFileChooser sigChooser = new JFileChooser();
            sigChooser.setDialogTitle("Select the signature file");
            int sigResult = sigChooser.showOpenDialog(null);
            if (sigResult != JFileChooser.APPROVE_OPTION) {
                System.out.println("No signature file selected.");
                return;
            }
            File sigFile = sigChooser.getSelectedFile();
            byte[] signatureBytes = Files.readAllBytes(sigFile.toPath());

            PublicKey publicKey = loadPublicKey("publicKey.txt");

            boolean isVerified = verifySignature(dataBytes, signatureBytes, publicKey);
            System.out.println("Verification status: " + isVerified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
