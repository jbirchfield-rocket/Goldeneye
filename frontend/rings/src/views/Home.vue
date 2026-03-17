<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { setCustomerIdCookie, getCurrentCustomerId } from '@/services/customerService';
import axios from 'axios';

const selectedCustomerId = ref<number | null>(null);

//Interface for customer data
interface Customer {
  custId: number;
  name: string;
}

const customers = ref<Customer[]>([]);

//function to store a customer id in cookie to use in API calls
const handleCustomerChange = (event: Event) => {
  const customerId = Number((event.target as HTMLSelectElement).value);
  setCustomerIdCookie(customerId);
  selectedCustomerId.value = customerId;
};

const getAvailableCustomers = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/customers`);
    // Handle the response to populate customer options
    customers.value = response.data;
    console.log('Available customers:', response.data);
  } catch (error) {
    console.error('Error fetching customers:', error);
    // mock data
    customers.value = [
      { custId: 1, name: 'Customer 1' },
      { custId: 2, name: 'Customer 2' },
      { custId: 3, name: 'Customer 3' }
    ];
  }
};

// Load the current customer ID when component mounts
onMounted(() => {
  selectedCustomerId.value = getCurrentCustomerId();
  getAvailableCustomers();
});
</script>

<template>
  <div class="home-container">
    <div class="customer-selector-wrapper">
      <label for="customer-select">Customer:</label>
      <select 
        id="customer-select"
        class="customer-select" 
        @change="handleCustomerChange($event)" 
        :value="selectedCustomerId || ''"
      >
        <option value="" disabled>Select Customer</option>
        <option v-for="customer in customers" :key="customer.custId" :value="customer.custId">{{ customer.name }}</option>
      </select>
    </div>
    
    <div class="content-center">
      <img src="../assets/Goldeneye Logo2.png" alt="Goldeneye Logo" class="logo-image" />
      <h1 class="welcome-message">Welcome to Goldeneye</h1>
      <p class="description">
        Goldeneye is a company dedicated to those who have an eye for golden perfection.
        We sell only the highest quality rings at reasonable prices. 
        Have gold in your eyes and on your fingers.
      </p>
    </div>
  </div>
</template>

<style scoped>
.home-container {
  position: relative;
  width: 100%;
  min-height: 60vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.welcome-message {
  font-size: 2.5em;
  margin: 20px 0;
  color: white;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8);
}

.customer-selector-wrapper {
  position: absolute;
  top: 20px;
  right: 20px;
  display: flex;
  align-items: center;
  gap: 10px;
  background-color: rgba(0, 0, 0, 0.4);
  padding: 10px 15px;
  border-radius: 8px;
  backdrop-filter: blur(5px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
}

.customer-selector-wrapper label {
  color: white;
  font-size: 14px;
  font-weight: bold;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.8);
  white-space: nowrap;
}

.content-center {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  margin-top: 40px;
}

.logo-image {
  width: 400px;
  height: auto;
  margin-bottom: 20px;
}

h1 {
  font-size: 2.5em;
  margin: 20px 0;
  color: white;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8);
}

.description {
  max-width: 700px;
  font-size: 1.2em;
  line-height: 1.6;
  color: white;
  text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.8);
  margin: 0 20px;
}

.customer-selector-wrapper{
  box-sizing: border-box;
  max-width: 100%;
}

.customer-select {
  padding: 8px 12px;
  border-radius: 4px;
  border: 1px solid #baaa51;
  background-color: rgba(0, 0, 0, 0.95);
  color: #ffffff;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  /* min-width: 140px; */
  box-sizing: border-box;
  max-width: 100%;
}

.customer-select:hover {
  background-color: white;
  border-color: #d4c570;
  box-shadow: 0 0 8px rgba(186, 170, 81, 0.4);
}

.customer-select:focus {
  outline: none;
  border-color: #baaa51;
  box-shadow: 0 0 8px rgba(186, 170, 81, 0.6);
}

.customer-select option {
  background-color: white;
  color: #333;
}

/* Responsive Design */
@media (max-width: 768px) {
  .customer-selector-wrapper {
    position: static;
    margin-bottom: 20px;
    width: 100%;
    max-width: 300px;
  }
  
  .logo-image {
    width: 300px;
  }
  
  h1 {
    font-size: 2em;
  }
  
  .description {
    font-size: 1em;
  }
}
</style>
