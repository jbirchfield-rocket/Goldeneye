<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { getCurrentCustomerId } from '@/services/customerService';

interface OrderRing {
  productName: string;
  materialName: string;
  width: number;
  stoneName: string;
  quantity: number;
  unitPrice: number;
}

interface Location {
  custId: number;
  street: string;
  city: string;
  state: string;
  zip: string;
  locID: number;
}

interface Order {
  orderId: string;
  customerName: string;
  orderDate?: string;
  location: Location;
  orderItems: OrderRing[];
  
  status?: string;
  
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
    const response = await axios.get(`${import.meta.env.VITE_API_URL}/orders/${customerId}`);
    //orders filtered by customer id
    // orders.value = response.data.filter((order: Order) => order.customerId === customerId);
    orders.value = response.data;
  } catch (err) {
    console.error('Error fetching orders:', err);
    // error.value = 'Failed to load orders';
    // Mock data 
    const mockOrders: Order[] = [
      {
        orderId: '12345',
        customerName: 'John Doe',
        orderDate: '2024-06-10',
        orderItems: [
          {
            productName: 'Classic Band',
            materialName: 'Gold',
            width: 4,
            stoneName: 'Cubic Zirconia',
            quantity: 2,
            unitPrice: 599.98
          }
        ],
        location: {
          custId: 1,
          street: '123 Main St',
          city: 'Anytown',
          state: 'CA',
          zip: '90210',
          locID: 1
        },
        status: 'Shipped'
      },
      {
        orderId: '12346',
        customerName: 'John Doe',
        orderDate: '2024-06-15',
        orderItems: [
          {
            productName: 'Classic Band',
            materialName: 'Silver',
            width: 4,
            stoneName: 'Natural Diamond',
            quantity: 1,
            unitPrice: 449.99
          },
          {
            productName: 'Etched Band',
            materialName: 'Platinum',
            width: 6,
            stoneName: 'Lab-Grown Diamond',
            quantity: 1,
            unitPrice: 899.99
          }
        ],
        location: {
          custId: 1,
          street: '123 Main St',
          city: 'Anytown',
          state: 'CA',
          zip: '90210',
          locID: 1
        },
        status: 'Delivered'
      },
      {
        orderId: '12347',
        customerName: 'Jane Smith',
        orderDate: '2024-06-18',
        orderItems: [
          {
            productName: 'Modern Band',
            materialName: 'Gold',
            width: 2,
            stoneName: 'Semi-precious',
            quantity: 3,
            unitPrice: 433.32
          }
        ],
        location: {
          custId: 2,
          street: '456 Oak Ave',
          city: 'Sometown',
          state: 'NY',
          zip: '10001',
          locID: 2
        },
        status: 'Processing'
      },
      {
        orderId: '12348',
        customerName: 'Bob Johnson',
        orderDate: '2024-06-20',
        orderItems: [
          {
            productName: 'Classic Band',
            materialName: 'Silver',
            width: 4,
            stoneName: 'Lab-Grown Diamond',
            quantity: 1,
            unitPrice: 349.99
          }
        ],
        location: {
          custId: 3,
          street: '789 Pine Rd',
          city: 'Yourtown',
          state: 'TX',
          zip: '75001',
          locID: 3
        },
        status: 'Pending'
      }
    ];
    
    // Filter mock data by customer ID
    orders.value = mockOrders.filter(order => order.location.custId === customerId);
    
  } finally {
    loading.value = false;
  }
};

const getOrderTotal = (order: Order): number => {
  // Safety check: return 0 if orderItems is undefined or empty
  if (!order.orderItems || order.orderItems.length === 0) {
    return 0;
  }
  return order.orderItems.reduce((total, ring) => total + ring.unitPrice, 0);
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
          <div class="header-item">Order #</div>
          <div class="header-item">Ring Type</div>
          <div class="header-item">Material</div>
          <div class="header-item">Width</div>
          <div class="header-item">Stone</div>
          <div class="header-item">Quantity</div>
          <div class="header-item">Price</div>
        </div>
        
        <!-- Order Rows -->
        <div class="order-body">
          <div class="narrow-view">
            <div 
              v-for="(ring, index) in order.orderItems" 
              :key="index" 
              class="order-row"
              :class="{ 'first-row': index === 0 }"
            >
              <div class="order-cell order-id">
                <span v-if="index === 0">Order #: {{ order.orderId }}</span>
              </div>
              <div class="order-cell">Ring Type: {{ ring.productName }}</div>
              <div class="order-cell">Material: {{ ring.materialName }}</div>
              <div class="order-cell">Width: {{ ring.width }} mm</div>
              <div class="order-cell">Stone: {{ ring.stoneName }}</div>
              <div class="order-cell">Quantity: {{ ring.quantity }}</div>
              <div class="order-cell">Price: ${{ ring.unitPrice.toFixed(2) }}</div>
          </div>
        </div>

          <div class="wide-view">
            <div 
            v-for="(ring, index) in order.orderItems" 
            :key="index" 
            class="order-row"
            :class="{ 'first-row': index === 0 }"
            >
            <div class="order-cell order-id">
              <span v-if="index === 0">{{ order.orderId }}</span>
            </div>
            <div class="order-cell">{{ ring.productName }}</div>
            <div class="order-cell">{{ ring.materialName }}</div>
            <div class="order-cell">{{ ring.width }} mm</div>
            <div class="order-cell">{{ ring.stoneName }}</div>
            <div class="order-cell">{{ ring.quantity }}</div>
            <div class="order-cell">${{ ring.unitPrice.toFixed(2) }}</div>
          </div>
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
