const express = require('express');
const { createServer } = require('node:http');
const { join } = require('node:path');
const { Server } = require('socket.io');
const axios = require('axios');

const app = express();
const server = createServer(app);
const io = new Server(server);

app.use(express.json());
app.use(express.static(__dirname)); // Serve static files

app.get('/', (req, res) => {
    res.sendFile(join(__dirname, 'index.html'));
});

const LOCAL_AI_URL = "http://localhost:11434/api/generate";

const models = {
    'deepscaler' : 'DeepScaler',
    'deepseek-r1:1.5b' : 'DeepSeek R1',
    'llama3.2' : 'Llama 3.2',
    'deepseek-coder' : 'DeepSeek Coder',
}

io.on('connection', (socket) => {
    console.log('A user connected');

    socket.on('chat message', async ({ message, model }) => {  // Accept message and model
        console.log('Message received:', message, 'with model:', models[model]);

        try {
            const response = await axios.post(LOCAL_AI_URL, {
                model: model,  // Use the selected model
                prompt: message,
                stream: true
            }, {
                headers: { "Content-Type": "application/json" },
                responseType: 'stream' // Enable streaming response
            });

            let aiResponse = "" + models[model] + ": ";

            response.data.on('data', (chunk) => {
                const lines = chunk.toString().split("\n").filter(line => line.trim() !== "");

                for (const line of lines) {
                    try {
                        const json = JSON.parse(line);
                        if (json.response) {
                            aiResponse += json.response; // Append to the response
                            socket.emit('chat message', aiResponse); // Send updated message
                        }
                    } catch (error) {
                        console.error("Error parsing AI response:", error);
                    }
                }
            });


            response.data.on('end', () => {
                console.log("AI response complete.");
            });

        } catch (error) {
            console.error("Error communicating with AI:", error);
            socket.emit('chat message', "Error: Unable to get AI response.");
        }
    });

    socket.on('disconnect', () => {
        console.log('User disconnected');
    });
});

server.listen(3000, () => {
    console.log('Server running at http://localhost:3000');
});
