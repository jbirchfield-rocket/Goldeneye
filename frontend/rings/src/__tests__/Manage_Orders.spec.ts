import { describe, it, expect, beforeEach, vi, type Mock } from 'vitest'
import { mount, flushPromises } from '@vue/test-utils'
import { createRouter, createWebHistory } from 'vue-router'
import Manage_Orders from '../views/Manage_Orders.vue'

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

// Mock order data matching the component's interface
const mockOrders = [
  {
    orderId: '99001',
    customerName: 'Test Customer',
    orderDate: '2024-07-01',
    orderItems: [
      {
        orderItemId: 1,
        productName: 'Classic Band',
        materialName: 'Gold',
        width: 4,
        stoneName: 'Cubic Zirconia',
        quantity: 2,
        unitPrice: 599.98,
      },
    ],
    location: {
      custId: 5,
      street: '100 Test Blvd',
      city: 'Testville',
      state: 'TX',
      zip: '75000',
      locID: 10,
    },
    status: 'Shipped',
  },
  {
    orderId: '99002',
    customerName: 'Test Customer',
    orderDate: '2024-07-15',
    orderItems: [
      {
        orderItemId: 2,
        productName: 'Solitaire Ring',
        materialName: 'Platinum',
        width: 6,
        stoneName: 'Natural Diamond',
        quantity: 1,
        unitPrice: 899.99,
      },
      {
        orderItemId: 3,
        productName: 'Etched Band',
        materialName: 'Silver',
        width: 2,
        stoneName: 'Lab-Grown Diamond',
        quantity: 1,
        unitPrice: 449.99,
      },
    ],
    location: {
      custId: 5,
      street: '100 Test Blvd',
      city: 'Testville',
      state: 'TX',
      zip: '75000',
      locID: 10,
    },
    billLocation: {
      custId: 5,
      street: '200 Billing Ave',
      city: 'Billtown',
      state: 'CA',
      zip: '90210',
      locID: 20,
    },
    status: 'Delivered',
  },
]

function createTestRouter() {
  return createRouter({
    history: createWebHistory(),
    routes: [
      { path: '/', component: { template: '<div>Home</div>' } },
      { path: '/purchase-rings', component: { template: '<div>Purchase</div>' } },
      { path: '/manage-orders', component: { template: '<div>Manage Orders</div>' } },
    ],
  })
}

function mountManageOrders() {
  const router = createTestRouter()
  return mount(Manage_Orders, {
    global: {
      plugins: [router],
    },
  })
}

