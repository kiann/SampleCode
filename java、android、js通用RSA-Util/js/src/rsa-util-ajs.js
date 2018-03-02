var b64map = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
var b64pad = "=";
var BI_RM = "0123456789abcdefghijklmnopqrstuvwxyz";
var MING_LEN = 117;
var MI_LEN = 128;

function stringToByte(str) {
	var bytes = new Array();
	var len, c;
	len = str.length;
	for(var i = 0; i < len; i++) {
		c = str.charCodeAt(i);
		if(c >= 0x010000 && c <= 0x10FFFF) {
			bytes.push(((c >> 18) & 0x07) | 0xF0);
			bytes.push(((c >> 12) & 0x3F) | 0x80);
			bytes.push(((c >> 6) & 0x3F) | 0x80);
			bytes.push((c & 0x3F) | 0x80);
		} else if(c >= 0x000800 && c <= 0x00FFFF) {
			bytes.push(((c >> 12) & 0x0F) | 0xE0);
			bytes.push(((c >> 6) & 0x3F) | 0x80);
			bytes.push((c & 0x3F) | 0x80);
		} else if(c >= 0x000080 && c <= 0x0007FF) {
			bytes.push(((c >> 6) & 0x1F) | 0xC0);
			bytes.push((c & 0x3F) | 0x80);
		} else {
			bytes.push(c & 0xFF);
		}
	}
	return bytes;

}

function charToByte(ch) {
	var bytes = new Array();
	var c = ('' + ch).charCodeAt(0);
	if(c >= 0x010000 && c <= 0x10FFFF) {
		bytes.push(((c >> 18) & 0x07) | 0xF0);
		bytes.push(((c >> 12) & 0x3F) | 0x80);
		bytes.push(((c >> 6) & 0x3F) | 0x80);
		bytes.push((c & 0x3F) | 0x80);
	} else if(c >= 0x000800 && c <= 0x00FFFF) {
		bytes.push(((c >> 12) & 0x0F) | 0xE0);
		bytes.push(((c >> 6) & 0x3F) | 0x80);
		bytes.push((c & 0x3F) | 0x80);
	} else if(c >= 0x000080 && c <= 0x0007FF) {
		bytes.push(((c >> 6) & 0x1F) | 0xC0);
		bytes.push((c & 0x3F) | 0x80);
	} else {
		bytes.push(c & 0xFF);
	}
	return bytes;
}

function byteToString(arr) {
	if(typeof arr === 'string') {
		return arr;
	}
	var str = '',
		_arr = arr;
	for(var i = 0; i < _arr.length; i++) {
		var one = _arr[i].toString(2),
			v = one.match(/^1+?(?=0)/);
		if(v && one.length == 8) {
			var bytesLength = v[0].length;
			var store = _arr[i].toString(2).slice(7 - bytesLength);
			for(var st = 1; st < bytesLength; st++) {
				store += _arr[st + i].toString(2).slice(2);
			}
			str += String.fromCharCode(parseInt(store, 2));
			i += bytesLength - 1;
		} else {
			str += String.fromCharCode(_arr[i]);
		}
	}
	return str;
}

function hex2b64(d) {
	var b;
	var e;
	var a = "";
	for(b = 0; b + 3 <= d.length; b += 3) {
		e = parseInt(d.substring(b, b + 3), 16);
		a += b64map.charAt(e >> 6) + b64map.charAt(e & 63)
	}
	if(b + 1 == d.length) {
		e = parseInt(d.substring(b, b + 1), 16);
		a += b64map.charAt(e << 2)
	} else {
		if(b + 2 == d.length) {
			e = parseInt(d.substring(b, b + 2), 16);
			a += b64map.charAt(e >> 2) + b64map.charAt((e & 3) << 4)
		}
	}
	while((a.length & 3) > 0) {
		a += b64pad
	}
	return a
}

function b64tohex(e) {
	var c = "";
	var d;
	var a = 0;
	var b;
	for(d = 0; d < e.length; ++d) {
		if(e.charAt(d) == b64pad) {
			break
		}
		v = b64map.indexOf(e.charAt(d));
		if(v < 0) {
			continue
		}
		if(a == 0) {
			c += int2char(v >> 2);
			b = v & 3;
			a = 1
		} else {
			if(a == 1) {
				c += int2char((b << 2) | (v >> 4));
				b = v & 15;
				a = 2
			} else {
				if(a == 2) {
					c += int2char(b);
					c += int2char(v >> 2);
					b = v & 3;
					a = 3
				} else {
					c += int2char((b << 2) | (v >> 4));
					c += int2char(v & 15);
					a = 0
				}
			}
		}
	}
	if(a == 1) {
		c += int2char(b << 2)
	}
	return c
}

function int2char(a) {
	return BI_RM.charAt(a)
}
var rsaUtil = {
	encrypt: function(content, publicKey) {
		var result = '';
		var crypt = new JSEncrypt();
		publicKey = hex2b64(publicKey);
		crypt.setPublicKey(publicKey);

		var charCountAll = 0;
		var chars = '';
		var charBytesCount = 0;

		var bytes;
		var temp;
		while(charCountAll < content.length) {
			bytes = charToByte(content.charAt(charCountAll));
			if(charBytesCount + bytes.length > MING_LEN) {
				temp = crypt.encrypt(chars);
				temp = b64tohex(temp).toUpperCase();
				result += temp;
				charBytesCount = 0;
				chars='';
			} else if(charBytesCount + bytes.length === MING_LEN) {
				chars += content.charAt(charCountAll);
				charCountAll++;
				temp = crypt.encrypt(chars);
				temp = b64tohex(temp).toUpperCase();
				result += temp;
				charBytesCount = 0;
				chars='';
			} else {
				chars += content.charAt(charCountAll);
				charCountAll++;
				charBytesCount += bytes.length;
				if(charCountAll===content.length){
				temp = crypt.encrypt(chars);
				temp = b64tohex(temp).toUpperCase();
				result += temp;					
				}
			}
		}
		return result;
	},
	decrypt: function(content, privateKey) {

		var crypt = new JSEncrypt();
		privateKey = hex2b64(privateKey);
		crypt.setPrivateKey(privateKey);
		var count = content.length / 256;
		var result = '';
		var split;
		for(var i = 0; i < count; i++) {
			if(i < count - 1) {
				split = content.slice(i * 256, (i + 1) * 256);
			} else {
				split = content.slice(i * 256);
			}
			split = hex2b64(split);
			split = crypt.decrypt(split);
			result += split;
		}
		return result;
	}
};