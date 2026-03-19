import { describe, it, expect, beforeEach, vi, type Mock } from 'vitest'
import { mount, flushPromises } from '@vue/test-utils'
import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'

// Mock axios
vi.mock('axios', () => ({
  default: {
    get: vi.fn(),
    post: vi.fn(),
  },
}))

// Mock customerService
vi.mock('@/services/customerService', () => ({
  setCustomerIdCookie: vi.fn(),
  getCurrentCustomerId: vi.fn(),
}))

import axios from 'axios'
import { setCustomerIdCookie, getCurrentCustomerId } from '@/services/customerService'

// Helper to clear all cookies
function clearAllCookies() {
  document.cookie.split(';').forEach((c) => {
    document.cookie = c.trim().split('=')[0] + '=; path=/; max-age=0'
  })
}

// Mock customer data
const mockCustomers = [
  { custId: 1, name: 'Alice Johnson', active: 1 },
  { custId: 2, name: 'Bob Smith', active: 1 },
  { custId: 3, name: 'Charlie Brown', active: 1 },
]

function createTestRouter() {
  return createRouter({
    history: createWebHistory(),
    routes: [
      { path: '/', component: { template: '<div>Home</div>' } },
      { path: '/purchase-rings', component: { template: '<div>Purchase</div>' } },
      { path: '/manage-orders', component: { template: '<div>Orders</div>' } },
      { path: '/cart', component: { template: '<div>Cart</div>' } },
    ],
  })
}

function mountHome() {
  const router = createTestRouter()
  return mount(Home, {
    global: {
      plugins: [router],
    },
  })
}

