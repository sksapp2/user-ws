package com.infc.ms.user.service;

import com.infc.ms.user.enums.KeysStoreEnum;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface MessageSecurity {

    Object generatePublicAndPrivateKey(KeysStoreEnum keysStoreEnum) throws NoSuchAlgorithmException, IOException;
}
