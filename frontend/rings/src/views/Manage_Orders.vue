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
  zip: number;
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
  const customerId = getCurrentCustomerId() || 1; // Default to customer 1 if not set
  
  try {
    loading.value = true;
    error.value = null;
    // Replace with real endpoint
    const response = await axios.get('http://localhost:8080/api/orders');
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
            stone: 'None',
            quantity: 2,
            price: 599.98
          }
        ],
          location: {
            customerid: 1,
            street: '123 Main St',
            city: 'Anytown',
            state: 'CA',
            zip: 90210
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
            stone: 'Diamond',
            quantity: 1,
            price: 449.99
          },
          {
            ringType: 'Etched Band',
            material: 'Platinum',
            width: '6mm',
            stone: 'Sapphire',
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
          zip: 90210
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
            stone: 'Ruby',
            quantity: 3,
            price: 1299.97
          }
        ],
          location: {
            customerid: 2,
            street: '456 Oak Ave',
            city: 'Sometown',
            state: 'NY',
            zip: 10001
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
            stone: 'Emerald',
            quantity: 1,
            price: 349.99
          }
        ],
          location: {
            customerid: 3,
            street: '789 Pine Rd',
            city: 'Yourtown',
            state: 'TX',
            zip: 75001
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
          <div v-if="order.orderDate" class="order-date">Date: {{ order.orderDate }}</div>
          <div class="order-location">Location: {{ order.location.street }}, {{ order.location.city }}, {{ order.location.state }} {{ order.location.zip }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.manage-orders-container {
  padding: 20px;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  color: rgba(255, 255, 255, 1);
  box-sizing: border-box;
}

h1 {
  text-align: center;
  margin-bottom: 40px;
  font-size: 2.5em;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8);
}

.loading-message,
.error-message,
.empty-orders {
  text-align: center;
  padding: 60px 20px;
  background-color: rgb(88, 88, 88);
  border-radius: 8px;
  color: white;
  box-sizing: border-box;
  max-width: 100%;
  width: 100%;
  overflow: hidden;
}

.loading-message p,
.error-message p,
.empty-orders p {
  font-size: 1.2em;
  margin-bottom: 20px;
  word-wrap: break-word;
}

.retry-btn,
.shop-link {
  display: inline-block;
  padding: 12px 24px;
  background-color: #baaa51;
  color: white;
  text-decoration: none;
  border: none;
  border-radius: 4px;
  font-weight: bold;
  font-size: 1em;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.retry-btn:hover,
.shop-link:hover {
  background-color: #a89840;
}

.orders-list {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.order-card {
  background-color: rgba(0, 0, 0, .7);
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  width: 96%;
}

.order-header {
  display: grid;
  grid-template-columns: 1fr 1.2fr 1fr 0.8fr 0.8fr 0.8fr 1fr;
  background-color: rgba(125, 125, 125, 0.5);
  border-bottom: 2px solid #333;
  padding: 15px 20px;
  font-weight: bold;
  
}

.header-item {
  font-size: 0.95em;
  color: white;
}

.order-body {
  background-color: rgba(0, 0, 0, 0.5);
}

.order-row {
  display: grid;
  grid-template-columns: 1fr 1.2fr 1fr 0.8fr 0.8fr 0.8fr 1fr;
  padding: 15px 20px;
  color: #000;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}

.order-row:last-child {
  border-bottom: none;
}

.order-row.first-row {
  border-top: 2px solid #666;
}

.order-cell {
  display: flex;
  align-items: center;
  font-size: 0.95em;
  color: white;
}

.order-date {
  margin-top: 10px;
  font-size: 0.9em;
  color: #ddd;
  padding-left: 20px;
}

.order-location {
  margin-top: 10px;
  font-size: 0.9em;
  color: #ddd;
  padding-left: 20px;
}

.order-id {
  font-weight: bold;
  color: #ffffff;
}

/* Responsive Design */
@media (max-width: 1024px) {
  .order-header {
    display: none;
  }
  
  .order-row {
    display: flex;
    flex-direction: column;
    gap: 8px;
    padding: 20px;
    border-bottom: 2px solid #999;
  }
  
  .order-row.first-row {
    border-top: 3px solid #333;
    background-color: rgba(75, 75, 75, 0.95);
  }
  
  .order-cell {
    font-size: 1em;
  }
  
  .order-id {
    font-size: 1.1em;
    margin-bottom: 10px;
  }
}

@media (max-width: 768px) {
  h1 {
    font-size: 2em;
  }
  
  .manage-orders-container {
    padding: 10px;
  }
  
  .order-card {
    margin: 0 -10px;
    border-radius: 0;
  }
}
</style>
