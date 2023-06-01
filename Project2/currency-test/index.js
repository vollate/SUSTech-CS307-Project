import fs from 'fs';
import fetch from 'node-fetch';

const TestTimes = Math.pow(2, 12);
const PostTemplate = {
    method: 'POST', headers: {
        "Content-Type": "application/json"
    }, body: ''
}

const ServerInfo = JSON.parse(fs.readFileSync('./server-info.json'));

let promiseArray = [];
console.time('Get ' + TestTimes + ' post cost');

for (let i = 0; i < TestTimes; ++i) {
    let getPost = PostTemplate;
    getPost.body = JSON.stringify({
        "type": "PostOp", "content": ["GetPost", i % 200]
    });
    promiseArray.push(fetch(ServerInfo.URL, getPost));
}
Promise.allSettled(promiseArray).then((res) => {
    for (let each of res) {
        if (each.status === 'rejected') console.error(each);
    }
    console.timeEnd('Get ' + TestTimes + ' post cost');

}).then(() => {
    console.time('Login ' + TestTimes + ' users cost');
    promiseArray.length = 0;
    for (let i = 0; i < TestTimes; ++i) {
        let login = PostTemplate;
        login.body = JSON.stringify({
            "type": "UserOp", "content": ["Login", i.toString() + '_user', i.toString() + '_passwd']
        });
        promiseArray.push(fetch(ServerInfo.URL, login));
    }
    Promise.allSettled(promiseArray).then((res) => {
        for (let each of res) {
            if (each.status === 'rejected') console.error(each);
        }
        console.timeEnd('Login ' + TestTimes + ' users cost');
    }).then(() => {
        let timeStamp = new Date();
        timeStamp.setDate(timeStamp.getDate() - 7);
        console.time('SearchHotTable ' + TestTimes + ' users cost');
        promiseArray.length = 0;
        for (let i = 0; i < TestTimes; ++i) {
            let login = PostTemplate;
            login.body = JSON.stringify({
                "type": "SearchOp", "content": ["SearchByHot", timeStamp.toISOString().slice(0, 19).replace('T', ' ')]
            });
            promiseArray.push(fetch(ServerInfo.URL, login));
        }

        Promise.allSettled(promiseArray).then((res) => {
            for (let each of res) {
                if (each.status === 'rejected') console.error(each);
            }
            console.timeEnd('SearchHotTable ' + TestTimes + ' users cost');
        })
    })
})
