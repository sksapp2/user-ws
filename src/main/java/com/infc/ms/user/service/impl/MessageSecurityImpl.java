package com.infc.ms.user.service.impl;

import com.infc.ms.user.enums.KeysStoreEnum;
import com.infc.ms.user.service.MessageSecurity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class MessageSecurityImpl implements MessageSecurity {
    @Override
    public String generatePublicAndPrivateKey(KeysStoreEnum keysStoreEnum) throws NoSuchAlgorithmException, IOException {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair keyPair = kpg.generateKeyPair();
        Key pub = keyPair.getPublic();
        System.out.println("Public key format: " + pub.getFormat());

        Key pvt = keyPair.getPrivate();
        System.out.println("Private key format: " + pvt.getFormat());

        if(keysStoreEnum.getKeyStore().equals(KeysStoreEnum.FILE_SYSTEM)){
            generatePublicKeyAndPrivateKeyFileSystem(keyPair, "/Users/Khanh/Documents/huongdanjava");
        }
        return null;
    }

    private static void generatePublicKeyAndPrivateKeyFileSystem(KeyPair keypair, String outputFileWithoutExtension)
            throws IOException {
        OutputStream out = new FileOutputStream(outputFileWithoutExtension + ".key");
        out.write(keypair.getPrivate().getEncoded());
        out.close();

        out = new FileOutputStream(outputFileWithoutExtension + ".pub");
        out.write(keypair.getPublic().getEncoded());
        out.close();
    }
}
