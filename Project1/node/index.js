"use strict";

const fs_p = require('fs/promises');
const fs = require('fs');
const dbInserter = require('./db/Inserter.js');

let postContent, replyContent;

console.time('total execution time');
const clientInfo = JSON.parse(fs.readFileSync('user-info.json'));

dbInserter.initPool(clientInfo);
dbInserter.openConnection();

postContent = fs.readFileSync('data/posts.json');
replyContent = fs.readFileSync('data/replies.json');

dbInserter.insert('posts', JSON.parse(postContent)).then(() => {
    dbInserter.insert('replies', JSON.parse(replyContent)).then(() => {
        dbInserter.closeConnection();
        console.timeEnd('total execution time');
        process.exit();
    })
})
