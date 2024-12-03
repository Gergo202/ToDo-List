import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        {duration: '10s', target: 10},
        {duration: '20s', target: 50},
        {duration: '10s', target: 0},
    ],
};

const BASE_URL = 'http://localhost:8080/api/todos';

export default function () {
    const createPayload = JSON.stringify({
        description: `Teszt teendő - ${__VU}-${__ITER}`,
        done: false,
    });

    const createResponse = http.post(BASE_URL, createPayload, {
        headers: { 'Content-Type': 'application/json' },
    });

    check(createResponse, {
        'Teendő létrehozva (201)': (res) => res.status === 201,
    });

    const createdTodo = createResponse.json();
    const todoId = createdTodo.id;

    const getResponse = http.get(BASE_URL);
    check(getResponse, {
        'Teendők sikeresen lekérve (200)': (res) => res.status === 200,
    });

    const updatePayload = JSON.stringify({
        description: `Frissített teszt teendő - ${__VU}-${__ITER}`,
        done: true,
    });

    const updateResponse = http.put(`${BASE_URL}/${todoId}`, updatePayload, {
        headers: { 'Content-Type': 'application/json' },
    });

    check(updateResponse, {
        'Teendő frissítve (200)': (res) => res.status === 200,
    });

    const deleteResponse = http.del(`${BASE_URL}/${todoId}`);
    check(deleteResponse, {
        'Teendő törölve (204)': (res) => res.status === 204,
    });

    sleep(1);
}