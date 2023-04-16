"use strict";

const {Client} = require('pg');
const insertion = {
    User: 'insert into data.users(name,user_id, registration_time, phone) values ($1,$2,$3,$4)',
    Post: 'insert into data.posts(post_id, title, posting_time, author_name, city, country, content) values ($1,$2,$3,$4,$5,$6,$7)',
    Reply: "insert into data.replies(reply_id, post_id, stars, content, author_name)values(nextval('id_sequence.reply_seq')::numeric,$1,$2,$3,$4)",
    SecondaryReply: "insert into data.secondary_replies(secondary_reply_id, reply_id, stars, content, author_name)values(nextval('id_sequence.secondary_reply_seq'):: numeric, (select data.replies.reply_id from data.replies where author_name = $1 and content = $2), $3, $4, $5)",
    Category: 'insert into data.categories(category, post_id) values ($1,$2)',
    Follow: 'insert into relation.follow_relation(followee_name, follower_name)values($1,$2)',
    Favorite: 'insert into relation.favorite_relation(post_id, user_name)values($1,$2)',
    Like: 'insert into relation.like_relation(post_id, user_name)valueqs($1,$2)',
    Share: 'insert into relation.share_relation(post_id, user_name)values($1,$2)',
    parse: function (type, json) {
        switch (type) {
            case 'User':
                return [json['Author'], json['Author\'s ID'], json['Author Registration Time'], json['Author\'s Phone']];
            case 'Post':
                return [json['Post ID'], json['Title'], json['Posting Time'], json['Author'], json['Posting City'], json[''], json['Content']];
            case 'Reply':
                return [json['Post ID'], json['Reply Stars'], json['Reply Content'], json['Reply Author']];
            case 'SecondaryReply':
                return [json['Reply Author'], json['Reply Content'], json['Secondary Reply Stars'], json['Secondary Reply Content'], json['Secondary Reply Author']];
            default:
                throw ('unknown request');
        }
    },
    insertArray: function (type, obj) {
        switch (type) {
            case 'Category':
                return new Promise((resolve, reject) => {
                    let allPromise = [];
                    for (let each of obj['Category']) {
                        allPromise.push(client.query(this.Category, [each, obj['Post ID']]));
                    }
                    Promise.all(allPromise)
                        .then(() => resolve('succeed'))
                        .catch((e) => console.error(e))
                });
            case 'Follow':
                return new Promise((resolve, reject) => {
                    let allPromise = [];
                    for (let each of obj['Authors Followed By']) {
                        allPromise.push(client.query(this.Follow, [obj['Author'], each]));
                    }
                    Promise.all(allPromise)
                        .then(() => resolve('succeed'))
                        .catch((e) => console.error(e))
                });
            case 'Favorite':
                return new Promise((resolve, reject) => {
                    let allPromise = [];
                    for (let each of obj['Authors Who Favorited the Post']) {
                        allPromise.push(client.query(this.Favorite, [obj['Post ID'], each]));
                    }
                    Promise.all(allPromise)
                        .then(() => resolve('succeed'))
                        .catch((e) => console.error(e))
                });
            case 'Like':
                return new Promise((resolve, reject) => {
                    let allPromise = [];
                    for (let each of obj['Authors Who Liked the Post']) {
                        allPromise.push(client.query(this.Favorite, [obj['Post ID'], each]));
                    }
                    Promise.all(allPromise)
                        .then(() => resolve('succeed'))
                        .catch((e) => console.error(e))
                });
            case 'Share':
                return new Promise((resolve, reject) => {
                    let allPromise = [];
                    for (let each of obj['Authors Who Shared the Post']) {
                        allPromise.push(client.query(this.Favorite, [obj['Post ID'], each]));
                    }
                    Promise.all(allPromise)
                        .then(() => resolve('succeed'))
                        .catch((e) => console.error(e))
                });
            default:
                throw ('wrong type');
        }
    }
}

let client;

async function insertPost(obj) {
    let promiseList = [];
    for (let post of obj) {
        await client.query(insertion.User, insertion.parse('User', post));
        await client.query(insertion.Post, insertion.parse('Post', post));
        promiseList.push(insertion.insertArray('Category', post));
        promiseList.push(insertion.insertArray('Follow', post));
        promiseList.push(insertion.insertArray('Favorite', post));
        promiseList.push(insertion.insertArray('Like', post));
        promiseList.push(insertion.insertArray('Share', post));
    }
    return new Promise((resolve, reject) => {
        Promise.all(promiseList)
            .then(() => resolve('succeed'))
            .catch((e) => console.error(e))
    })
}

async function insertReply(obj) {
    let promiseList = [];
    for (let reply of obj) {
        promiseList.push(client.query(insertion.Reply, insertion.parse('Reply', reply)));
        promiseList.push(client.query(insertion.SecondaryReply, insertion.parse('SecondaryReply', reply)));
    }
    return new Promise((resolve, reject) => {
        Promise.all(promiseList)
            .then(() => resolve('succeed'))
            .catch((e) => console.error(e))
    })
}

module.exports = {
    init: function (clientInfo) {
        client = new Client(clientInfo);
    },
    openConnection: function () {
        client.connect()
            .catch(() => console.error('failed to connect'));
    },
    closeConnection: function () {
        client.end();
    },
    insert: async function (name, jsonData) {
        switch (name) {
            case 'posts':
                return insertPost(jsonData);
            case 'replies':
                return insertReply(jsonData);
            default:
                throw ('unknown file name');
        }
    }
}

