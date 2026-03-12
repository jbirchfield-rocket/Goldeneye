<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useCart } from '@/services/useCart';

interface Ring {
  id: number;
  image: string;
  materialTypes: string[];
  bandWidths: string[];
  ringStones: string[];
  basePrice: number;
}

const rings = ref<Ring[]>([]);
const selectedOptions = ref<Record<number, {
  materialType: string;
  bandWidth: string;
  ringStone: string;
  quantity: number;
  proposedPrice: number;
}>>({});

const { addToCart: addItemToCart } = useCart();

const fetchRings = async () => {
  try {
    // Replace with API endpoint
    const response = await axios.get('http://localhost:8080/api/rings');
    rings.value = response.data;
    
    rings.value.forEach(ring => {
      selectedOptions.value[ring.id] = {
        materialType: ring.materialTypes[0] || '',
        bandWidth: ring.bandWidths[0] || '',
        ringStone: ring.ringStones[0] || '',
        quantity: 1,
        proposedPrice: ring.basePrice || 0
      };
    });
  } catch (error) {
    console.error('Error fetching rings:', error);
    // Mock data
    rings.value = [
      {
        id: 1,
        image: '/api/placeholder/300/200',
        materialTypes: ['Silver', 'Gold', 'Platinum'],
        bandWidths: ['2mm', '4mm', '6mm'],
        ringStones: ['Cubic Zirconia', 'Semi-precious', 'Lab-Grown Diamond', 'Natural Diamond'],
        basePrice: 299.99
      },
      {
        id: 2,
        image: '/api/placeholder/300/200',
        materialTypes: ['Silver', 'Gold', 'Platinum'],
        bandWidths: ['2mm', '4mm', '6mm'],
        ringStones: ['Cubic Zirconia', 'Semi-precious', 'Lab-Grown Diamond', 'Natural Diamond'],
        basePrice: 349.99
      },
      {
        id: 3,
        image: '/api/placeholder/300/200',
        materialTypes: ['Silver', 'Gold', 'Platinum'],
        bandWidths: ['2mm', '4mm', '6mm'],
        ringStones: ['Cubic Zirconia', 'Semi-precious', 'Lab-Grown Diamond', 'Natural Diamond'],
        basePrice: 399.99
      },
      {
        id: 4,
        image: '/api/placeholder/300/200',
        materialTypes: ['Silver', 'Gold', 'Platinum'],
        bandWidths: ['2mm', '4mm', '6mm'],
        ringStones: ['Cubic Zirconia', 'Semi-precious', 'Lab-Grown Diamond', 'Natural Diamond'],
        basePrice: 449.99
      }
    ];
    
    rings.value.forEach(ring => {
      selectedOptions.value[ring.id] = {
        materialType: ring.materialTypes[0] || '',
        bandWidth: ring.bandWidths[0] || '',
        ringStone: ring.ringStones[0] || '',
        quantity: 1,
        proposedPrice: ring.basePrice
      };
    });
  }
};

const updatePrice = (ringId: number) => {
  const ring = rings.value.find(r => r.id === ringId);
  if (ring && selectedOptions.value[ringId]) {
    const options = selectedOptions.value[ringId];
    // swap the price multipliers with multipliers from db
    let price = ring.basePrice;
    if (options.materialType === 'Gold') price *= 1.5;
    if (options.materialType === 'Platinum') price *= 2;
    if (options.bandWidth === '6mm') price *= 1.2;
    if (options.bandWidth === '4mm') price *= 1.1;
    if (options.ringStone === 'Semi-precious') price *= 1.2;
    if (options.ringStone === 'Lab-Grown Diamond') price *= 1.5;
    if (options.ringStone === 'Natural Diamond') price *= 2;
    options.proposedPrice = price * options.quantity;
  }
};

const addToCart = (ringId: number) => {
  const ring = rings.value.find(r => r.id === ringId);
  const options = selectedOptions.value[ringId];
  
  if (ring && options) {
    addItemToCart({
      ringId: ring.id,
      ringImage: ring.image,
      materialType: options.materialType,
      bandWidth: options.bandWidth,
      ringStone: options.ringStone,
      quantity: options.quantity,
      price: options.proposedPrice,
      addedAt: Date.now()
    });
    
    alert(`Added ${options.quantity} ring(s) to cart!`);
  }
};

