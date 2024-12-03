const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const PORT = 3000;

app.use(bodyParser.json());

let todos = [];
let currentId = 1;

app.get('/api/todos', (req, res) => {
    res.json(todos);
});

app.get('/api/todos/:id', (req, res) => {
    const todo = todos.find(t => t.id === parseInt(req.params.id));
    if (!todo) return res.status(404).send('Teendő nem található');
    res.json(todo);
});

app.post('/api/todos', (req, res) => {
    const todo = {
        id: currentId++,
        description: req.body.description,
        done: false
    };
    todos.push(todo);
    res.status(201).json(todo);
});

app.put('/api/todos/:id', (req, res) => {
    const todo = todos.find(t => t.id === parseInt(req.params.id));
    if (!todo) return res.status(404).send('Teendő nem található');

    if (req.body.description !== undefined) todo.description = req.body.description;
    if (req.body.done !== undefined) todo.done = req.body.done;

    res.json(todo);
});

app.delete('/api/todos/:id', (req, res) => {
    const todoIndex = todos.findIndex(t => t.id === parseInt(req.params.id));
    if (todoIndex === -1) return res.status(404).send('Teendő nem található');

    todos.splice(todoIndex, 1);
    res.status(204).send();
});

app.listen(PORT, () => {
    console.log(`Szerver fut a http://localhost:${PORT} címen`);
});