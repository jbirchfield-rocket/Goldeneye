import { ref, computed } from 'vue';

export interface CartItem {
  ringId: number;
  ringImage: string;
  materialType: string;
  bandWidth: string;
  ringSize: string;
  quantity: number;
  price: number;
  addedAt: number;
}

const CART_COOKIE_NAME = 'goldeneye_cart';
const COOKIE_EXPIRY_DAYS = 7;

// Global state (singleton pattern) - shared across all components
const cartItems = ref<CartItem[]>([]);
let isInitialized = false;

export function useCart() {
  // Helper function to get cookie value
  const getCookie = (name: string): string | null => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) {
      const cookieValue = parts.pop()?.split(';').shift();
      return cookieValue ? decodeURIComponent(cookieValue) : null;
    }
    return null;
  };

  // Helper function to set cookie
  const setCookie = (name: string, value: string, days: number) => {
    const date = new Date();
    date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
    const expires = `expires=${date.toUTCString()}`;
    document.cookie = `${name}=${encodeURIComponent(value)};${expires};path=/`;
  };

  // Load cart from cookie
  const loadCart = () => {
    const cartCookie = getCookie(CART_COOKIE_NAME);
    if (cartCookie) {
      try {
        const parsedCart = JSON.parse(cartCookie);
        cartItems.value = Array.isArray(parsedCart) ? parsedCart : [];
      } catch (error) {
        console.error('Error parsing cart cookie:', error);
        cartItems.value = [];
      }
    } else {
      cartItems.value = [];
    }
  };

  // Save cart to cookie
  const saveCart = () => {
    if (cartItems.value.length === 0) {
      // Clear the cookie if cart is empty
      document.cookie = `${CART_COOKIE_NAME}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
    } else {
      setCookie(CART_COOKIE_NAME, JSON.stringify(cartItems.value), COOKIE_EXPIRY_DAYS);
    }
  };

  // Add item to cart
  const addToCart = (item: CartItem) => {
    // Check if item with same configuration already exists
    const existingItemIndex = cartItems.value.findIndex(
      (cartItem) =>
        cartItem.ringId === item.ringId &&
        cartItem.materialType === item.materialType &&
        cartItem.bandWidth === item.bandWidth &&
        cartItem.ringSize === item.ringSize
    );

    if (existingItemIndex !== -1) {
      // Update quantity if item exists
      cartItems.value[existingItemIndex].quantity += item.quantity;
      cartItems.value[existingItemIndex].price += item.price;
    } else {
      // Add new item
      cartItems.value.push({
        ...item,
        addedAt: Date.now()
      });
    }

    saveCart();
  };

  // Remove item from cart
  const removeFromCart = (index: number) => {
    cartItems.value.splice(index, 1);
    saveCart();
  };

  // Update item quantity
  const updateQuantity = (index: number, newQuantity: number, pricePerUnit: number) => {
    if (newQuantity <= 0) {
      removeFromCart(index);
    } else {
      cartItems.value[index].quantity = newQuantity;
      cartItems.value[index].price = pricePerUnit * newQuantity;
      saveCart();
    }
  };

  // Clear entire cart
  const clearCart = () => {
    cartItems.value = [];
    saveCart();
  };

  // Computed values
  const cartCount = computed(() => {
    return cartItems.value.reduce((total, item) => total + item.quantity, 0);
  });

  const cartTotal = computed(() => {
    return cartItems.value.reduce((total, item) => total + item.price, 0);
  });

  // Load cart on first initialization only
  if (!isInitialized) {
    loadCart();
    isInitialized = true;
  }

  return {
    cartItems,
    cartCount,
    cartTotal,
    addToCart,
    removeFromCart,
    updateQuantity,
    clearCart,
    loadCart
  };
}
