<script setup lang="ts">
import { useCart } from '@/services/useCart';

const { cartCount } = useCart();
</script>

<template>

  <div class="background-img">
    <div class="foreground">
      <nav>
        <router-link to="/">Home</router-link>
        <router-link to="/purchase-rings">Purchase Rings</router-link>
        <router-link to="/manage-orders">Manage Orders</router-link>
        <router-link to="/about-us">About Us</router-link>
        <router-link class="right-link cart-link" to="/cart">
          Cart
          <span v-if="cartCount > 0" class="cart-badge">{{ cartCount }}</span>
        </router-link>
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
  background-attachment: fixed;
  min-height: 100vh;
}

.foreground {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  min-height: 100vh;
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

.right-link {
  margin-left: auto;
}

.cart-link {
  position: relative;
  display: inline-flex;
  align-items: center;
}

.cart-badge {
  position: absolute;
  top: -8px;
  right: -12px;
  background-color: #358600;
  color: white;
  border-radius: 20%;
  padding: 2px 6px;
  font-size: 12px;
  font-weight: bold;
  min-width: 20px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

nav a {
  color: white;
  text-decoration: none;
  font-size: 18px;
  font-weight: bold;
  transition: all 0.3s ease;
  margin-left: 10px;
  margin-right: 10px;
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
