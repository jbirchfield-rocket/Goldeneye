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
@import '../styles/home_styles.css';
</style>
