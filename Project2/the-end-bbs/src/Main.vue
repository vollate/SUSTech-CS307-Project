<template>
  <NavBar v-bind="NavBarParameters" @change-nav="changeNav" @search="search"/>
  <HomePage v-bind="HomePageParameters" v-if="HomeStatus"/>
  <!--  <PostPage v-bind="PostPageParameters" v-if="PostStatus"/>-->
  <!--  <CategoryPage v-bind="CategoryParameters" v-if="CategoryStatus"/>-->
  <UserPage v-if="UserStatus"/>
  <Post v-bind="PostParameters" v-if="PostStatus" @dealRelation="dealRelation"/>
  <NewPost v-if="NewPostStatus"/>
</template>

<script setup>
import {computed, provide, reactive, ref} from "vue";
import NavBar from "./componments/NavBar.vue";
import HomePage from "./componments/HomePage.vue";
import PostPage from "./componments/PostPage.vue";
import CategoryPage from "./componments/CategoryPage.vue";
import UserPage from "./componments/UserPage.vue";
import Post from "./componments/Post.vue";
import {GlobalVariable} from "./componments/GlobalVariable.vue";
import NewPost from "./componments/NewPost.vue";

async function changeDisplay() {
}
provide('fetchSinglePost', fetchSinglePost);

async function doInit() {
  switch (NavBarParameters.curSelected) {
    case 'Home':
      return fetchHotTable();
    case 'Post':
      return fetchPosts();
    case 'Category':
      return fetchCategories();
    case 'User':
      return fetchPosts();
  }
}

// NavBar variable
const NavBarParameters = reactive({
  curSelected: 'User',
  // navBarInfo: [['Home'], ['Post'], ['Category'], ['User'], ['NewPost']]
  navBarInfo: [['Home'], ['Post'], ['User'], ['NewPost']]
})
const HomeStatus = computed(() => NavBarParameters.curSelected === 'Home');
const PostStatus = computed(() => NavBarParameters.curSelected === 'Post');
const CategoryStatus = computed(() => NavBarParameters.curSelected === 'Category');
const UserStatus = computed(() => NavBarParameters.curSelected === 'User');
const NewPostStatus = computed(() => NavBarParameters.curSelected === 'NewPost');

function changeNav(clickedName) {
  if (clickedName !== NavBarParameters.curSelected) {
    NavBarParameters.curSelected = clickedName;
    doInit();
  }
}

async function search(postID) {
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify({
    type: "PostOp",
    content: ["GetPost", postID]
  })
  return fetch(GlobalVariable.postURL, config)
      .then(res => res.json())
      .then(data => {
        // console.log(data.content);
        PostParameters.post = data.content[0];
      }).catch(e => console.log(e));
}

// HomePage variable
const HomePageParameters = reactive({
  headerClass: 'table-primary',
  tableClass: 'table-default',
  tableHeader: ['Title', 'Author Name', 'Content', 'Post ID'],
  tableContent: [['T1', 'far', 'emmmmmm', '3'], ['T2', 'ptr', 'example', '1']]
  // tableContent: hotTable
})
let hotTable = reactive([['T1', 'far', 'emmmmmm', '3'], ['T2', 'ptr', 'example', '1']]);

async function fetchHotTable() {
  let config = GlobalVariable.fetchTemplate;
  let timeStamp = new Date();
  timeStamp.setDate(timeStamp.getDate() - 7);
  config.body = JSON.stringify({
    type: "SearchOp",
    content: ['SearchByHot', timeStamp.toISOString().slice(0, 19).replace('T', ' ')]
  })
  fetch(GlobalVariable.postURL, config)
      .then(res => res.json())
      .then(data => {
        for (let i = 0; i < data.content.length; i++) {
          HomePageParameters.tableContent[i] = [data.content[i].title, data.content[i].author_name, data.content[i].content, data.content[i].post_id];
        }
        console.log(HomePageParameters.tableContent);
      })
}

// PostPage variable
const PostPageParameters = ref({
  headerClass: 'table-primary',
  tableClass: 'table-default',
  tableHeader: ['Title', 'Author', 'CategoryPage', 'Likes'],
  tableContent: [['f', 'f', 'f', 's'], ['e', 'e', 'e', 'q']]
})

async function fetchPosts() {//TODO

}

// CategoryPage variable
const CategoryParameters = reactive({
  categories: {
    name: ['f', 'e', 'd', 'c', 'b', 'a'],
    amount: [1, 2, 3, 4, 5, 6]
  }
})

async function fetchCategories() {
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify({
    type: 'PostOp',
    content: ['GetCategories']
  });
  return fetch(GlobalVariable.postURL, config)
      .then(ret => ret.json())
      .then(contents => {
        CategoryParameters.categories.name.length = 0;
        CategoryParameters.categories.amount.length = 0;
        for (let i = 0; i < contents.length;) {
          CategoryParameters.categories.name.push(contents[i++]);
          CategoryParameters.categories.amount.push(contents[i++]);//TODO
        }
      });
}

