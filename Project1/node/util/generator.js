"use strict";

const fs_p = require('fs/promises');
const fs = require('fs');
const config = JSON.parse(fs.readFileSync('./config.json'));

let namePool = [];
let categoryPool = [];
let titlePool = [];
let contentPool = [];

let poolPromises = [genPool(namePool, config['Post Amount']),
    genPool(categoryPool, config['Category Amount']),
    genPool(titlePool, config['User Amount']),
    genPool(contentPool, config['Post Amount']),
];

async function genPool(ary, count) {
    let pow = 1 + Math.log10(count);
    let tmp = Math.pow(10, pow + 1);
    return new Promise((resolve, reject) => {
        for (let i = 0; i < count; ++i) {
            ary.push(tmp.toString() + ary.toString());
            ++tmp;
        }
        resolve();
    });
}
