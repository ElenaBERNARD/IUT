const fs=require('fs');
const chalk=require('chalk');
const { loadEnvFile } = require('process');

const addNote = (titles, body) => {
    const notes = loadNotes()
    const duplicateNote = notes.find((note) => note.title === title)
    if(!duplicateNote) {
        note.push({
            title: title,
            body: body
        })
        saveNotes(notes)
        console.log(chalk.green.inverse('New note added !'))
    }
    else {
        console.log(chalk.red.inverse('Note title taken !'))
    }
}

const removeNtote = (title) => {
    const notes = loadNotes()
    const notesToKeep = notes.filter((note) => note.title !== title)
    if(notes.length > notesToKeep.length) {
        console.log(chalk.green.inverse('Note removed !'))
        saveNotes(notesToKeep)
    }
    else {
        console.log(chalk.red.inverse('No note found !'))
    }
}

const saveNotes = (notes) => {
    const dataJSON = JSON.stringify(notes)
    fs.writeFileSync('notes.json', dataJSON)
}