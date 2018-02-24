## 适用于Android、标准Java以及JavaScript的AES工具（AES-128-CBC加密模式）
>因为js用zeropadding补码而java没有该方式，所以java使用nopadding然后手动实现

其中Android和Java的用法一致，将*AES_Util_AJS.java*文件和base64文件夹复制到工程中，然后使用如下方式加密和解密AES
```java
        String key = AES_Util_AJS.createKeygen();
        System.out.println("key:" + key);
        String text = "开始在网上找到一份很普通的Java";
        System.out.println("text:" + text);
        String result = AES_Util_AJS.encrypt(text, key);
        System.out.println("result:" + result);
        String result2 = AES_Util_AJS.decrypt(result, key);
        System.out.println("result2:" + result2);
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