<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { getCurrentCustomerId } from '@/services/customerService';

/**
 * TO DO:
 * - filter orders by customer ID
 * - add location for order in the order card
 */

interface OrderRing {
  ringType: string;
  material: string;
  width: string;
  stone: string;
  quantity: number;
  price: number;
}

interface Location {
  customerid: number;
  street: string;
  city: string;
  state: string;
  zip: string;
  locID: number;
}

interface Order {
  orderId: string;
  customerId: number;
  rings: OrderRing[];
  orderDate?: string;
  status?: string;
  location: Location;
}

const orders = ref<Order[]>([]);
const loading = ref(true);
const error = ref<string | null>(null);

const fetchOrders = async () => {
  const customerId = getCurrentCustomerId() || 2; // Default to customer 1 if not set
  
  try {
    loading.value = true;
    error.value = null;
    // Replace with real endpoint
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/api/orders`);
    //orders filtered by customer id
    orders.value = response.data.filter((order: Order) => order.customerId === customerId);
  } catch (err) {
    console.error('Error fetching orders:', err);
    // error.value = 'Failed to load orders';
    // Mock data 
    const mockOrders = [
      {
        orderId: '12345',
        customerId: 1,
        rings: [
          {
            ringType: 'Classic Band',
            material: 'Gold',
            width: '4mm',
            stone: 'Cubic Zirconia',
            quantity: 2,
            price: 599.98
          }
        ],
          location: {
            customerid: 1,
            street: '123 Main St',
            city: 'Anytown',
            state: 'CA',
            zip: '90210',
            locID: 1
          }
      },
      {
        orderId: '12346',
        customerId: 1,
        rings: [
          {
            ringType: 'Classic Band',
            material: 'Silver',
            width: '4mm',
            stone: 'Natural Diamond',
            quantity: 1,
            price: 449.99
          },
          {
            ringType: 'Etched Band',
            material: 'Platinum',
            width: '6mm',
            stone: 'Lab-Grown Diamond',
            quantity: 1,
            price: 899.99
          }
        ],
        orderDate: '2024-06-15',
        location: {
          customerid: 1,
          street: '123 Main St',
          city: 'Anytown',
          state: 'CA',
          zip: '90210',
          locID: 1
        }
      },
      {
        orderId: '12347',
        customerId: 2,
        rings: [
          {
            ringType: 'Modern Band',
            material: 'Gold',
            width: '2mm',
            stone: 'Semi-precious',
            quantity: 3,
            price: 1299.97
          }
        ],
          location: {
            customerid: 2,
            street: '456 Oak Ave',
            city: 'Sometown',
            state: 'NY',
            zip: '10001',
            locID: 2
          }
      },
      {
        orderId: '12348',
        customerId: 3,
        rings: [
          {
            ringType: 'Classic Band',
            material: 'Silver',
            width: '4mm',
            stone: 'Lab-Grown Diamond',
            quantity: 1,
            price: 349.99
          }
        ],
          location: {
            customerid: 3,
            street: '789 Pine Rd',
            city: 'Yourtown',
            state: 'TX',
            zip: '75001',
            locID: 3
          }
      }
    ];
    
    // Filter mock data by customer ID
    orders.value = mockOrders.filter(order => order.customerId === customerId);
  } finally {
    loading.value = false;
  }
};

const getOrderTotal = (order: Order): number => {
  return order.rings.reduce((total, ring) => total + ring.price, 0);
};

onMounted(() => {
  fetchOrders();
});
</script>

<template>
  <div class="manage-orders-container">
    <h1>Manage Orders</h1>
    
    <div v-if="loading" class="loading-message">
      <p>Loading orders...</p>
    </div>
    
    <div v-else-if="error" class="error-message">
      <p>{{ error }}</p>
      <button @click="fetchOrders" class="retry-btn">Retry</button>
    </div>
    
    <div v-else-if="orders.length === 0" class="empty-orders">
      <p>No orders found.</p>
      <router-link to="/purchase-rings" class="shop-link">
        Start Shopping
      </router-link>
    </div>
    
    <div v-else class="orders-list">
      <div class="order-card" v-for="order in orders" :key="order.orderId">
        <!-- Header Row -->
        <div class="order-header">
          <div class="header-item">Order #:</div>
          <div class="header-item">Ring Type:</div>
          <div class="header-item">Material:</div>
          <div class="header-item">Width:</div>
          <div class="header-item">Stone:</div>
          <div class="header-item">Quantity:</div>
          <div class="header-item">Price:</div>
        </div>
        
        <!-- Order Rows -->
        <div class="order-body">
          <div 
            v-for="(ring, index) in order.rings" 
            :key="index" 
            class="order-row"
            :class="{ 'first-row': index === 0 }"
          >
            <div class="order-cell order-id">
              <span v-if="index === 0">Order #: {{ order.orderId }}</span>
            </div>
            <div class="order-cell">Ring Type: {{ ring.ringType }}</div>
            <div class="order-cell">Material: {{ ring.material }}</div>
            <div class="order-cell">Width: {{ ring.width }}</div>
            <div class="order-cell">Stone: {{ ring.stone }}</div>
            <div class="order-cell">Quantity: {{ ring.quantity }}</div>
            <div class="order-cell">Price: ${{ ring.price.toFixed(2) }}</div>
          </div>
          <div class="additional-info">
            <div class="order-location">Delivery Location: {{ order.location.street }}, {{ order.location.city }}, {{ order.location.state }} {{ order.location.zip }}</div>
            <div v-if="order.orderDate" class="order-date">Date: {{ order.orderDate }}</div>
            <!-- display order total -->
            <div class="order-total">Total: ${{ getOrderTotal(order).toFixed(2) }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import '../styles/manage_orders_styles.css';

</style>