describe('Home.vue', () => {
  beforeEach(() => {
    clearAllCookies()
    vi.clearAllMocks()
    ;(getCurrentCustomerId as Mock).mockReturnValue(null)
  })

  describe('initial rendering', () => {
    beforeEach(() => {
      ;(axios.get as Mock).mockResolvedValue({ data: mockCustomers })
    })

    it('should render the home container', async () => {
      const wrapper = mountHome()
      await flushPromises()
      expect(wrapper.find('.home-container').exists()).toBe(true)
    })

    it('should display the Goldeneye logo image', async () => {
      const wrapper = mountHome()
      await flushPromises()
      const logo = wrapper.find('.logo-image')
      expect(logo.exists()).toBe(true)
      expect(logo.attributes('alt')).toBe('Goldeneye Logo')
    })

    it('should display the welcome message', async () => {
      const wrapper = mountHome()
      await flushPromises()
      expect(wrapper.find('.welcome-message').text()).toBe('Welcome to Goldeneye')
    })

    it('should display the company description', async () => {
      const wrapper = mountHome()
      await flushPromises()
      const description = wrapper.find('.description')
      expect(description.exists()).toBe(true)
      expect(description.text()).toContain('dedicated to those who have an eye for golden perfection')
      expect(description.text()).toContain('highest quality rings')
    })

    it('should render the customer selector label', async () => {
      const wrapper = mountHome()
      await flushPromises()
      const label = wrapper.find('label[for="customer-select"]')
      expect(label.exists()).toBe(true)
      expect(label.text()).toBe('Customer:')
    })

    it('should render the customer select dropdown', async () => {
      const wrapper = mountHome()
      await flushPromises()
      const select = wrapper.find('#customer-select')
      expect(select.exists()).toBe(true)
    })
  })

  describe('customer dropdown - API success', () => {
    beforeEach(() => {
      ;(axios.get as Mock).mockResolvedValue({ data: mockCustomers })
    })

    it('should fetch customers on mount', async () => {
      mountHome()
      await flushPromises()
      expect(axios.get).toHaveBeenCalledWith(expect.stringContaining('/customers'))
    })

    it('should populate dropdown with fetched customers', async () => {
      const wrapper = mountHome()
      await flushPromises()

      const options = wrapper.findAll('#customer-select option')
      // "Select Customer" disabled option + 3 customers + "Add New Customer"
      expect(options).toHaveLength(5)
      expect(options[1]!.text()).toBe('Alice Johnson')
      expect(options[2]!.text()).toBe('Bob Smith')
      expect(options[3]!.text()).toBe('Charlie Brown')
    })

    it('should include a disabled "Select Customer" placeholder option', async () => {
      const wrapper = mountHome()
      await flushPromises()

      const placeholderOption = wrapper.find('#customer-select option[disabled]')
      expect(placeholderOption.exists()).toBe(true)
      expect(placeholderOption.text()).toBe('Select Customer')
    })

    it('should include an "Add New Customer" option', async () => {
      const wrapper = mountHome()
      await flushPromises()

      const options = wrapper.findAll('#customer-select option')
      const addNewOption = options[options.length - 1]!
      expect(addNewOption.text()).toBe('Add New Customer')
      expect(addNewOption.attributes('value')).toBe('ADD_NEW')
    })

    it('should set customer option values to custId', async () => {
      const wrapper = mountHome()
      await flushPromises()

      const options = wrapper.findAll('#customer-select option')
      expect(options[1]!.attributes('value')).toBe('1')
      expect(options[2]!.attributes('value')).toBe('2')
      expect(options[3]!.attributes('value')).toBe('3')
    })
  })

  describe('customer dropdown - API failure (fallback)', () => {
    it('should use fallback mock customers when API fails', async () => {
      ;(axios.get as Mock).mockRejectedValue(new Error('Network error'))

      const wrapper = mountHome()
      await flushPromises()

      const options = wrapper.findAll('#customer-select option')
      // "Select Customer" + 3 fallback customers + "Add New Customer"
      expect(options).toHaveLength(5)
      expect(options[1]!.text()).toBe('Customer 1')
      expect(options[2]!.text()).toBe('Customer 2')
      expect(options[3]!.text()).toBe('Customer 3')
    })
  })

  describe('customer selection', () => {
    beforeEach(() => {
      ;(axios.get as Mock).mockResolvedValue({ data: mockCustomers })
    })

    it('should call setCustomerIdCookie when a customer is selected', async () => {
      const wrapper = mountHome()
      await flushPromises()

      const select = wrapper.find('#customer-select')
      await select.setValue('2')
      await select.trigger('change')

      expect(setCustomerIdCookie).toHaveBeenCalledWith(2)
    })

    it('should show new customer section when "Add New Customer" is selected', async () => {
      const wrapper = mountHome()
      await flushPromises()

      // Verify new customer section is not visible initially
      expect(wrapper.find('.new-customer-section').exists()).toBe(false)

      // Simulate selecting "Add New Customer"
      const select = wrapper.find('#customer-select')
      const changeEvent = new Event('change')
      Object.defineProperty(changeEvent, 'target', {
        value: { value: 'ADD_NEW' },
        writable: false,
      })
      select.element.dispatchEvent(changeEvent)
      await flushPromises()

      expect(wrapper.find('.new-customer-section').exists()).toBe(true)
    })

    it('should render input and button in new customer section', async () => {
      const wrapper = mountHome()
      await flushPromises()

      // Trigger "Add New Customer"
      const select = wrapper.find('#customer-select')
      const changeEvent = new Event('change')
      Object.defineProperty(changeEvent, 'target', {
        value: { value: 'ADD_NEW' },
        writable: false,
      })
      select.element.dispatchEvent(changeEvent)
      await flushPromises()

      expect(wrapper.find('.new-customer-input').exists()).toBe(true)
      expect(wrapper.find('.add-customer-button').exists()).toBe(true)
    })

    it('should hide new customer section when a regular customer is selected', async () => {
      const wrapper = mountHome()
      await flushPromises()

      // First show the section
      const select = wrapper.find('#customer-select')
      let changeEvent = new Event('change')
      Object.defineProperty(changeEvent, 'target', {
        value: { value: 'ADD_NEW' },
        writable: false,
      })
      select.element.dispatchEvent(changeEvent)
      await flushPromises()
      expect(wrapper.find('.new-customer-section').exists()).toBe(true)

      // Then select a regular customer
      changeEvent = new Event('change')
      Object.defineProperty(changeEvent, 'target', {
        value: { value: '1' },
        writable: false,
      })
      select.element.dispatchEvent(changeEvent)
      await flushPromises()
      expect(wrapper.find('.new-customer-section').exists()).toBe(false)
    })

    it('should load saved customer ID on mount', async () => {
      ;(getCurrentCustomerId as Mock).mockReturnValue(2)

      mountHome()
      await flushPromises()

      expect(getCurrentCustomerId).toHaveBeenCalled()
    })
  })

  describe('add new customer', () => {
    beforeEach(() => {
      ;(axios.get as Mock).mockResolvedValue({ data: mockCustomers })
    })

    it('should add a new customer when API succeeds', async () => {
      const newCustomer = { custId: 4, name: 'Diana Prince' }
      ;(axios.post as Mock).mockResolvedValue({ data: newCustomer })

      const wrapper = mountHome()
      await flushPromises()

      // Open "Add New Customer" section
      const select = wrapper.find('#customer-select')
      const changeEvent = new Event('change')
      Object.defineProperty(changeEvent, 'target', {
        value: { value: 'ADD_NEW' },
        writable: false,
      })
      select.element.dispatchEvent(changeEvent)
      await flushPromises()

      // Fill in and submit
      const input = wrapper.find('.new-customer-input')
      await input.setValue('Diana Prince')
      await wrapper.find('.add-customer-button').trigger('click')
      await flushPromises()

      expect(axios.post).toHaveBeenCalledWith(
        expect.stringContaining('/customers'),
        { name: 'Diana Prince' },
      )
      expect(setCustomerIdCookie).toHaveBeenCalledWith(4)
    })

    it('should add the new customer to the dropdown after successful creation', async () => {
      const newCustomer = { custId: 4, name: 'Diana Prince' }
      ;(axios.post as Mock).mockResolvedValue({ data: newCustomer })

      const wrapper = mountHome()
      await flushPromises()

      // Open "Add New Customer" section
      const select = wrapper.find('#customer-select')
      const changeEvent = new Event('change')
      Object.defineProperty(changeEvent, 'target', {
        value: { value: 'ADD_NEW' },
        writable: false,
      })
      select.element.dispatchEvent(changeEvent)
      await flushPromises()

      // Fill in and submit
      await wrapper.find('.new-customer-input').setValue('Diana Prince')
      await wrapper.find('.add-customer-button').trigger('click')
      await flushPromises()

      // Verify the customer was added to dropdown
      const options = wrapper.findAll('#customer-select option')
      const customerNames = options.map((o) => o.text())
      expect(customerNames).toContain('Diana Prince')
    })

    it('should alert when trying to add a customer with empty name', async () => {
      const alertSpy = vi.spyOn(window, 'alert').mockImplementation(() => {})

      const wrapper = mountHome()
      await flushPromises()

      // Open "Add New Customer" section
      const select = wrapper.find('#customer-select')
      const changeEvent = new Event('change')
      Object.defineProperty(changeEvent, 'target', {
        value: { value: 'ADD_NEW' },
        writable: false,
      })
      select.element.dispatchEvent(changeEvent)
      await flushPromises()

      // Click add without entering a name
      await wrapper.find('.add-customer-button').trigger('click')
      await flushPromises()

      expect(alertSpy).toHaveBeenCalledWith('Please enter a valid customer name.')
      expect(axios.post).not.toHaveBeenCalled()

      alertSpy.mockRestore()
    })

    it('should alert when trying to add a customer with whitespace-only name', async () => {
      const alertSpy = vi.spyOn(window, 'alert').mockImplementation(() => {})

      const wrapper = mountHome()
      await flushPromises()

      // Open section
      const select = wrapper.find('#customer-select')
      const changeEvent = new Event('change')
      Object.defineProperty(changeEvent, 'target', {
        value: { value: 'ADD_NEW' },
        writable: false,
      })
      select.element.dispatchEvent(changeEvent)
      await flushPromises()

      await wrapper.find('.new-customer-input').setValue('   ')
      await wrapper.find('.add-customer-button').trigger('click')
      await flushPromises()

      expect(alertSpy).toHaveBeenCalledWith('Please enter a valid customer name.')
      expect(axios.post).not.toHaveBeenCalled()

      alertSpy.mockRestore()
    })

    it('should alert when API fails to add customer', async () => {
      ;(axios.post as Mock).mockRejectedValue(new Error('Server error'))
      const alertSpy = vi.spyOn(window, 'alert').mockImplementation(() => {})

      const wrapper = mountHome()
      await flushPromises()

      // Open section
      const select = wrapper.find('#customer-select')
      const changeEvent = new Event('change')
      Object.defineProperty(changeEvent, 'target', {
        value: { value: 'ADD_NEW' },
        writable: false,
      })
      select.element.dispatchEvent(changeEvent)
      await flushPromises()

      await wrapper.find('.new-customer-input').setValue('Diana Prince')
      await wrapper.find('.add-customer-button').trigger('click')
      await flushPromises()

      expect(alertSpy).toHaveBeenCalledWith('Failed to add customer. Please try again.')

      alertSpy.mockRestore()
    })

    it('should clear the input field after successfully adding a customer', async () => {
      const newCustomer = { custId: 5, name: 'Eve Torres' }
      ;(axios.post as Mock).mockResolvedValue({ data: newCustomer })

      const wrapper = mountHome()
      await flushPromises()

      // Open section
      const select = wrapper.find('#customer-select')
      const changeEvent = new Event('change')
      Object.defineProperty(changeEvent, 'target', {
        value: { value: 'ADD_NEW' },
        writable: false,
      })
      select.element.dispatchEvent(changeEvent)
      await flushPromises()

      const input = wrapper.find('.new-customer-input')
      await input.setValue('Eve Torres')
      await wrapper.find('.add-customer-button').trigger('click')
      await flushPromises()

      expect((input.element as HTMLInputElement).value).toBe('')
    })
  })
})
