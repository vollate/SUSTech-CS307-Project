const fs_p = require('fs/promises');
const JsonDir = 'data/';

let files = [];

let dir_files = fs_p.readdir(JsonDir, {withFileTypes: true});
dir_files.then((file_list) => {
    for (let file_name of file_list)
        files.push(fs_p.readFile(JsonDir + file_name.name));
}).then(() => {
    Promise.all(files).then((data) => {
        for (let file_content of data) {
            let json = JSON.parse(file_content);
            console.log(json);
        }
    });
}).catch((e) => {
    console.error(e);
});

