"use strict";

const {Client} = require('pg');
const {Pool} = require('pg');
const maxPromise = Math.pow(2, 19);

const insertion = {
    User: 'insert into data.users(name,user_id, registration_time, phone) values ($1,$2,$3,$4)',
    Post: 'insert into data.posts(post_id, title, posting_time, author_name, city, country, content) values ($1,$2,$3,$4,$5,$6,$7)',
    Reply: "insert into data.replies(reply_id, post_id, stars, content, author_name)values(nextval('id_sequence.reply_seq')::numeric,$1,$2,$3,$4)",
    SecondaryReply: "insert into data.secondary_replies(secondary_reply_id, reply_id, stars, content, author_name)values(nextval('id_sequence.secondary_reply_seq'):: numeric, (select data.replies.reply_id from data.replies where author_name = $1 and content = $2), $3, $4, $5)",
    Category: 'insert into data.categories(category, post_id) values ($1,$2)',
    Follow: 'insert into relation.follow_relation(followee_name, follower_name)values($1,$2)',
    Favorite: 'insert into relation.favorite_relation(post_id, user_name)values($1,$2)',
    Like: 'insert into relation.like_relation(post_id, user_name)values($1,$2)',
    Share: 'insert into relation.share_relation(post_id, user_name)values($1,$2)',
    parse: function (type, json) {
        switch (type) {
            case 'User':
                return [json['Author'], json['Author\'s ID'], json['Author Registration Time'], json['Author\'s Phone']];
            case 'Post':
                let location = json['Posting City'];
                let index = location.lastIndexOf(',');
                return [json['Post ID'], json['Title'], json['Posting Time'], json['Author'], location.substring(0, index), location.substring(index + 1), json['Content']];
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
                        allPromise.push(db.query(this.Category, [each, obj['Post ID']]));
                    }
                    Promise.allSettled(allPromise)
                        .then((results) => {
                            results.forEach((promise) => {
                                if (promise.status === 'rejected') {
                                    console.error(promise);
                                }
                            })
                            resolve();
                        })
                });
            case 'Follow':
                return new Promise((resolve, reject) => {
                    let allPromise = [];
                    for (let each of obj['Authors Followed By']) {
                        allPromise.push(db.query(this.Follow, [each, obj['Author']]));
                    }
                    Promise.allSettled(allPromise)
                        .then((results) => {
                            results.forEach((promise) => {
                                if (promise.status === 'rejected') {
                                    console.error(promise);
                                }
                            })
                            resolve();
                        })
                });
            case 'Favorite':
                return new Promise((resolve, reject) => {
                    let allPromise = [];
                    for (let each of obj['Authors Who Favorited the Post']) {
                        allPromise.push(db.query(this.Favorite, [obj['Post ID'], each]));
                    }
                    Promise.allSettled(allPromise)
                        .then((results) => {
                            results.forEach((promise) => {
                                if (promise.status === 'rejected') {
                                    console.error(promise);
                                }
                            })
                            resolve();
                        })
                });
            case 'Like':
                return new Promise((resolve, reject) => {
                    let allPromise = [];
                    for (let each of obj['Authors Who Liked the Post']) {
                        allPromise.push(db.query(this.Like, [obj['Post ID'], each]));
                    }
                    Promise.allSettled(allPromise)
                        .then((results) => {
                            results.forEach((promise) => {
                                if (promise.status === 'rejected') {
                                    console.error(promise);
                                }
                            })
                            resolve();
                        })
                });
            case 'Share':
                return new Promise((resolve, reject) => {
                    let allPromise = [];
                    for (let each of obj['Authors Who Shared the Post']) {
                        allPromise.push(db.query(this.Share, [obj['Post ID'], each]));
                    }
                    Promise.allSettled(allPromise)
                        .then((results) => {
                            results.forEach((promise) => {
                                if (promise.status === 'rejected') {
                                    console.error(promise);
                                }
                            })
                            resolve();
                        })
                });
            default:
                throw ('wrong type');
        }
    }
}

let db;

async function insertPost(obj) {
    let promiseList = [];
    for (let post of obj) {
        promiseList.push(db.query(insertion.User, insertion.parse('User', post)));
    }
    Promise.allSettled(promiseList).then((results) => {
        results.forEach((promise) => {
            if (promise.status === 'rejected') {
                console.error(promise);
            }
        })
        promiseList.length = 0;
    })
    for (let post of obj) {
        promiseList.push(db.query(insertion.Post, insertion.parse('Post', post)));
    }
    Promise.allSettled(promiseList).then((results) => {
        results.forEach((promise) => {
            if (promise.status === 'rejected') {
                console.error(promise);
            }
        })
        promiseList.length = 0;
    })
    for (let post of obj) {
        promiseList.push(insertion.insertArray('Category', post));
        promiseList.push(insertion.insertArray('Follow', post));
        promiseList.push(insertion.insertArray('Favorite', post));
        promiseList.push(insertion.insertArray('Like', post));
        promiseList.push(insertion.insertArray('Share', post));
    }
    return new Promise((resolve, reject) => {
        Promise.allSettled(promiseList)
            .then((results) => {
                results.forEach((promise) => {
                    if (promise.status === 'rejected') {
                        console.error(promise);
                    }
                })
                console.log('finish post insert');
                resolve();
            })
    })
}

async function insertReply(obj) {
    let promiseList = [];
    let count = 0, te = 0;
    for (let reply of obj) {
        if (++count > maxPromise) {
            await Promise.allSettled(promiseList);
            promiseList.forEach((promise) => {
                if (promise.status === 'rejected') {
                    console.error(promise);
                }
            })
            promiseList.length = 0;
            count = 0;
        }
        promiseList.push(db.query(insertion.Reply, insertion.parse('Reply', reply, db)));
    }
    await Promise.allSettled(promiseList);
    promiseList.forEach((promise) => {
        if (promise.status === 'rejected') {
            console.error(promise);
        }
    })
    promiseList.length = 0;
    count = 0;
    for (let reply of obj) {
        if (++count > maxPromise) {
            await Promise.allSettled(promiseList);
            promiseList.forEach((promise) => {
                if (promise.status === 'rejected') {
                    console.error(promise);
                }
            })
            promiseList.length = 0;
            count = 0;
        }
        promiseList.push(db.query(insertion.SecondaryReply, insertion.parse('SecondaryReply', reply, db)));
    }

    return new Promise((resolve, reject) => {
        Promise.allSettled(promiseList)
            .then((results) => {
                results.forEach((promise) => {
                    if (promise.status === 'rejected') {
                        console.error(promise);
                    }
                })
                console.log('finish reply insert');
                resolve();
            })
    })
}

module.exports = {
    initClient: function (info) {
        db = new Client(info);
    },
    initPool: function (info) {
        db = new Pool(info);
    },
    openConnection: function () {
        db.connect()
            .catch(() => console.error('failed to connect'));
    },
    closeConnection: function () {
        db.end();
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

