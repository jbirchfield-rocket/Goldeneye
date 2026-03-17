<script setup lang="ts">
import { ref } from 'vue';
import { useCart } from '@/services/useCart';

const { cartCount } = useCart();
const menuOpen = ref(false);

const toggleMenu = () => {
  menuOpen.value = !menuOpen.value;
};

const closeMenu = () => {
  menuOpen.value = false;
};

</script>

<template>

  <div class="background-img">
    <div class="foreground">
      <nav>
        <div class="nav-content" :class="{ 'menu-open': menuOpen }">
          <router-link to="/" @click="closeMenu">Home</router-link>
          <router-link to="/purchase-rings" @click="closeMenu">Purchase Rings</router-link>
          <router-link to="/manage-orders" @click="closeMenu">Manage Orders</router-link>
          <router-link to="/about-us" @click="closeMenu">About Us</router-link>
          <router-link class="right-link cart-link" to="/cart" @click="closeMenu">
            Cart
            <span v-if="cartCount > 0" class="cart-badge">{{ cartCount }}</span>
          </router-link>
        </div>
        <!-- Three bar icon for hamburger menu only for mobile viewports-->
          <img 
              class="hamburger-icon" 
              src="./assets/Hamburger_Icon.png" 
              alt="Menu Icon" 
              @click="toggleMenu"
            />
        <!-- Goldeneye logo links back to welcome/landing page-->
         <router-link to="/" @click="closeMenu">
            <img 
              class="logo" 
              src="./assets/Goldeneye_Single.png" 
              alt="Goldeneye Logo"
              :class="{ 'menu-active': menuOpen }"
            />
          </router-link>
      </nav>
      <div class="foreground-content">
        <router-view />
      </div>
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

body {
  font-family: 'Cormorant Garamond', serif;
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
  background-color: rgba(0, 0, 0, 0.6);
  max-width: 80%;
  margin: auto;
  /* box-sizing: border-box; */
}

.foreground-content {
  max-width: 100%;
  padding: 20px;
  box-sizing: border-box;
}

nav {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 20px;
  background-color: rgba(0, 0, 0, 0.7);
  box-sizing: border-box;
  position: relative;
}

.nav-content {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  flex: 1;
  gap: 10px;
}

.logo {
  float: right;
  width: 50px;
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
  /* padding: 2px 6px; */
  font-size: .8em;
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

.hamburger-icon {
  display: none;
  width: 30px;
  cursor: pointer;
}

/* Mobile Styles */
@media (max-width: 768px) {
  nav {
    flex-wrap: nowrap;
    justify-content: space-between;
  }

  .nav-content {
    position: fixed;
    top: 0;
    left: -100%;
    width: 70%;
    height: 100vh;
    background-color: rgba(0, 0, 0, 0.95);
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    padding: 80px 20px 20px 20px;
    gap: 20px;
    transition: left 0.3s ease;
    z-index: 1000;
    box-shadow: 2px 0 10px rgba(0, 0, 0, 0.5);
  }

  .nav-content.menu-open {
    left: 0;
  }

  .nav-content a {
    font-size: 20px;
    margin: 0;
    width: 100%;
    padding: 10px 0;
    border-bottom: 1px solid rgba(186, 170, 81, 0.3);
  }

  .nav-content a:hover {
    transform: translateX(10px);
    color: #baaa51;
  }

  .right-link {
    margin-left: 0 !important;
  }

   .logo {
    position: relative;
    float: right;
    cursor: pointer;
    transition: transform 0.3s ease;
    
  } 

  .logo.menu-active {
  transform: rotate(180deg);
}

  /* Overlay when menu is open */
  .nav-content.menu-open::before {
    content: '';
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: -1;
  }

  .hamburger-icon {
    display: block;
  }
}

</style>