onMounted(() => {
  fetchRings();
});
</script>

<template>
  <div class="purchase-container">
    <div class="rings-grid">
      <div v-for="ring in rings" :key="ring.id" class="ring-card">
        <div class="ring-image-container">
          <img :src="ring.image" :alt="`Ring ${ring.id}`" class="ring-image" />
        </div>
        
        <div class="ring-options">
          <div class="option-row">
            <div class="option-group">
              <label>Select Material Type</label>
              <select 
                v-model="selectedOptions[ring.id]!.materialType"
                @change="updatePrice(ring.id)"
                class="option-select"
              >
                <option v-for="type in ring.materialTypes" :key="type" :value="type">
                  {{ type }}
                </option>
              </select>
            </div>
            
            <div class="option-group">
              <label>Select Band Width</label>
              <select 
                v-model="selectedOptions[ring.id]!.bandWidth"
                @change="updatePrice(ring.id)"
                class="option-select"
              >
                <option v-for="width in ring.bandWidths" :key="width" :value="width">
                  {{ width }}
                </option>
              </select>
            </div>
            
            <div class="option-group">
              <label>Select Ring Stone</label>
              <select 
                v-model="selectedOptions[ring.id]!.ringStone"
                @change="updatePrice(ring.id)"
                class="option-select"
              >
                <option v-for="stone in ring.ringStones" :key="stone" :value="stone">
                  {{ stone }}
                </option>
              </select>
            </div>
          </div>
          
          <div class="bottom-row">
            <div class="option-group quantity-group">
              <label>Quantity</label>
              <select 
                v-model.number="selectedOptions[ring.id]!.quantity"
                @change="updatePrice(ring.id)"
                class="option-select"
              >
                <option v-for="n in 10" :key="n" :value="n">{{ n }}</option>
              </select>
            </div>
            
            <div class="price-group">
              <label>Proposed Price</label>
              <div class="price-display">${{ selectedOptions[ring.id]!.proposedPrice.toFixed(2) }}</div>
            </div>
            
            <button @click="addToCart(ring.id)" class="add-to-cart-btn">
              Add to Cart
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.purchase-container {
  padding: 20px;
  width: 100%;
  box-sizing: border-box;
}

.rings-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 30px;
  max-width: 1400px;
  margin: 0 auto;
}

.ring-card {
  background-color: rgba(0, 0, 0, 0.3);
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
}

.ring-image-container {
  width: 100%;
  height: 250px;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  box-sizing: border-box;
}

.ring-image {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

.ring-options {
  padding: 15px;
}

.option-row {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  margin-bottom: 10px;
}

.option-group {
  display: flex;
  flex-direction: column;
}

.option-group label {
  color: white;
  font-size: 12px;
  margin-bottom: 5px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.8);
}

.option-select {
  background-color: rgba(209, 209, 209, 0.9);
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 8px;
  font-size: 14px;
  cursor: pointer;
  color: #333;
}

.option-select:focus {
  outline: none;
  border-color: #baaa51;
}

.bottom-row {
  display: grid;
  grid-template-columns: 1fr 1fr 1.5fr;
  gap: 10px;
  align-items: end;
}

.quantity-group {
  min-width: 80px;
}

.price-group {
  display: flex;
  flex-direction: column;
}

.price-display {
  background-color: rgba(209, 209, 209, 0.9);
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 8px;
  font-size: 14px;
  font-weight: bold;
  color: #333;
  text-align: center;
}

.add-to-cart-btn {
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
  height: 38px;
}

.add-to-cart-btn:hover {
  background-color: #45a049;
}

.add-to-cart-btn:active {
  transform: scale(0.98);
}

@media (max-width: 1200px) {
  .rings-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
}

@media (max-width: 768px) {
  .option-row {
    grid-template-columns: 1fr;
  }
  
  .bottom-row {
    grid-template-columns: 1fr;
  }
  
  .add-to-cart-btn {
    width: 100%;
  }
}
</style>
