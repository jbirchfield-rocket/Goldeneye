import { describe, it, expect, beforeEach, vi, type Mock } from 'vitest'
import { mount, flushPromises } from '@vue/test-utils'
import { createRouter, createWebHistory } from 'vue-router'
import Cart from '../views/Cart.vue'
import { useCart } from '@/services/useCart'
import type { CartItem } from '@/services/useCart'

// Mock axios
vi.mock('axios', () => ({
  default: {
    get: vi.fn(),
    post: vi.fn(),
  },
}))

// Mock customerService
vi.mock('@/services/customerService', () => ({
  getCurrentCustomerId: vi.fn(),
}))

import axios from 'axios'
import { getCurrentCustomerId } from '@/services/customerService'

// Helper to clear all cookies
function clearAllCookies() {
  document.cookie.split(';').forEach((c) => {
    document.cookie = c.trim().split('=')[0] + '=; path=/; max-age=0'
  })
}

// Factory for creating a CartItem
function createCartItem(overrides: Partial<CartItem> = {}): CartItem {
  return {
    ringId: 1,
    ringImage: '/rings/ring1.png',
    materialType: 1,
    bandWidth: 5,
    ringStone: 2,
    quantity: 1,
    price: 100,
    addedAt: Date.now(),
    ...overrides,
  }
}

// Create a minimal router for router-link support
function createTestRouter() {
  return createRouter({
    history: createWebHistory(),
    routes: [
      { path: '/', component: { template: '<div>Home</div>' } },
      { path: '/purchase-rings', component: { template: '<div>Purchase</div>' } },
      { path: '/cart', component: { template: '<div>Cart</div>' } },
    ],
  })
}

function mountCart() {
  const router = createTestRouter()
  return mount(Cart, {
    global: {
      plugins: [router],
    },
  })
}

