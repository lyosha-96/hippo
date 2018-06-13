# Hippo
Simple javascript bot constructor for vk (in future for all messengers)

# What can hippo do
![Imgur](https://i.imgur.com/gWu5Mw0.jpg)
I think that these 3 operations are enough to write any bot logic.

# Example
```javascript
function sayhello(message) {
  if (message.text == "hello") {
    send("hello bro!");
  }
}

function think(message) {
  sayhello(message);
}
```
