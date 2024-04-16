import javax.swing.*;
import java.io.*;
import java.security.*;
import java.util.Base64;

public class SignatureAPI {

    public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // Tamanho recomendado para RSA
        return keyGen.generateKeyPair();
    }

    public static byte[] signData(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    public static void savePublicKey(PublicKey publicKey, String filename) throws IOException {
        byte[] keyBytes = publicKey.getEncoded();
        String publicKeyBase64 = Base64.getEncoder().encodeToString(keyBytes);
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(publicKeyBase64.getBytes());
        }
    }

    public static void main(String[] args) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a file to sign");
            int result = fileChooser.showOpenDialog(null);
            if (result != JFileChooser.APPROVE_OPTION) {
                System.out.println("No file selected.");
                return;
            }
            File selectedFile = fileChooser.getSelectedFile();
            FileInputStream fis = new FileInputStream(selectedFile);
            byte[] dataBytes = fis.readAllBytes();
            fis.close();

            KeyPair keyPair = generateRSAKeyPair();
            byte[] signedData = signData(dataBytes, keyPair.getPrivate());

            try (FileOutputStream fos = new FileOutputStream(selectedFile.getName() + ".sig")) {
                fos.write(signedData);
            }

            savePublicKey(keyPair.getPublic(), "publicKey.txt");

            System.out.println("File signed successfully, and public key saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
