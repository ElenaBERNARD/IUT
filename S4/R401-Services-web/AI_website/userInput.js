document.addEventListener("DOMContentLoaded", function () {
    const socket = io();
    const form = document.getElementById("form");
    const input = document.getElementById("input");
    const messages = document.getElementById("messages");
    const modelSelector = document.getElementById("model-selector");

    form.addEventListener("submit", function (e) {
        e.preventDefault();
        if (input.value) {
            const message = input.value;
            const model = modelSelector.value;

            // Append user message
            appendMessage(message, "user");
            socket.emit("chat message", { message, model });

            input.value = "";
        }
    });

    socket.on("ai new_message", function () {
        appendMessage("...", "ai");
    });

    socket.on("ai message", function (msg) {
        updateAIMessage(msg);
    });

    function appendMessage(text, sender) {
        const li = document.createElement("li");
        li.classList.add(sender);

        if (sender === "ai") {
            li.innerHTML = marked.parse(text, { sanitize: true });
        } else {
            li.textContent = text;
        }

        messages.appendChild(li);
        messages.scrollTop = messages.scrollHeight;
    }

    function updateAIMessage(text) {
        let lastAIMessage = messages.querySelector("li.ai:last-child");
        // Update existing AI message
        lastAIMessage.innerHTML = marked.parse(text, { sanitize: true });

        // Apply syntax highlighting
        lastAIMessage.querySelectorAll("pre code").forEach((block) => {
            hljs.highlightElement(block);
        });

        // Handle <think> styling for all instances
        lastAIMessage.querySelectorAll("think").forEach((thinkBlock) => {
            thinkBlock.classList.add("thinking");
        });

        messages.scrollTop = messages.scrollHeight; // Auto-scroll
    }
});