// const PostParameters = reactive({post: {}});
const PostParameters = reactive({post:{
      "post_id": 13,
      "title": "My First Time Hiking in the Mountains",
      "posting_time": "2015-01-30 06:30:48",
      "author_name": "prior_application",
      "city": "Dallas",
      "country": " United States",
      "content": "Yesterday I went on my first hiking trip in the mountains. It was an amazing experience to be surrounded by nature and the beautiful scenery. The fresh air and the sound of the birds made me forget all the stress and worries of daily life.",
      "replies": [
        {
          "reply_id": 2409,
          "stars": null,
          "content": "aaaa",
          "author_name": "aaaa",
          "anonymity": true,
          "secReplies": []
        },
        {
          "reply_id": 183,
          "stars": "8",
          "content": "I have never been hiking before, but your description makes me want to give it a try.",
          "author_name": "parking_disaster",
          "anonymity": false,
          "secReplies": [
            {
              "secReply_id": 195,
              "stars": "10",
              "content": "Hiking is a great way to exercise and enjoy nature.",
              "author_name": "joint_script",
              "anonymity": false
            },
            {
              "secReply_id": 193,
              "stars": "6",
              "content": "Just make sure to bring enough water and snacks.",
              "author_name": "overall_insect",
              "anonymity": false
            },
            {
              "secReply_id": 191,
              "stars": "26",
              "content": "You should definitely try it!",
              "author_name": "hot_aspect",
              "anonymity": false
            }
          ]
        },
        {
          "reply_id": 181,
          "stars": "23",
          "content": "I love hiking too! Which trail did you take?",
          "author_name": "gross_assistance",
          "anonymity": false,
          "secReplies": [
            {
              "secReply_id": 202,
              "stars": "12",
              "content": "I took the Blue Ridge Trail.",
              "author_name": "alternative_profit",
              "anonymity": false
            },
            {
              "secReply_id": 198,
              "stars": "5",
              "content": "I usually hike in the nearby park.",
              "author_name": "easy_repeat",
              "anonymity": false
            },
            {
              "secReply_id": 190,
              "stars": "5",
              "content": "It was a difficult trail but worth it.",
              "author_name": "quiet_strength",
              "anonymity": false
            }
          ]
        },
        {
          "reply_id": 189,
          "stars": "41",
          "content": "I'm always worried about getting lost during a hike. Did you have a map or a guide?",
          "author_name": "nice_exercise",
          "anonymity": false,
          "secReplies": [
            {
              "secReply_id": 204,
              "stars": "1",
              "content": "I didn't need a map, the trail was well-marked.",
              "author_name": "male_feeling",
              "anonymity": false
            },
            {
              "secReply_id": 200,
              "stars": "3",
              "content": "I had a map and a compass, but I also downloaded a hiking app.",
              "author_name": "suitable_usual",
              "anonymity": false
            },
            {
              "secReply_id": 199,
              "stars": "1",
              "content": "I went with a group of experienced hikers who knew the trail well.",
              "author_name": "fresh_gene",
              "anonymity": false
            }
          ]
        },
        {
          "reply_id": 186,
          "stars": "17",
          "content": "Did you encounter any wildlife during your hike?",
          "author_name": "heavy_single",
          "anonymity": false,
          "secReplies": [
            {
              "secReply_id": 194,
              "stars": "15",
              "content": "I heard some birds but didn't see any.",
              "author_name": "certain_spot",
              "anonymity": false
            },
            {
              "secReply_id": 192,
              "stars": "9",
              "content": "I saw a deer and some squirrels.",
              "author_name": "forward_border",
              "anonymity": false
            },
            {
              "secReply_id": 197,
              "stars": "19",
              "content": "No, I didn't see any animals.",
              "author_name": "positive_glove",
              "anonymity": false
            }
          ]
        },
        {
          "reply_id": 190,
          "stars": "16",
          "content": "I wish I had time for hiking, but I'm too busy with work and family.",
          "author_name": "unique_till",
          "anonymity": false,
          "secReplies": [
            {
              "secReply_id": 205,
              "stars": "1",
              "content": "It can be hard to find time, but it's important to make time for things you enjoy.",
              "author_name": "cheap_contest",
              "anonymity": false
            },
            {
              "secReply_id": 206,
              "stars": "17",
              "content": "Maybe you can plan a hiking trip with your family on the weekend.",
              "author_name": "healthy_clerk",
              "anonymity": false
            }
          ]
        }
      ]
} });

async function fetchSinglePost(postId) {
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify({
    type: 'PostOp',
    content: ['GetPost', postId]
  });
  return fetch(GlobalVariable.postURL, config)
      .then(ret => ret.json())
      .then(contents => {
        console.log(contents);
        PostParameters.post = contents;
      });
}

async function dealRelation(opInfo) {
  if (!GlobalVariable.hadLogin)
    return window.alert("Login First !");
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify({
    type: 'RelationOp',
    content: [opInfo[0], opInfo[1], GlobalVariable.userName]
  })
  return fetch(GlobalVariable.postURL, config)
      .then(ret => ret.json())
      .then(data => {
        console.log(data);
        if (data.content[0])
          window.alert('Operation succeed');
        else
          window.alert('Operation failed: ' + contents[1]);
      });
}
</script>

<style scoped>

</style>