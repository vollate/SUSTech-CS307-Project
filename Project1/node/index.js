"use strict";

const fs_p = require('fs/promises');
const fs = require('fs');
const dbInserter = require('./db/Inserter.js');
const JSON_DIR = 'data/';

let fileReadList = [];
let insertionList = [];

const clientInfo = JSON.parse(fs.readFileSync('user-info.json'));
const dir_files = fs.readdirSync(JSON_DIR, {withFileTypes: true});
dbInserter.init(clientInfo);
dbInserter.openConnection();

for (let file of dir_files) {
    if (file.name.match('posts') !== null) {
        fileReadList.push(fs_p.readFile(JSON_DIR + file.name)
            .then((raw) => {
                insertionList.push(dbInserter.insert('posts', JSON.parse(raw)));
            })
        );
    } else if (file.name.match('replies') !== null) {
        fileReadList.push(fs_p.readFile(JSON_DIR + file.name)
            .then((raw) => {
                insertionList.push(dbInserter.insert('replies', JSON.parse(raw)));
            })
        );
    } else {
        console.warn('unknown file name: ' + file);
    }
}

Promise.allSettled(fileReadList)
    .then(() => {
        Promise.allSettled(insertionList).then
        (() => {
            dbInserter.closeConnection();
            process.exit();
        })
    })
    .catch((e) => {
        console.error(e);
        dbInserter.closeConnection();
    });

