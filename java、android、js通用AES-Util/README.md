## 适用于Android、标准Java以及JavaScript的AES工具（AES-128-CBC加密模式）
>因为js用zeropadding填充而java没有该方式，所以java使用nopadding也就是不填充然后手动实现zeropadding效果

>为了能够简化网络传输中的转码步骤，所以全程加解密没有使用base64而是使用16进制Hex大些字符串

其中Android和Java的用法一致，将*AES_Util_AJS.java*文件复制到工程中，然后使用如下方式加密和解密AES
```java
    private static void testAES() {
        String key = AES_Util_AJS.createKeygen();
        System.out.println("key:" + key);
        String text = "开始在网上找到一份很普通的Java RSA加密算法，直接引入到项目中发现在客户端（Android）加密后，在服务器端（Java）解不开；";
        System.out.println("text:" + text);
        System.out.println(text.length());
        String result = AES_Util_AJS.encrypt(text, key);
        System.out.println("result:" + result);
        result = AES_Util_AJS.decrypt(result, key);
        System.out.println("result:" + result);
        System.out.println(result.length());
    }
```
输出如下
```
key:9xilcgw78swjn1mz
text:开始在网上找到一份很普通的Java RSA加密算法，直接引入到项目中发现在客户端（Android）加密后，在服务器端（Java）解不开；
result:FF107A38F3A2660D7DA40FD63BB9A7F3DF5BD06E158EA15C9431B3929A596749E804F81F0EFE5F836E6C32D5EC4CA4E3EE23072C26C07570ECEB24569305A90681422B77027F1BE7C1BB01A4E541A691322A90F49CF4D6267E5E69D81E041014C3817DF470C8C2442EE3EC81620491C71D80A792E66438EF58EA6B00CD28605985E7CBE562B9B1B61F3293955E9B2F22090570CC48E9C61B0BB1DEB71BB90719ABF4FA2A6E878825F7DC498353E19FE5
result:开始在网上找到一份很普通的Java RSA加密算法，直接引入到项目中发现在客户端（Android）加密后，在服务器端（Java）解不开；
```

注意：加密内容首尾不能有空格等分隔符

js的用法如下：将aes-util-ajs.js或aes-util-ajs-min.js文件添加到工程中并引用，然后用如下方式加密和解密AES
```javascript
	<script>
		var strKey = aesUtil.createKeygen();
		console.info(strKey);
		var text = '开始在网上找到一份很普通的Java RSA加密算法';
		console.info(text);
		var result = aesUtil.encrypt(text, strKey);
		console.info(result);
		result = aesUtil.decrypt(result, strKey);
		console.info(result);
	</script>

```
使用该工具生成的加密串可以在这三个平台之间互转