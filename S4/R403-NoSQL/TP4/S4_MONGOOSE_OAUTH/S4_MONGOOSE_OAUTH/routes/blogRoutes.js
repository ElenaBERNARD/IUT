const requireLogin = require('../middlewares/requireLogin');
const mongoose = require('mongoose');
const Blog = mongoose.model('Blog');
const redis = require('redis');
module.exports = app => {
  app.get('/api/blogs/:id', requireLogin, async (req, res) => {
    const blog = await Blog.findOne({
      _user: req.user.id,
      _id: req.params.id
    });
    res.send(blog);
  });

  app.get('/api/blogs', requireLogin, async (req, res) => {
    const redisUrl = 'redis://127.0.0.1:6379';
    const client = redis.createClient({ url: redisUrl });
    client.on('error', err => console.error(err));
    await client.connect();

    const cachedBlogs = await client.get(req.user.id);
    if (cachedBlogs) {
      console.log('SERVING FROM CACHE');
      return res.send(JSON.parse(cachedBlogs));
    }

    console.log('SERVING FROM MONGODB');
    const blogs = await Blog.find({ _user: req.user.id });
    await client.set(req.user.id, JSON.stringify(blogs));
    await client.disconnect();

    res.send(blogs);
  });

  app.post('/api/blogs', requireLogin, async (req, res) => {
    const redisUrl = 'redis://127.0.0.1:6379';
    const client = redis.createClient({ url: redisUrl });
    client.on('error', err => console.error(err));
    await client.connect();
    const { title, content } = req.body;
    const blog = new Blog({ title, content, _user: req.user.id });
    try {
      await blog.save();
      await client.del(req.user.id);
      await client.disconnect();
      res.send(blog);
    } catch (err) {
      res.send(400, err);
    }
  });

  app.delete('/api/blogs/:id', requireLogin, async (req, res) => {
    try {
      const blog = await Blog.deleteOne({
        _user: req.user.id,
        _id: req.params.id
      });
      const blogs = await Blog.find({ _user: req.user.id });
      res.send(blogs);
    } catch (err) {
      res.status(500).send({ error: 'Failed to delete blog' });
    }
  });
};