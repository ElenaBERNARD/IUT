const fs = require("fs");

const loadNotes = () => {
    fs.readFile("notes.jspn", 'utf8', (err, data)=> {
        if (err){
            return [];
        }
        const dataStr = data.toString();
        console.log("OK");
        return JSON.parse(data.str);
    })
}