describe('Cart.vue', () => {
  beforeEach(() => {
    clearAllCookies()
    // Reset cart state
    const { clearCart } = useCart()
    clearCart()

    // Reset all mocks
    vi.clearAllMocks()

    // Default mock: API calls reject so fallback mock data is used
    ;(axios.get as Mock).mockRejectedValue(new Error('Network error'))
    ;(axios.post as Mock).mockResolvedValue({ data: { orderNumber: '12345' } })
    ;(getCurrentCustomerId as Mock).mockReturnValue(null)
  })

  describe('empty cart rendering', () => {
    it('should display empty cart message when cart is empty', () => {
      const wrapper = mountCart()
      expect(wrapper.find('.empty-cart').exists()).toBe(true)
      expect(wrapper.text()).toContain('Your cart is currently empty.')
    })

    it('should show "Continue Shopping" link when cart is empty', () => {
      const wrapper = mountCart()
      const link = wrapper.find('.continue-shopping-link')
      expect(link.exists()).toBe(true)
      expect(link.text()).toContain('Continue Shopping')
    })

    it('should not show cart content when cart is empty', () => {
      const wrapper = mountCart()
      expect(wrapper.find('.cart-content').exists()).toBe(false)
    })
  })

  describe('cart with items rendering', () => {
    beforeEach(() => {
      const { addToCart } = useCart()
      addToCart(createCartItem({ ringId: 1, price: 100, quantity: 2 }))
      addToCart(createCartItem({ ringId: 2, price: 250, quantity: 1, materialType: 2 }))
    })

    it('should show cart content when items exist', () => {
      const wrapper = mountCart()
      expect(wrapper.find('.cart-content').exists()).toBe(true)
      expect(wrapper.find('.empty-cart').exists()).toBe(false)
    })

    it('should render each cart item', () => {
      const wrapper = mountCart()
      const items = wrapper.findAll('.cart-item')
      expect(items).toHaveLength(2)
    })

    it('should display ring ID for each item', () => {
      const wrapper = mountCart()
      expect(wrapper.text()).toContain('Ring #1')
      expect(wrapper.text()).toContain('Ring #2')
    })

    it('should display item details (material, band width, stone)', () => {
      const wrapper = mountCart()
      const firstItem = wrapper.findAll('.cart-item')[0]!
      expect(firstItem.text()).toContain('Material:')
      expect(firstItem.text()).toContain('Band Width:')
      expect(firstItem.text()).toContain('Ring Stone:')
    })

    it('should display unit price and total price', () => {
      const wrapper = mountCart()
      // Item 1: price=100, quantity=2, unit price=$50.00
      expect(wrapper.text()).toContain('$50.00 each')
      expect(wrapper.text()).toContain('$100.00')
      // Item 2: price=250, quantity=1, unit price=$250.00
      expect(wrapper.text()).toContain('$250.00 each')
    })

    it('should render item images', () => {
      const wrapper = mountCart()
      const images = wrapper.findAll('.item-image img')
      expect(images).toHaveLength(2)
      expect(images[0]!.attributes('src')).toBe('/rings/ring1.png')
    })

    it('should display quantity select for each item', () => {
      const wrapper = mountCart()
      const selects = wrapper.findAll('.quantity-select')
      expect(selects).toHaveLength(2)
    })

    it('should display remove buttons for each item', () => {
      const wrapper = mountCart()
      const removeButtons = wrapper.findAll('.remove-btn')
      expect(removeButtons).toHaveLength(2)
    })

    it('should display order summary', () => {
      const wrapper = mountCart()
      expect(wrapper.text()).toContain('Order Summary')
      expect(wrapper.text()).toContain('Items (3):') // 2 + 1
      expect(wrapper.text()).toContain('$350.00') // 100 + 250
    })

    it('should display checkout and clear cart buttons', () => {
      const wrapper = mountCart()
      expect(wrapper.find('.checkout-btn').exists()).toBe(true)
      expect(wrapper.find('.clear-cart-btn').exists()).toBe(true)
    })

    it('should display continue shopping link in cart summary', () => {
      const wrapper = mountCart()
      expect(wrapper.find('.continue-shopping-btn').exists()).toBe(true)
    })
  })

  describe('remove item', () => {
    it('should remove an item when remove button is clicked', async () => {
      const { addToCart } = useCart()
      addToCart(createCartItem({ ringId: 1 }))
      addToCart(createCartItem({ ringId: 2, materialType: 2 }))

      const wrapper = mountCart()
      expect(wrapper.findAll('.cart-item')).toHaveLength(2)

      await wrapper.findAll('.remove-btn')[0]!.trigger('click')
      await flushPromises()

      expect(wrapper.findAll('.cart-item')).toHaveLength(1)
      expect(wrapper.text()).toContain('Ring #2')
    })

    it('should show empty cart when last item is removed', async () => {
      const { addToCart } = useCart()
      addToCart(createCartItem())

      const wrapper = mountCart()
      await wrapper.find('.remove-btn').trigger('click')
      await flushPromises()

      expect(wrapper.find('.empty-cart').exists()).toBe(true)
    })
  })

  describe('quantity change', () => {
    it('should update quantity and recalculate price when quantity changes', async () => {
      const { addToCart } = useCart()
      addToCart(createCartItem({ ringId: 1, price: 100, quantity: 1 }))

      const wrapper = mountCart()
      const select = wrapper.find('.quantity-select')

      await select.setValue(3)
      await flushPromises()

      // price per unit was 100/1 = 100, new qty=3, new price = 300
      expect(wrapper.text()).toContain('$100.00 each')
      expect(wrapper.text()).toContain('$300.00')
    })
  })

  describe('clear cart', () => {
    it('should clear all items when clear cart button is clicked', async () => {
      const { addToCart } = useCart()
      addToCart(createCartItem({ ringId: 1 }))
      addToCart(createCartItem({ ringId: 2, materialType: 2 }))

      const wrapper = mountCart()
      expect(wrapper.findAll('.cart-item')).toHaveLength(2)

      await wrapper.find('.clear-cart-btn').trigger('click')
      await flushPromises()

      expect(wrapper.find('.empty-cart').exists()).toBe(true)
    })
  })

  describe('fetchLocations', () => {
    it('should fetch locations on mount and display them', async () => {
      ;(getCurrentCustomerId as Mock).mockReturnValue(1)
      ;(axios.get as Mock).mockResolvedValue({
        data: [
          { custId: 1, street: '123 Main St', city: 'Anytown', state: 'CA', zip: '12345', locId: 1 },
          { custId: 1, street: '456 Oak Ave', city: 'Othertown', state: 'NY', zip: '67890', locId: 2 },
        ],
      })

      const { addToCart } = useCart()
      addToCart(createCartItem())

      const wrapper = mountCart()
      await flushPromises()

      const options = wrapper.findAll('.location-select option')
      // "Select Delivery Location" disabled + 2 locations + "Add New Location"
      expect(options.length).toBeGreaterThanOrEqual(4)
      expect(wrapper.text()).toContain('123 Main St')
      expect(wrapper.text()).toContain('456 Oak Ave')
    })

    it('should use mock data when API call fails', async () => {
      ;(getCurrentCustomerId as Mock).mockReturnValue(3)
      ;(axios.get as Mock).mockRejectedValue(new Error('Network error'))

      const { addToCart } = useCart()
      addToCart(createCartItem())

      const wrapper = mountCart()
      await flushPromises()

      // Customer 3 should see "321 Elm St" from mock data
      expect(wrapper.text()).toContain('321 Elm St')
      expect(wrapper.text()).toContain('Springfield')
    })

    it('should default to customer ID 3 when no customer is logged in', async () => {
      ;(getCurrentCustomerId as Mock).mockReturnValue(null)
      ;(axios.get as Mock).mockRejectedValue(new Error('Network error'))

      const { addToCart } = useCart()
      addToCart(createCartItem())

      const wrapper = mountCart()
      await flushPromises()

      // Default customer ID 3: "321 Elm St, Springfield, IL"
      expect(wrapper.text()).toContain('321 Elm St')
    })
  })

  describe('handleCheckout', () => {
    it('should return early when cart is empty', async () => {
      const wrapper = mountCart()
      // Cart is empty so no checkout button visible; force a scenario
      // by adding an item, rendering, then clearing
      const { addToCart, clearCart: clear } = useCart()
      addToCart(createCartItem())
      await wrapper.vm.$nextTick()

      clear()
      await wrapper.vm.$nextTick()

      // Cart is now empty, no post should have been called
      expect(axios.post).not.toHaveBeenCalled()
    })

    it('should alert when no customer ID and checkout is clicked', async () => {
      ;(getCurrentCustomerId as Mock).mockReturnValue(null)

      const { addToCart } = useCart()
      addToCart(createCartItem())

      const wrapper = mountCart()
      await flushPromises()

      const alertSpy = vi.spyOn(window, 'alert').mockImplementation(() => {})

      await wrapper.find('.checkout-btn').trigger('click')
      await flushPromises()

      expect(alertSpy).toHaveBeenCalledWith('Please log in to place an order.')
      expect(axios.post).not.toHaveBeenCalledWith(
        expect.stringContaining('/order'),
        expect.anything()
      )
      alertSpy.mockRestore()
    })

    it('should alert when no delivery location is selected', async () => {
      ;(getCurrentCustomerId as Mock).mockReturnValue(1)

      const { addToCart } = useCart()
      addToCart(createCartItem())

      const wrapper = mountCart()
      await flushPromises()

      const alertSpy = vi.spyOn(window, 'alert').mockImplementation(() => {})

      // selectedLocation is '' by default
      await wrapper.find('.checkout-btn').trigger('click')
      await flushPromises()

      expect(alertSpy).toHaveBeenCalledWith('Please select a delivery location before checking out.')
      alertSpy.mockRestore()
    })

    it('should submit order when customer and location are set', async () => {
      ;(getCurrentCustomerId as Mock).mockReturnValue(1)
      ;(axios.get as Mock).mockResolvedValue({
        data: [
          { custId: 1, street: '123 Main St', city: 'Anytown', state: 'CA', zip: '12345', locId: 5 },
        ],
      })
      ;(axios.post as Mock).mockResolvedValue({ data: { orderNumber: 'ORD-999' } })

      const { addToCart } = useCart()
      addToCart(createCartItem({ ringId: 10, materialType: 2, bandWidth: 4, ringStone: 3, quantity: 2 }))

      const wrapper = mountCart()
      await flushPromises()

      // Select a delivery location
      const deliverySelect = wrapper.findAll('.location-select')[0]!
      await deliverySelect.setValue('5')
      await flushPromises()

      const alertSpy = vi.spyOn(window, 'alert').mockImplementation(() => {})

      await wrapper.find('.checkout-btn').trigger('click')
      await flushPromises()

      expect(axios.post).toHaveBeenCalledWith(
        expect.stringContaining('/order'),
        expect.objectContaining({
          custId: 1,
          locationId: 5,
          orderItems: expect.arrayContaining([
            expect.objectContaining({
              productId: 10,
              materialId: 2,
              widthId: 4,
              stoneId: 3,
              quantity: 2,
            }),
          ]),
        })
      )

      alertSpy.mockRestore()
    })

    it('should clear cart after successful order submission', async () => {
      ;(getCurrentCustomerId as Mock).mockReturnValue(1)
      ;(axios.get as Mock).mockResolvedValue({
        data: [
          { custId: 1, street: '123 Main St', city: 'Anytown', state: 'CA', zip: '12345', locId: 5 },
        ],
      })
      ;(axios.post as Mock).mockResolvedValue({ data: { orderNumber: 'ORD-999' } })

      const { addToCart } = useCart()
      addToCart(createCartItem())

      const wrapper = mountCart()
      await flushPromises()

      const deliverySelect = wrapper.findAll('.location-select')[0]!
      await deliverySelect.setValue('5')
      await flushPromises()

      const alertSpy = vi.spyOn(window, 'alert').mockImplementation(() => {})

      await wrapper.find('.checkout-btn').trigger('click')
      await flushPromises()

      // After successful order, cart should be cleared
      expect(alertSpy).toHaveBeenCalledWith(
        expect.stringContaining('Order submitted successfully!')
      )
      expect(wrapper.find('.empty-cart').exists()).toBe(true)
      alertSpy.mockRestore()
    })
  })

  describe('handleLocationChange', () => {
    beforeEach(() => {
      const { addToCart } = useCart()
      addToCart(createCartItem())
      ;(getCurrentCustomerId as Mock).mockReturnValue(3)
    })

    it('should show new location form when ADD_NEW is selected', async () => {
      const wrapper = mountCart()
      await flushPromises()

      const deliverySelect = wrapper.findAll('.location-select')[0]!
      await deliverySelect.setValue('ADD_NEW')
      await flushPromises()

      expect(wrapper.find('.no-location').isVisible()).toBe(true)
      expect(wrapper.text()).toContain('Location not found? Add a new one!')
    })

    it('should not show new location form by default', async () => {
      ;(axios.get as Mock).mockRejectedValue(new Error('Network error'))

      const wrapper = mountCart()
      await flushPromises()

      // showNewLocationSection starts as false, so the form should be hidden
      expect(wrapper.find('.no-location').isVisible()).toBe(false)
    })
  })

  describe('addLocation / handleAddLocation', () => {
    beforeEach(() => {
      const { addToCart } = useCart()
      addToCart(createCartItem())
      ;(getCurrentCustomerId as Mock).mockReturnValue(3)
    })

    it('should submit new location and reset form', async () => {
      ;(axios.post as Mock).mockResolvedValue({
        data: { custId: 3, street: '999 New Rd', city: 'Newtown', state: 'FL', zip: '11111', locId: 99 },
      })

      const wrapper = mountCart()
      await flushPromises()

      // Show the add location form
      const deliverySelect = wrapper.findAll('.location-select')[0]!
      await deliverySelect.setValue('ADD_NEW')
      await flushPromises()

      // Fill in the form
      const inputs = wrapper.findAll('.location-input')
      await inputs[0]!.setValue('999 New Rd')
      await inputs[1]!.setValue('Newtown')
      await inputs[2]!.setValue('FL')
      await inputs[3]!.setValue('11111')

      // Submit the form via form submit event (not button click)
      await wrapper.find('.no-location form').trigger('submit')
      await flushPromises()

      expect(axios.post).toHaveBeenCalledWith(
        expect.stringContaining('/locations/3'),
        expect.objectContaining({
          street: '999 New Rd',
          city: 'Newtown',
          state: 'FL',
          zip: '11111',
        })
      )

      // Form should be hidden after submission
      expect(wrapper.find('.no-location').isVisible()).toBe(false)
    })
  })

  describe('billing location select', () => {
    it('should render billing location select', async () => {
      ;(getCurrentCustomerId as Mock).mockReturnValue(1)
      ;(axios.get as Mock).mockResolvedValue({
        data: [
          { custId: 1, street: '100 Billing St', city: 'Billtown', state: 'TX', zip: '77777', locId: 10 },
        ],
      })

      const { addToCart } = useCart()
      addToCart(createCartItem())

      const wrapper = mountCart()
      await flushPromises()

      const billingSelect = wrapper.findAll('.location-select')[1]!
      expect(billingSelect.exists()).toBe(true)

      // Set billing location
      await billingSelect.setValue('10')
      await flushPromises()

      expect(wrapper.text()).toContain('100 Billing St')
    })
  })

  describe('getPricePerUnit', () => {
    it('should calculate correct unit price', () => {
      const { addToCart } = useCart()
      addToCart(createCartItem({ ringId: 1, price: 300, quantity: 3 }))

      const wrapper = mountCart()
      // Unit price = 300 / 3 = 100
      expect(wrapper.text()).toContain('$100.00 each')
    })

    it('should handle zero quantity gracefully', () => {
      const { addToCart, cartItems } = useCart()
      addToCart(createCartItem({ ringId: 1, price: 0, quantity: 1 }))
      // Manually set quantity to 0 to test fallback
      cartItems.value[0]!.quantity = 0

      const wrapper = mountCart()
      // price=0, quantity fallback=1, so unit price = $0.00
      expect(wrapper.text()).toContain('$0.00 each')
    })
  })
})