describe('Manage_Orders.vue', () => {
  beforeEach(() => {
    clearAllCookies()
    vi.clearAllMocks()
    ;(getCurrentCustomerId as Mock).mockReturnValue(5)
  })

  describe('loading state', () => {
    it('should display loading message initially', () => {
      // Keep the API pending (never resolve) to see loading state
      ;(axios.get as Mock).mockReturnValue(new Promise(() => {}))

      const wrapper = mountManageOrders()
      expect(wrapper.find('.loading-message').exists()).toBe(true)
      expect(wrapper.text()).toContain('Loading orders...')
    })

    it('should not display orders list while loading', () => {
      ;(axios.get as Mock).mockReturnValue(new Promise(() => {}))

      const wrapper = mountManageOrders()
      expect(wrapper.find('.orders-list').exists()).toBe(false)
    })

    it('should not show error message while loading', () => {
      ;(axios.get as Mock).mockReturnValue(new Promise(() => {}))

      const wrapper = mountManageOrders()
      expect(wrapper.find('.error-message').exists()).toBe(false)
    })
  })

  describe('page heading', () => {
    it('should display the "Manage Orders" heading', async () => {
      ;(axios.get as Mock).mockResolvedValue({ data: mockOrders })

      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.find('h1').text()).toBe('Manage Orders')
    })
  })

  describe('successful data loading', () => {
    beforeEach(() => {
      ;(axios.get as Mock).mockResolvedValue({ data: mockOrders })
    })

    it('should fetch orders for the current customer', async () => {
      mountManageOrders()
      await flushPromises()

      expect(axios.get).toHaveBeenCalledWith(expect.stringContaining('/orders/5'))
    })

    it('should hide loading message after data loads', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.find('.loading-message').exists()).toBe(false)
    })

    it('should render order cards', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      const cards = wrapper.findAll('.order-card')
      expect(cards).toHaveLength(2)
    })

    it('should display order IDs', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.text()).toContain('99001')
      expect(wrapper.text()).toContain('99002')
    })

    it('should display product names for order items', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.text()).toContain('Classic Band')
      expect(wrapper.text()).toContain('Solitaire Ring')
      expect(wrapper.text()).toContain('Etched Band')
    })

    it('should display material names', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.text()).toContain('Gold')
      expect(wrapper.text()).toContain('Platinum')
      expect(wrapper.text()).toContain('Silver')
    })

    it('should display widths in mm', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.text()).toContain('4 mm')
      expect(wrapper.text()).toContain('6 mm')
      expect(wrapper.text()).toContain('2 mm')
    })

    it('should display stone names', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.text()).toContain('Cubic Zirconia')
      expect(wrapper.text()).toContain('Natural Diamond')
      expect(wrapper.text()).toContain('Lab-Grown Diamond')
    })

    it('should display quantities', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.text()).toContain('2')
      expect(wrapper.text()).toContain('1')
    })

    it('should display prices formatted with dollar sign', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.text()).toContain('$599.98')
      expect(wrapper.text()).toContain('$899.99')
      expect(wrapper.text()).toContain('$449.99')
    })

    it('should display delivery location', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.text()).toContain('100 Test Blvd')
      expect(wrapper.text()).toContain('Testville')
      expect(wrapper.text()).toContain('TX')
      expect(wrapper.text()).toContain('75000')
    })

    it('should display billing location when present', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.text()).toContain('200 Billing Ave')
      expect(wrapper.text()).toContain('Billtown')
      expect(wrapper.text()).toContain('CA')
      expect(wrapper.text()).toContain('90210')
    })

    it('should display order dates', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.text()).toContain('2024-07-01')
      expect(wrapper.text()).toContain('2024-07-15')
    })

    it('should render order header columns', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      const headerItems = wrapper.findAll('.order-header .header-item')
      expect(headerItems.length).toBeGreaterThanOrEqual(7)
      const headerTexts = headerItems.map((h) => h.text())
      expect(headerTexts).toContain('Order #')
      expect(headerTexts).toContain('Ring Type')
      expect(headerTexts).toContain('Material')
      expect(headerTexts).toContain('Width')
      expect(headerTexts).toContain('Stone')
      expect(headerTexts).toContain('Quantity')
      expect(headerTexts).toContain('Price')
    })
  })

  describe('order totals', () => {
    beforeEach(() => {
      ;(axios.get as Mock).mockResolvedValue({ data: mockOrders })
    })

    it('should display the correct total for a single-item order', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      // Order 99001 has 1 item with unitPrice 599.98 → total $599.98
      expect(wrapper.text()).toContain('$599.98')
    })

    it('should display the correct total for a multi-item order', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      // Order 99002 has items 899.99 + 449.99 = 1349.98
      expect(wrapper.text()).toContain('$1349.98')
    })

    it('should handle order with no items gracefully (total $0.00)', async () => {
      const orderWithNoItems = {
        orderId: '99003',
        customerName: 'Empty Order Customer',
        orderDate: '2024-08-01',
        orderItems: [],
        location: {
          custId: 5,
          street: '100 Test Blvd',
          city: 'Testville',
          state: 'TX',
          zip: '75000',
          locID: 10,
        },
      }
      ;(axios.get as Mock).mockResolvedValue({ data: [orderWithNoItems] })

      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.text()).toContain('$0.00')
    })
  })

  describe('multiple order items per order', () => {
    beforeEach(() => {
      ;(axios.get as Mock).mockResolvedValue({ data: mockOrders })
    })

    it('should render multiple rows for an order with multiple items', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      // Order 99002 has 2 items
      const orderCards = wrapper.findAll('.order-card')
      const secondCard = orderCards[1]!
      // Each view (narrow + wide) renders the items, check the wide view
      const wideRows = secondCard.findAll('.wide-view .order-row')
      expect(wideRows).toHaveLength(2)
    })

    it('should only show order ID on the first row of each order', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      const orderCards = wrapper.findAll('.order-card')
      const secondCard = orderCards[1]!
      const wideRows = secondCard.findAll('.wide-view .order-row')

      // First row should have the orderId
      const firstRowIdCell = wideRows[0]!.find('.order-id span')
      expect(firstRowIdCell.exists()).toBe(true)
      expect(firstRowIdCell.text()).toBe('99002')

      // Second row should not have an orderId span
      const secondRowIdCell = wideRows[1]!.find('.order-id span')
      expect(secondRowIdCell.exists()).toBe(false)
    })
  })

  describe('empty orders state', () => {
    it('should display empty orders message when no orders exist', async () => {
      ;(axios.get as Mock).mockResolvedValue({ data: [] })

      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.find('.empty-orders').exists()).toBe(true)
      expect(wrapper.text()).toContain('No orders found.')
    })

    it('should show "Start Shopping" link when no orders exist', async () => {
      ;(axios.get as Mock).mockResolvedValue({ data: [] })

      const wrapper = mountManageOrders()
      await flushPromises()

      const shopLink = wrapper.find('.shop-link')
      expect(shopLink.exists()).toBe(true)
      expect(shopLink.text()).toContain('Start Shopping')
      expect(shopLink.attributes('href')).toBe('/purchase-rings')
    })

    it('should not show orders list when no orders exist', async () => {
      ;(axios.get as Mock).mockResolvedValue({ data: [] })

      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.find('.orders-list').exists()).toBe(false)
    })
  })

  describe('error state with fallback data', () => {
    it('should use fallback mock data when API fails', async () => {
      ;(getCurrentCustomerId as Mock).mockReturnValue(1)
      ;(axios.get as Mock).mockRejectedValue(new Error('Network error'))

      const wrapper = mountManageOrders()
      await flushPromises()

      // Component falls back to mock data filtered by customer ID 1
      expect(wrapper.find('.orders-list').exists()).toBe(true)
      expect(wrapper.text()).toContain('12345')
    })

    it('should filter fallback mock data by customer ID', async () => {
      ;(getCurrentCustomerId as Mock).mockReturnValue(2)
      ;(axios.get as Mock).mockRejectedValue(new Error('Network error'))

      const wrapper = mountManageOrders()
      await flushPromises()

      // Customer 2 only has order 12347 in the mock data
      expect(wrapper.text()).toContain('12347')
      expect(wrapper.text()).not.toContain('12345')
    })

    it('should show empty state when fallback data has no orders for customer', async () => {
      ;(getCurrentCustomerId as Mock).mockReturnValue(999)
      ;(axios.get as Mock).mockRejectedValue(new Error('Network error'))

      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.find('.empty-orders').exists()).toBe(true)
      expect(wrapper.text()).toContain('No orders found.')
    })

    it('should not be in loading state after error', async () => {
      ;(axios.get as Mock).mockRejectedValue(new Error('Network error'))

      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.find('.loading-message').exists()).toBe(false)
    })
  })

  describe('default customer ID', () => {
    it('should default to customer ID 2 when no customer ID is set', async () => {
      ;(getCurrentCustomerId as Mock).mockReturnValue(null)
      ;(axios.get as Mock).mockResolvedValue({ data: [] })

      mountManageOrders()
      await flushPromises()

      expect(axios.get).toHaveBeenCalledWith(expect.stringContaining('/orders/2'))
    })
  })

  describe('narrow and wide view rendering', () => {
    beforeEach(() => {
      ;(axios.get as Mock).mockResolvedValue({ data: mockOrders })
    })

    it('should render both narrow and wide view sections', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.find('.narrow-view').exists()).toBe(true)
      expect(wrapper.find('.wide-view').exists()).toBe(true)
    })

    it('should show labels in narrow view cells', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      const narrowView = wrapper.find('.narrow-view')
      expect(narrowView.text()).toContain('Ring Type:')
      expect(narrowView.text()).toContain('Material:')
      expect(narrowView.text()).toContain('Width:')
      expect(narrowView.text()).toContain('Stone:')
      expect(narrowView.text()).toContain('Quantity:')
      expect(narrowView.text()).toContain('Price:')
    })

    it('should show order ID with "Order #:" prefix in narrow view', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      const narrowView = wrapper.find('.narrow-view')
      expect(narrowView.text()).toContain('Order #: 99001')
    })
  })

  describe('additional info section', () => {
    beforeEach(() => {
      ;(axios.get as Mock).mockResolvedValue({ data: mockOrders })
    })

    it('should display delivery location info', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      const additionalInfo = wrapper.findAll('.additional-info')
      expect(additionalInfo.length).toBeGreaterThan(0)
      expect(wrapper.text()).toContain('Delivery Location:')
    })

    it('should display billing location when available', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.text()).toContain('Billing Location:')
      expect(wrapper.text()).toContain('200 Billing Ave, Billtown, CA 90210')
    })

    it('should not show billing location when not available', async () => {
      const orderWithoutBilling = [
        {
          ...mockOrders[0],
          billLocation: undefined,
        },
      ]
      ;(axios.get as Mock).mockResolvedValue({ data: orderWithoutBilling })

      const wrapper = mountManageOrders()
      await flushPromises()

      const additionalInfo = wrapper.find('.additional-info')
      // Only delivery location should be present, not billing
      const locationDivs = additionalInfo.findAll('.order-location')
      expect(locationDivs).toHaveLength(1)
      expect(locationDivs[0]!.text()).toContain('Delivery Location:')
    })

    it('should display the order date', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      expect(wrapper.text()).toContain('Date:')
      expect(wrapper.text()).toContain('2024-07-01')
    })

    it('should not show date label when orderDate is missing', async () => {
      const orderWithoutDate = [
        {
          orderId: '99099',
          customerName: 'No Date Customer',
          orderItems: [
            {
              productName: 'Test Band',
              materialName: 'Gold',
              width: 4,
              stoneName: 'Cubic Zirconia',
              quantity: 1,
              unitPrice: 100.0,
            },
          ],
          location: {
            custId: 5,
            street: '100 Test Blvd',
            city: 'Testville',
            state: 'TX',
            zip: '75000',
            locID: 10,
          },
        },
      ]
      ;(axios.get as Mock).mockResolvedValue({ data: orderWithoutDate })

      const wrapper = mountManageOrders()
      await flushPromises()

      const additionalInfo = wrapper.find('.additional-info')
      expect(additionalInfo.find('.order-date').exists()).toBe(false)
    })

    it('should display order total with "Total:" label', async () => {
      const wrapper = mountManageOrders()
      await flushPromises()

      const totals = wrapper.findAll('.order-total')
      expect(totals.length).toBeGreaterThan(0)
      expect(totals[0]!.text()).toContain('Total:')
    })
  })
})
