<template>
  <NavBar v-bind="NavBarParameters" @change-nav="changeNav"/>
  <!--  <HomePage v-bind="HomePageParameters" v-if="HomeStatus"/>-->
  <!--  <PostPage v-bind="PostPageParameters" v-if="PostStatus"/>-->
  <!--  <CategoryPage v-bind="CategoryParameters" v-if="CategoryStatus"/>-->
  <!--  <UserPage v-if="UserStatus"/>-->
  <Post v-bind="PostParameters" @dealRelation="dealRelation"/>
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

async function changeDisplay() {
}

provide('fetchSinglePost', fetchSinglePost);

async function doInit() {
  switch (NavBarParameters.curSelected) {
    case 'Home':
      // return fetchHotTable();TODO
      return;
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
  navBarInfo: [['Home'], ['Post'], ['Category'], ['User']]
})
const HomeStatus = computed(() => NavBarParameters.curSelected === 'Home');
const PostStatus = computed(() => NavBarParameters.curSelected === 'Post');
const CategoryStatus = computed(() => NavBarParameters.curSelected === 'Category');
const UserStatus = computed(() => NavBarParameters.curSelected === 'User');

function changeNav(clickedName) {
  if (clickedName !== NavBarParameters.curSelected) {
    NavBarParameters.curSelected = clickedName;
    doInit();
  }
}

// HomePage variable
const HomePageParameters = reactive({
  headerClass: 'table-primary',
  tableClass: 'table-default',
  tableHeader: ['Title', 'UserPage', 'Content', 'Clicks'],
  tableContent: [['T1', 'far', 'emmmmmm', '3'], ['T2', 'ptr', 'example', '1']]
  // tableContent: hotTable
})
let hotTable = reactive([['T1', 'far', 'emmmmmm', '3'], ['T2', 'ptr', 'example', '1']]);

async function fetchHotTable() {
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify({
    type: "SearchOp",
    content: []
  })
  hotTable = fetch(GlobalVariable.postURL, config).then((content) => {
    hotTable = content.json();
  })
}

// PostPage variable
const PostPageParameters = ref({
  headerClass: 'table-primary',
  tableClass: 'table-default',
  tableHeader: ['Title', 'Author', 'CategoryPage', 'Likes'],
  tableContent: [['f', 'f', 'f', 's'], ['e', 'e', 'e', 'q']]
})

async function fetchPosts() {

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

//Post variable
const PostParameters = ref({
  post: {
    "post_id": 4,
    "title": "What's Your Favorite Hobby?",
    "posting_time": "2011-04-04 17:36:46",
    "author_name": "acceptable_camp",
    "city": "Toronto",
    "country": " Canada",
    "content": "0",
    "replies": [
      {
        "reply_id": 60,
        "stars": "14",
        "content": "I enjoy playing musical instruments in my free time. It's a great way to relax and express myself creatively.",
        "author_name": "proof_lift",
        "secReplies": [
          {
            "secReply_id": 59,
            "stars": "0",
            "content": "I wish I was musically talented.",
            "author_name": "remote_drawing"
          },
          {
            "secReply_id": 62,
            "stars": "17",
            "content": "What kind of instruments do you play?",
            "author_name": "joint_gather"
          }
        ]
      },
      {
        "reply_id": 67,
        "stars": "3",
        "content": "I like to collect stamps and coins from all around the world. It's a fun way to learn about different cultures.",
        "author_name": "ugly_effect",
        "secReplies": [
          {
            "secReply_id": 65,
            "stars": "6",
            "content": "How many countries have you collected stamps from?",
            "author_name": "character_rope"
          },
          {
            "secReply_id": 67,
            "stars": "19",
            "content": "What's the most interesting coin you've ever found?",
            "author_name": "less_associate"
          }
        ]
      },
      {
        "reply_id": 56,
        "stars": "33",
        "content": "I love hiking and being outdoors. There's nothing quite like the feeling of being surrounded by nature.",
        "author_name": "lost_equal",
        "secReplies": [
          {
            "secReply_id": 56,
            "stars": "12",
            "content": "I'm more of a beach person myself.",
            "author_name": "sick_mountain"
          },
          {
            "secReply_id": 55,
            "stars": "26",
            "content": "Where is your favorite place to go hiking?",
            "author_name": "remote_phone"
          }
        ]
      },
      {
        "reply_id": 62,
        "stars": "10",
        "content": "I love practicing yoga and meditation. It helps me clear my mind and stay centered.",
        "author_name": "clear_neck",
        "secReplies": [
          {
            "secReply_id": 60,
            "stars": "5",
            "content": "Do you practice every day?",
            "author_name": "educational_audience"
          },
          {
            "secReply_id": 63,
            "stars": "4",
            "content": "Have you noticed any benefits to your mental health?",
            "author_name": "slight_prompt"
          }
        ]
      },
      {
        "reply_id": 64,
        "stars": "12",
        "content": "I really enjoy playing board games with my friends and family. It's a great way to bond and have fun together.",
        "author_name": "forward_sail",
        "secReplies": [
          {
            "secReply_id": 66,
            "stars": "20",
            "content": "I've never been much of a board game person, but maybe I should try it out.",
            "author_name": "alternative_profit"
          },
          {
            "secReply_id": 64,
            "stars": "6",
            "content": "What's your favorite board game?",
            "author_name": "emotional_drink"
          }
        ]
      },
      {
        "reply_id": 53,
        "stars": "22",
        "content": "I really enjoy playing video games in my free time. It helps me unwind after a long day at work.",
        "author_name": "wild_minimum",
        "secReplies": [
          {
            "secReply_id": 52,
            "stars": "1",
            "content": "I've been playing a lot of RPGs lately.",
            "author_name": "gross_tower"
          },
          {
            "secReply_id": 54,
            "stars": "10",
            "content": "What kind of games do you like to play?",
            "author_name": "common_spend"
          }
        ]
      },
      {
        "reply_id": 55,
        "stars": "4",
        "content": "I'm really into painting and drawing. It's a great way for me to express myself and unwind.",
        "author_name": "amazing_hide",
        "secReplies": [
          {
            "secReply_id": 57,
            "stars": "4",
            "content": "Do you have any favorite artists?",
            "author_name": "senior_sample"
          },
          {
            "secReply_id": 61,
            "stars": "24",
            "content": "Have you ever sold any of your paintings?",
            "author_name": "aggressive_term"
          }
        ]
      }
    ]
  }
});

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
    return window.alert("Login First!");
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify({
    type: 'RelationOp',
    content: [opInfo[0], opInfo[1], GlobalVariable.userName]
  })
  return fetch(GlobalVariable.postURL, config)
      .then(ret => ret.json())
      .then(contents => {
        window.alert(contents[0]);
      });
}
</script>

<style scoped>

</style>