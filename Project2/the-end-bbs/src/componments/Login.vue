<script setup>
defineEmits(['signUp', 'signIn']);
const Parameters = defineProps({
  hadLogin: { type: Boolean },
  loginFailed: { type: Boolean }
});
const register = {
  userName: '',
  userId: '',
  phone: '',
  password: ''
};
const loginInfo = {
  userName:'',
  password: ''
}

</script>

<template>
  <div class="container text-center ">
    <div class="m-4" />
    <div class="row">
      <h1 style="color: #0050ee">Welcome</h1>
    </div>
    <div class="row">
      <div class="form-group w-50 p-3 col-1">
        <label class="form-label mt-4"></label>
        <div class="form-floating mb-3">
          <input v-model="loginInfo.userName" type="email" class="align-items-center form-control" id="signin-user" placeholder="name@example.com">
          <label for="floatingInput">User name</label>
        </div>
        <div class="form-floating">
          <input v-model="loginInfo.password" type="password" class="form-control" id="floatingPassword" placeholder="Password">
          <label for="floatingPassword">Password</label>
        </div>
        <div class="m-4" />
        <div class="row-cols-auto">
          <button @click="$emit('signIn',loginInfo)" class="btn col btn-info">Sign in</button>
        </div>
        <div class="col-2">
        </div>
      </div>
      <div class="form-group w-50 p-3 col-1">
        <label class="form-label mt-4"></label>
        <div class="form-floating mb-3">
          <input v-model="register.userName" type="email" class="align-items-center form-control" id="signup-username"
                 placeholder="void">
          <label for="floatingInput">User name</label>
        </div>
        <div class="form-floating mb-3">
          <input v-model="register.userId" type="email" class="align-items-center form-control" id="signup-userid"
            placeholder="0">
          <label for="floatingInput">ID (optional)</label>
        </div>
        <div class="form-floating mb-3">
          <input v-model="register.phone" type="email" class="align-items-center form-control" id="signup-phone"
            placeholder="10086">
          <label for="floatingInput">Phone number (optional)</label>
        </div>
        <div class="form-floating">
          <input v-model="register.password" type="password" class="form-control" id="signup-password"
            placeholder="Password">
          <label for="floatingPassword">Password</label>
        </div>
        <div class="m-4" />
        <div class="row-cols-auto">
          <button @click="$emit('signUp', register)" class="btn col offset-1 btn-primary">Sign up</button>
        </div>
        <div class="col-2">
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>