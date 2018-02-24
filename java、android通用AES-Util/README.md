## 适用于Android、标准Java的AES工具（AES/CBC/PKCS5Padding）

>只有一个文件，复制到工程中然后使用即可
```java
        String key = AES_Util_AJ.createKeygen();
        System.out.println("key:" + key);
        String text = "开始在网上找到一份很普通的Java RSA加密算法";
        System.out.println("text:" + text);
        String result = AES_Util_AJ.encrypt(text, key);
        System.out.println("result:" + result);
        result = AES_Util_AJ.decrypt(result, key);
        System.out.println("result:" + result);
```