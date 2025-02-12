const redis = require('redis');
const redisUrl = 'redis://127.0.0.1:6379';

async function main() {
    const client = redis.createClient(redisUrl);
    client.on("error", err => console.error("Erreur de cnx", err));
    await client.connect();
    console.log("Conncete a redis");

    // await client.set("R403", 'NoSQL');
    // await client.set('R401', 'Services Web');
    let r403 = await client.get('R403');
    console.log('R403: ', r403);

    const customers = {
        joseph: {
            products: 'Produit A',
            blogs: 'Blog B'
        },
        karine: {
            products: 'Produit C',
            blogs: 'Blog D'
        }
    }
    await client.HSET('joseph', 'products', 'Produit A');
    await client.HSET('joseph', 'blogs', 'Blog B');
    let valeur = await client.HGET('joseph', 'products');
    console.log('Valeur: ', valeur);
    let valeurs = await client.HGETALL('joseph');
    for (let key in valeurs) {
        console.log('Key: ', key, 'Valeur: ', valeurs[key]);
    }

    await client.set("courses", JSON.stringify({R403:'NOSQL'}), 'EX', 10);
    let courses = await client.get("courses");
    courses = JSON.parse(courses);

    await client.flushAll();

    await client.disconnect();
}

main().then(r => console.log("OK")).catch(e => console.error(e))