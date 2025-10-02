const socket = io();
const form = document.getElementById('form');
const input = document.getElementById('input');
const messages = document.getElementById('messages');
const modelSelector = document.getElementById('model-selector'); // Model selector

let currentUserMessageElement = null;
let currentAIMessageElement = null;

form.addEventListener('submit', (e) => {
    e.preventDefault();
    if (input.value) {
        // Get the selected model from the dropdown
        const selectedModel = modelSelector.value;

        // Create a new message element for the user's message
        currentUserMessageElement = document.createElement('li');
        currentUserMessageElement.textContent = `You: ${input.value}`;
        messages.appendChild(currentUserMessageElement);

        // Emit the user's message along with the selected model
        socket.emit('chat message', { message: input.value, model: selectedModel });
        input.value = '';

        // Create a new message element for the AI's response (this will be updated later)
        currentAIMessageElement = document.createElement('li');
        currentAIMessageElement.textContent = ``;
        messages.appendChild(currentAIMessageElement);
    }
});

// Listen for the AI's streaming response and update the message dynamically
socket.on('chat message', (msg) => {
    if (currentAIMessageElement) {
        // Append the incoming data chunk to the current AI message
        currentAIMessageElement.innerHTML = `${marked.parse(msg)}`;

        // Scroll to the bottom of the page to show the new updates
        window.scrollTo(0, document.body.scrollHeight);
    }
});