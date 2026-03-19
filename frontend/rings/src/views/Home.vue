<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { setCustomerIdCookie, getCurrentCustomerId } from '@/services/customerService';
import axios from 'axios';

const selectedCustomerId = ref<number | string | null>(null);

//Interface for customer data
interface Customer {
  custId: number;
  name: string;
  active?: number; //active is a number (1 for active, 0 for inactive)
}

const customers = ref<Customer[]>([]);
const newCustomerName = ref<string>('');
const showNewCustomerSection = ref<boolean>(false);

//function to store a customer id in cookie to use in API calls
const handleCustomerChange = (event: Event) => {
  const target = event.target as HTMLSelectElement;
  if (target.value === 'ADD_NEW') {
    showNewCustomerSection.value = true;
    
    setTimeout(() => {
      selectedCustomerId.value = null;
    }, 0);
  } else {
    showNewCustomerSection.value = false;
    // Store the selected customer ID in the cookie
    const customerId = Number(target.value);
    selectedCustomerId.value = customerId;
    setCustomerIdCookie(customerId);
  }
};

const getAvailableCustomers = async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/customers`);
    // Handle the response to populate customer options
    customers.value = response.data.filter((customer: { active: number; }) => customer.active === 1);
   // customers.value = response.data;
    console.log('Available customers:', customers.value);
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

const handleAddCustomer = async () => {
  if (!newCustomerName.value.trim()) {
    alert('Please enter a valid customer name.');
    return;
  }

  try {
    console.log('Adding new customer with name:', newCustomerName.value);
    const response = await axios.post(`${import.meta.env.VITE_API_URL}/customers`, { name: newCustomerName.value });
    
    const newCustomer: Customer = response.data;
    console.log('New customer added:', newCustomer);
    customers.value.push(newCustomer);
    setCustomerIdCookie(newCustomer.custId);
    selectedCustomerId.value = newCustomer.custId;
    newCustomerName.value = '';
  } catch (error) {
    console.error('Error adding customer:', error);
    alert('Failed to add customer. Please try again.');
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
        <option value="ADD_NEW">Add New Customer</option>
      </select>

      <div class="new-customer-section" v-if="showNewCustomerSection">
        <input 
          type="text" 
          v-model="newCustomerName" 
          placeholder="Enter new customer name" 
          class="new-customer-input">
        <button @click="handleAddCustomer" class="add-customer-button">Add Customer</button>
      </div>
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
