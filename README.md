# Hippo
Simple javascript bot constructor for vk (in future for all messengers)

# What can hippo do
![Imgur](https://i.imgur.com/gWu5Mw0.jpg)
I think that these 3 operations are enough to write any bot logic.

# Example
```javascript
var hippo = Java.type('com.oybek.hippo.lake.MegaHippo');

function sayhello(message) {
	if (message.body == "hello") {
		message.body = "hello bro!";
	} else {
		message.body = "I only receive 'hello'";
	}
	hippo.send(message);
}

// called when you receive new message
function think(message) {
	sayhello(message);
}
```
