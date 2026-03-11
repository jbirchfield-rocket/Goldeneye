<script setup lang="ts"></script>

<template>

  <div class="background-img">
    <div class="foreground">
      <nav>
        <router-link to="/">Home</router-link>
        <img class="logo" src="./assets/Goldeneye_Single.png" alt="Goldeneye Logo" />
      </nav>
      <router-view />
    </div>
  </div>
</template>

<style>
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  overflow-x: hidden;
}

#app {
  margin: 0;
  padding: 0;
}
</style>

<style scoped>

.background-img {
  background-image: url('./assets/Background_img.png');
  background-size: cover;
  background-position: center;
  height: 100vh;
}

.foreground {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: white;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
  background-color: rgba(0, 0, 0, 0.6);
  max-width: 80%;
  margin: auto;
}

nav {
  display: flex;
  align-items: left;
  justify-content: space-between;
  width: 96%;
  padding: 20px;
  background-color: rgba(0, 0, 0, 0.7);
}

.logo {
  float: right;
  margin-left: 10px;
  width: 50px;
  height: auto;
}

nav a {
  color: white;
  text-decoration: none;
  font-size: 18px;
  font-weight: bold;
  transition: all 0.3s ease;
}

nav a:visited {
  color: white;
}

nav a:hover,
nav a:visited:hover {
  color: #baaa51;
  transform: scale(1.2);
}
</style>
