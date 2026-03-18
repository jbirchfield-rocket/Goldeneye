import { describe, it, expect, beforeEach } from 'vitest'
import { clearCustomerIdCookie, getCurrentCustomerId, setCustomerIdCookie } from '../services/customerService'

describe('Customer cookie checks', () => {
  beforeEach(() => {
    // Clear cookies before each test
    document.cookie.split(';').forEach(c => {
      document.cookie = c.trim().split('=')[0] + '=; path=/; max-age=0'
    })
  })

  it('should set and get customer cookie correctly', () => {
    setCustomerIdCookie(1)
    expect(document.cookie).toContain("customerId=1")
  })

  it('should clear customer cookie', () => {
    setCustomerIdCookie(1)
    clearCustomerIdCookie()
    expect(document.cookie).not.toContain("customerId=1")
  })

  it('should get customerId from cookie when present', () => {
    setCustomerIdCookie(1)
    const id = getCurrentCustomerId()
    expect(id).toBe(1)
  })

  it('should return null when customerId cookie is not present', () => {
    const id = getCurrentCustomerId()
    expect(id).toBeNull()
  })
})
