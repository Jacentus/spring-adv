const socket = new WebSocket("ws://localhost:8081/ratings");
const ratingSpan = document.querySelector("#rating-value");
const messageInput = document.querySelector("#message");
const sendBtn = document.querySelector("#send-btn");

socket.onopen = () => console.log("Connected");

socket.onclose = () => console.log("Disconnected");

socket.onerror = (error) => console.log("Error: " + error);

socket.onmessage = (message) => ratingSpan.innerText = message.data;

sendBtn.addEventListener('click', () => {
    const message = messageInput.value;
    socket.send(message);
});

