var aesUtil = {
	createKeygen: function() {
		var charPool = ['a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
		];
		var result = "";
		for(var i = 0; i < 16; i++) {
			var j = parseInt(Math.random() * 36);
			result += charPool[j];
		}
		return result;
	},
	encrypt: function(content, key) {
		var k = CryptoJS.enc.Latin1.parse(key);
		var iv = CryptoJS.enc.Latin1.parse('0123456789012345');
		return CryptoJS.AES.encrypt(content, k, {
			iv: iv,
			mode: CryptoJS.mode.CBC,
			padding: CryptoJS.pad.ZeroPadding
		}).toString();
	},
	decrypt: function(content, key) {
		var k = CryptoJS.enc.Latin1.parse(key);
		var iv = CryptoJS.enc.Latin1.parse('0123456789012345');
		var bytes = CryptoJS.AES.decrypt(content, k, {
			iv: iv,
			mode: CryptoJS.mode.CBC,
			padding: CryptoJS.pad.ZeroPadding
		});
		return bytes.toString(CryptoJS.enc.Utf8);
	}
};

