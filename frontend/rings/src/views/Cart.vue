<script setup lang="ts">
import { useCart } from '@/services/useCart';
import { computed, ref, onMounted } from 'vue';
import axios from 'axios';
import { getCurrentCustomerId } from '@/services/customerService';

const { cartItems, cartCount, cartTotal, removeFromCart, updateQuantity, clearCart } = useCart();

interface Locations {
  customerid: number;
  street: string;
  city: string;
  state: string;
  zip: number;
}

const locations = ref<Locations[]>([]);

const getPricePerUnit = (item: any) => {
  return item.price / item.quantity;
};

const handleQuantityChange = (index: number, newQuantity: number) => {
  const pricePerUnit = getPricePerUnit(cartItems.value[index]);
  updateQuantity(index, newQuantity, pricePerUnit);
};

const handleCheckout = () => {
  if (cartItems.value.length === 0) {
    alert('Your cart is empty!');
    return;
  }
  // Implement checkout logic here
  alert(`Proceeding to checkout with ${cartCount.value} item(s) totaling $${cartTotal.value.toFixed(2)}`);
};

const fetchLocations = async () => {
  // function to get delivery locations for a specific customer
  const customerId = getCurrentCustomerId() || 3; // Default to 3 if no customer ID is found
  try {
    const response = await axios.get(`http://localhost:8080/api/customers/${customerId}/locations`);
    // Filter locations to only show those matching the current customer ID
    locations.value = response.data.filter((location: Locations) => location.customerid === customerId);
  } catch (error) {
    console.error('Error fetching delivery locations:', error);
    // Mock data for development - filter to match current customerId
    const mockData = [
      {
        customerid: 1,
        street: '123 Main St',
        city: 'Anytown',
        state: 'CA',
        zip: 12345
      },
      {
        customerid: 1,
        street: '456 Oak Ave',
        city: 'Othertown',
        state: 'NY',
        zip: 67890
      },
      {
        customerid: 2,
        street: '789 Pine Rd',
        city: 'Somewhere',
        state: 'TX',
        zip: 54321
      },
      {
        customerid: 3,
        street: '321 Elm St',
        city: 'Springfield',
        state: 'IL',
        zip: 98765
      }
    ];
    
    // Filter mock data to only show locations for current customer
    locations.value = mockData.filter(location => location.customerid === customerId);
  }
}

// onMounted(() => {
//   fetchLocations();
// });
</script>

<template>
  <div class="cart-container">
    <h1>Shopping Cart</h1>
    
    <div v-if="cartItems.length === 0" class="empty-cart">
      <p>Your cart is currently empty.</p>
      <router-link to="/purchase-rings" class="continue-shopping-link">
        Continue Shopping
      </router-link>
    </div>

    <div v-else class="cart-content">
      <div class="cart-items">
        <div v-for="(item, index) in cartItems" :key="index" class="cart-item">
          <div class="item-image">
            <img :src="item.ringImage" :alt="`Ring ${item.ringId}`" />
          </div>
          
          <div class="item-details">
            <h3>Ring #{{ item.ringId }}</h3>
            <div class="item-specs">
              <p><strong>Material:</strong> {{ item.materialType }}</p>
              <p><strong>Band Width:</strong> {{ item.bandWidth }}</p>
              <p><strong>Ring Size:</strong> {{ item.ringSize }}</p>
            </div>
          </div>
          
          <div class="item-quantity">
            <label>Quantity:</label>
            <select 
              :value="item.quantity" 
              @change="handleQuantityChange(index, Number(($event.target as HTMLSelectElement).value))"
              class="quantity-select"
            >
              <option v-for="n in 10" :key="n" :value="n">{{ n }}</option>
            </select>
          </div>
          
          <div class="item-price">
            <p class="unit-price">${{ getPricePerUnit(item).toFixed(2) }} each</p>
            <p class="total-price">${{ item.price.toFixed(2) }}</p>
          </div>
          
          <button @click="removeFromCart(index)" class="remove-btn" title="Remove from cart">
            ✕
          </button>
        </div>
      </div>
      
      <div class="cart-summary">
        <h2>Order Summary</h2>
        <div class="summary-row">
          <span>Items ({{ cartCount }}):</span>
          <span>${{ cartTotal.toFixed(2) }}</span>
        </div>
        <div class="summary-row subtotal">
          <span>Subtotal:</span>
          <span>${{ cartTotal.toFixed(2) }}</span>
        </div>
        <div class="summary-row total">
          <span>Total:</span>
          <span>${{ cartTotal.toFixed(2) }}</span>
        </div>
        
        <select class="delivery-select" @click="fetchLocations">
          <option disabled selected>Select Delivery Location</option>
          <option v-for="location in locations" :key="location.customerid" :value="location.street">
            {{ location.street }}, {{ location.city }}, {{ location.state }} {{ location.zip }}
          </option>
        </select>

        <button @click="handleCheckout" class="checkout-btn">
          Proceed to Checkout
        </button>
        
        <button @click="clearCart" class="clear-cart-btn">
          Clear Cart
        </button>
        
        <router-link to="/purchase-rings" class="continue-shopping-btn">
          Continue Shopping
        </router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>
