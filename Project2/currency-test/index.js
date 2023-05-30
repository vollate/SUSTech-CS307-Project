import fs from 'fs';
import fetch from 'node-fetch';

const TestTimes = Math.pow(2, 18);
const PostTemplate = {
    method: 'POST',
    headers: {
        "Content-Type":
            "application/json"
    },
    body: ''
}

const ServerInfo = JSON.parse(fs.readFileSync('./server-info.json'));
console.log(ServerInfo);

let array = [];
console.time('total execution time');

for (let i = 0; i < TestTimes; ++i) {
    let login = PostTemplate;
    login.body = JSON.stringify({
        "type": "PostOp",
        "content": ["GetPost",i%1024]
    });
    array.push(fetch(ServerInfo.URL, login));
}
Promise.allSettled(array).then((res)=>{
    console.timeEnd('total execution time');
    process.exit();
})

