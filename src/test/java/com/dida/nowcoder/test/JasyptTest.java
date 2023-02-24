package com.dida.nowcoder.test;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void test() {
        String user = this.stringEncryptor.encrypt("");
        System.out.println(user);

        String  password = this.stringEncryptor.encrypt("");
        System.out.println(password);
    }
    // DyYxvyNFXrq4kcX/lhsOX3SXYBYV0DWu
    // lYLpLf3WEobBUJsmUFv79jVVXDwjJs+z
}
