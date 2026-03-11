<script setup lang="ts"></script>

<template>

  <div class="background-img">
    <div class="foreground">
      <nav>
        <router-link to="/">Home</router-link>
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
</style>
