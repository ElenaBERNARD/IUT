const { MongoClient } = require('mongodb');
const uri = "mongodb://127.0.0.1:27017"
const nomBdd = "td_s4"
async function getBooks() {
    const client = new MongoClient(uri)
    try {
        await client.connect()
        const database = client.db(nomBdd)
        const booksCollection = database.collection('books')
        const books = await booksCollection.find({ 
            status: {$in:["PUBLISH", "AVAILABLE"]} 
        }).toArray()
        console.log(books)
        console.log("books")
        // return await books.find().toArray()
    } catch (err) {
        console.error(err)
    } finally {
        await client.close()
    }
}

getBooks()