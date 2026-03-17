<script setup lang="ts">
import { useCart } from '@/services/useCart';
import { computed, ref, onMounted } from 'vue';
import axios from 'axios';
import { getCurrentCustomerId } from '@/services/customerService';

const { cartItems, cartCount, cartTotal, removeFromCart, updateQuantity, clearCart } = useCart();

interface Locations {
  custID: number;
  street: string;
  city: string;
  state: string;
  zip: string;
  locID: number;
}

interface Order {
  custId: number;
  orderItems: OrderRing[];
  orderDate?: string;
  status?: string;
  locationId: number;
}

interface OrderRing {
  productId: number;
  materialId: number;
  widthId: number;
  stoneId: number;
  quantity: number;
}

const locations = ref<Locations[]>([]);
const newLocation = ref({
  street: '',
  city: '',
  state: '',
  zip: ''
});

const getPricePerUnit = (item: any) => {
  return (item.price || 0) / (item.quantity || 1);
};

const handleQuantityChange = (index: number, newQuantity: number) => {
  const pricePerUnit = getPricePerUnit(cartItems.value[index]);
  updateQuantity(index, newQuantity, pricePerUnit);
};

const handleCheckout = () => {
  if (cartItems.value.length === 0) {
    // alert('Your cart is empty!');
    return;
  }

  // Check if customer ID exists
  const customerId = getCurrentCustomerId();
  if (!customerId) {
    alert('Please log in to place an order.');
    console.error('No customer ID found. User must be logged in to checkout.');
    return;
  }

  const selectedLocID = Number(selectedLocation.value);
  if (!selectedLocID) {
    alert('Please select a delivery location before checking out.');
    console.error('No delivery location selected. locID is required to checkout.');
    return;
  }

  // Adding cart items to an order interface item
  const order: Order = {
    custId: customerId, //require customer ID
    orderItems: cartItems.value.map(item => ({
      productId: item.ringId,
      materialId: item.materialType,
      widthId: item.bandWidth,
      stoneId: item.ringStone,
      quantity: item.quantity
    })),
    locationId: Number(selectedLocation.value) || 0
  };
    // sending order to backend to be submitted and processed
    try {
      console.log('Submitting order:', order);
      axios.post(`${import.meta.env.VITE_API_URL}/order`, order)
        .then(response => {
          console.log('Order submitted successfully:', response.data);
          alert('Order submitted successfully!');
          clearCart();
        })
    } catch(error) {
      console.error('Error submitting order:', error);
      alert('Failed to submit order. Please try again.');
    }
  

  // alert(`Proceeding to checkout with ${cartCount.value} item(s) totaling $${cartTotal.value.toFixed(2)}`);
};

const fetchLocations = async () => {
  // function to get delivery locations for a specific customer
  const customerId = getCurrentCustomerId() || 3; // Default to 3 if no customer ID is found
  
  try {
    
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/locations/${customerId}`);
    // Filter locations to only show those matching the current customer ID
    locations.value = response.data.filter((location: Locations) => location.custID === customerId);
    
  } catch (error) {
    console.error('Error fetching delivery locations:', error);
    // Mock data for development - filter to match current customerId
    const mockData = [
      {
        custID: 1,
        street: '123 Main St',
        city: 'Anytown',
        state: 'CA',
        zip: "12345",
        locID: 1
      },
      {
        custID: 1,
        street: '456 Oak Ave',
        city: 'Othertown',
        state: 'NY',
        zip: "67890",
        locID: 2
      },
      {
        custID: 2,
        street: '789 Pine Rd',
        city: 'Somewhere',
        state: 'TX',
        zip: "54321",
        locID: 3
      },
      {
        custID: 3,
        street: '321 Elm St',
        city: 'Springfield',
        state: 'IL',
        zip: "98765",
        locID: 4
      }
    ];
    
    // Filter mock data to only show locations for current customer
    locations.value = mockData.filter(location => location.custID === customerId);
    console.log('Using mock locations data:', mockData);
    console.log('Filtered locations for customer ID', customerId, ':', locations.value);
  }
}

const addLocation = (newLocation: Omit<Locations, 'custID' | 'locID'>) => {
  // function to add a new delivery location for the current customer
  const customerId = getCurrentCustomerId() || 3; // Default to 3 if no customer ID is found
  const locationToAdd = { ...newLocation, custID: customerId, locID: 0 };
  
  try {
    axios.post(`${import.meta.env.VITE_API_URL}/customers/${customerId}/locations`, locationToAdd)
      .then(response => {
        console.log('Location added successfully:', response.data);
        locations.value.push(response.data); // Add the new location to the list
      })
  } catch (error) {
    console.error('Error adding delivery location:', error);
    alert('Failed to add delivery location. Please try again.');
  }
};

const showNewLocationSection = ref(false);
const selectedLocation = ref('');

const handleAddLocation = () => {
  addLocation(newLocation.value);
  // Reset form after submission
  newLocation.value = {
    street: '',
    city: '',
    state: '',
    zip: ''
  };
  showNewLocationSection.value = false;
  selectedLocation.value = '';
};

const handleLocationChange = (event: Event) => {
  const target = event.target as HTMLSelectElement;
  if (target.value === 'ADD_NEW') {
    showNewLocationSection.value = true;
    
    setTimeout(() => {
      selectedLocation.value = '';
    }, 0);
  } else {
    showNewLocationSection.value = false;
  }
};

onMounted(() => {
  fetchLocations();
});


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
              <p><strong>Ring Stone:</strong> {{ item.ringStone }}</p>
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
            <p class="total-price">${{ (item.price || 0).toFixed(2) }}</p>
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
        
        <select class="delivery-select" @click="fetchLocations"  @change="handleLocationChange" v-model="selectedLocation">
          <option disabled value="">Select Delivery Location</option>
          <option v-for="location in locations" :key="`${location.custID}-${location.locID}`" :value="`${location.locID}`"> 
            {{ location.street }}, {{ location.city }}, {{ location.state }} {{ location.zip }}
          </option>
          <option value="ADD_NEW">Add New Location</option>
        </select>
        <div class="no-location" v-show="showNewLocationSection">
          <h3>Location not found? Add a new one!</h3>
          <form @submit.prevent="handleAddLocation">
            <input class="location-input" v-model="newLocation.street" placeholder="Street" required />
            <input class="location-input" v-model="newLocation.city" placeholder="City" required />
            <input class="location-input" v-model="newLocation.state" placeholder="State" required />
            <input class="location-input" v-model="newLocation.zip" placeholder="ZIP Code" required />
            <button class="add-location-btn" type="submit">Add Location</button>
          </form>
        </div>

        <button @click="handleCheckout" class="checkout-btn">
          Checkout
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
@import '../styles/cart_styles.css';
</style>