.cart-container {
  padding: 20px;
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
  color: white;
  box-sizing: border-box;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 2.5em;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8);
}

.empty-cart {
  text-align: center;
  padding: 60px 20px;
  background-color: rgba(0, 0, 0, 0.3);
  border-radius: 8px;
}

.empty-cart p {
  font-size: 1.2em;
  margin-bottom: 20px;
}

.continue-shopping-link {
  display: inline-block;
  padding: 12px 24px;
  background-color: #baaa51;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  font-weight: bold;
  transition: background-color 0.3s ease;
}

.continue-shopping-link:hover {
  background-color: #a89840;
}

.cart-content {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 30px;
}

.cart-items {
  display: flex;
  flex-direction: column;
  gap: 20px;
  box-sizing: border-box;
  width: 100%;
}

.cart-item {
  display: grid;
  grid-template-columns: 150px 1fr auto 150px auto;
  gap: 20px;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.3);
  padding: 20px;
  border-radius: 8px;
  position: relative;
  box-sizing: border-box;
  width: 100%;
}

.item-image {
  width: 150px;
  height: 150px;
  background-color: white;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
}

.item-image img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  margin-right: 50px;
}

.item-details h3 {
  margin: 0 0 10px 0;
  color: #baaa51;
}

.item-specs p {
  margin: 5px 0;
  font-size: 0.9em;
}

.item-quantity {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.item-quantity label {
  font-size: 0.9em;
}

.quantity-select {
  padding: 8px;
  border-radius: 4px;
  border: 1px solid #ccc;
  background-color: white;
  cursor: pointer;
}

.item-price {
  text-align: right;
}

.unit-price {
  font-size: 0.85em;
  color: #ccc;
  margin: 0 0 5px 0;
}

.total-price {
  font-size: 1.2em;
  font-weight: bold;
  margin: 0;
  color: #baaa51;
}

.remove-btn {
  background-color: rgba(255, 0, 0, 0.7);
  color: white;
  border: none;
  border-radius: 4px;
  width: 40px;
  height: 40px;
  font-size: 1.5em;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.remove-btn:hover {
  background-color: rgba(255, 0, 0, 0.9);
}

.cart-summary {
  background-color: rgba(0, 0, 0, 0.4);
  padding: 25px;
  border-radius: 8px;
  height: fit-content;
  position: sticky;
  top: 20px;
  width: 75%;
}

.cart-summary h2 {
  margin: 0 0 20px 0;
  font-size: 1.5em;
  color: #baaa51;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
}

.summary-row.subtotal {
  margin-top: 10px;
  font-size: 1.1em;
}

.summary-row.total {
  font-size: 1.3em;
  font-weight: bold;
  color: #baaa51;
  border-bottom: none;
  margin-top: 10px;
  padding-top: 15px;
  border-top: 2px solid #baaa51;
}

.delivery-select {
  width: 100%;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #ccc;
  background-color: rgb(210, 210, 210);
  margin-top: 20px;
  cursor: pointer;
}

.checkout-btn,
.clear-cart-btn,
.continue-shopping-btn {
  width: 100%;
  padding: 15px;
  border: none;
  border-radius: 4px;
  font-size: 1em;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-top: 15px;
  text-decoration: none;
  display: block;
  text-align: center;
  box-sizing: border-box;
}

.checkout-btn {
  background-color: #4CAF50;
  color: white;
}

.checkout-btn:hover {
  background-color: #45a049;
  transform: translateY(-2px);
}

.clear-cart-btn {
  background-color: rgba(255, 0, 0, 0.7);
  color: white;
}

.clear-cart-btn:hover {
  background-color: rgba(255, 0, 0, 0.9);
}

.continue-shopping-btn {
  background-color: #baaa51;
  color: white;
}

.continue-shopping-btn:hover {
  background-color: #a89840;
}

@media (max-width: 1024px) {
  .cart-content {
    grid-template-columns: 1fr;
  }
  
  .cart-item {
    grid-template-columns: 100px 1fr;
    gap: 20px;
  }
  
  .item-image {
    width: 100px;
    height: 100px;
  }
  
  .item-details {
    padding-left: 10px;
  }
  
  .item-quantity,
  .item-price {
    grid-column: 2;
    padding-left: 10px;
  }
  
  .remove-btn {
    position: absolute;
    top: 10px;
    right: 10px;
  }
}
</style>
