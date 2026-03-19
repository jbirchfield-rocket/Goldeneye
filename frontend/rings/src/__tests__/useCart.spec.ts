import { describe, it, expect, beforeEach, vi } from 'vitest'
import { useCart, type CartItem } from '../services/useCart'

// Helper to clear all cookies
function clearAllCookies() {
  document.cookie.split(';').forEach((c) => {
    document.cookie = c.trim().split('=')[0] + '=; path=/; max-age=0'
  })
}

// Helper to set a raw cookie for testing
function setRawCookie(name: string, value: string) {
  document.cookie = `${name}=${encodeURIComponent(value)}; path=/`
}

// Factory for creating a CartItem
function createCartItem(overrides: Partial<CartItem> = {}): CartItem {
  return {
    ringId: 1,
    ringImage: 'ring1.png',
    materialType: 1,
    bandWidth: 5,
    ringStone: 2,
    quantity: 1,
    price: 100,
    addedAt: Date.now(),
    ...overrides,
  }
}

describe('useCart', () => {
  beforeEach(() => {
    // Clear cookies and reset cart state before each test
    clearAllCookies()
    // Reset the singleton state by clearing the cart
    const { clearCart } = useCart()
    clearCart()
  })

  describe('initialization', () => {
    it('should return an empty cart when no cookie exists', () => {
      const { cartItems, cartCount, cartTotal } = useCart()
      expect(cartItems.value).toEqual([])
      expect(cartCount.value).toBe(0)
      expect(cartTotal.value).toBe(0)
    })

    it('should load cart from cookie on initialization', () => {
      const item = createCartItem()
      setRawCookie('goldeneye_cart', JSON.stringify([item]))

      const { loadCart, cartItems } = useCart()
      loadCart()

      expect(cartItems.value).toHaveLength(1)
      expect(cartItems.value[0]!.ringId).toBe(item.ringId)
    })

    it('should handle invalid JSON in cookie gracefully', () => {
      const consoleSpy = vi.spyOn(console, 'error').mockImplementation(() => {})
      setRawCookie('goldeneye_cart', 'not-valid-json')

      const { loadCart, cartItems } = useCart()
      loadCart()

      expect(cartItems.value).toEqual([])
      expect(consoleSpy).toHaveBeenCalledWith('Error parsing cart cookie:', expect.any(SyntaxError))
      consoleSpy.mockRestore()
    })

    it('should handle non-array JSON in cookie', () => {
      setRawCookie('goldeneye_cart', JSON.stringify({ not: 'an array' }))

      const { loadCart, cartItems } = useCart()
      loadCart()

      expect(cartItems.value).toEqual([])
    })
  })

  describe('addToCart', () => {
    it('should add a new item to an empty cart', () => {
      const { addToCart, cartItems } = useCart()
      const item = createCartItem()

      addToCart(item)

      expect(cartItems.value).toHaveLength(1)
      expect(cartItems.value[0]!.ringId).toBe(1)
      expect(cartItems.value[0]!.quantity).toBe(1)
      expect(cartItems.value[0]!.price).toBe(100)
    })

    it('should set addedAt timestamp when adding a new item', () => {
      const { addToCart, cartItems } = useCart()
      const now = Date.now()
      const item = createCartItem({ addedAt: 0 })

      addToCart(item)

      expect(cartItems.value[0]!.addedAt).toBeGreaterThanOrEqual(now)
    })

    it('should increment quantity when adding an item with the same configuration', () => {
      const { addToCart, cartItems } = useCart()
      const item = createCartItem({ quantity: 2 })

      addToCart(item)
      addToCart(createCartItem({ quantity: 3 }))

      expect(cartItems.value).toHaveLength(1)
      expect(cartItems.value[0]!.quantity).toBe(5)
    })

    it('should add a separate item when configuration differs', () => {
      const { addToCart, cartItems } = useCart()

      addToCart(createCartItem({ ringId: 1, materialType: 1 }))
      addToCart(createCartItem({ ringId: 1, materialType: 2 }))

      expect(cartItems.value).toHaveLength(2)
    })

    it('should add a separate item when bandWidth differs', () => {
      const { addToCart, cartItems } = useCart()

      addToCart(createCartItem({ bandWidth: 5 }))
      addToCart(createCartItem({ bandWidth: 10 }))

      expect(cartItems.value).toHaveLength(2)
    })

    it('should add a separate item when ringStone differs', () => {
      const { addToCart, cartItems } = useCart()

      addToCart(createCartItem({ ringStone: 1 }))
      addToCart(createCartItem({ ringStone: 3 }))

      expect(cartItems.value).toHaveLength(2)
    })

    it('should persist the cart to a cookie after adding', () => {
      const { addToCart } = useCart()
      addToCart(createCartItem())

      expect(document.cookie).toContain('goldeneye_cart')
    })
  })

  describe('removeFromCart', () => {
    it('should remove an item by index', () => {
      const { addToCart, removeFromCart, cartItems } = useCart()

      addToCart(createCartItem({ ringId: 1 }))
      addToCart(createCartItem({ ringId: 2 }))

      removeFromCart(0)

      expect(cartItems.value).toHaveLength(1)
      expect(cartItems.value[0]!.ringId).toBe(2)
    })

    it('should clear the cookie when the last item is removed', () => {
      const { addToCart, removeFromCart, cartItems } = useCart()

      addToCart(createCartItem())
      removeFromCart(0)

      expect(cartItems.value).toHaveLength(0)
      // Cookie should be cleared (expired)
      expect(document.cookie).not.toContain('goldeneye_cart')
    })
  })

  describe('updateQuantity', () => {
    it('should update quantity and recalculate price', () => {
      const { addToCart, updateQuantity, cartItems } = useCart()
      addToCart(createCartItem({ quantity: 1, price: 100 }))

      updateQuantity(0, 3, 100)

      expect(cartItems.value[0]!.quantity).toBe(3)
      expect(cartItems.value[0]!.price).toBe(300)
    })

    it('should remove the item when quantity is set to 0', () => {
      const { addToCart, updateQuantity, cartItems } = useCart()
      addToCart(createCartItem())

      updateQuantity(0, 0, 100)

      expect(cartItems.value).toHaveLength(0)
    })

    it('should remove the item when quantity is negative', () => {
      const { addToCart, updateQuantity, cartItems } = useCart()
      addToCart(createCartItem())

      updateQuantity(0, -1, 100)

      expect(cartItems.value).toHaveLength(0)
    })

    it('should persist updated cart to cookie', () => {
      const { addToCart, updateQuantity } = useCart()
      addToCart(createCartItem({ quantity: 1, price: 50 }))

      updateQuantity(0, 5, 50)

      expect(document.cookie).toContain('goldeneye_cart')
    })
  })

  describe('clearCart', () => {
    it('should remove all items from the cart', () => {
      const { addToCart, clearCart, cartItems } = useCart()

      addToCart(createCartItem({ ringId: 1 }))
      addToCart(createCartItem({ ringId: 2 }))

      clearCart()

      expect(cartItems.value).toHaveLength(0)
    })

    it('should clear the cookie when cart is cleared', () => {
      const { addToCart, clearCart } = useCart()
      addToCart(createCartItem())

      clearCart()

      expect(document.cookie).not.toContain('goldeneye_cart')
    })
  })

  describe('cartCount', () => {
    it('should return 0 for an empty cart', () => {
      const { cartCount } = useCart()
      expect(cartCount.value).toBe(0)
    })

    it('should return the total quantity of all items', () => {
      const { addToCart, cartCount } = useCart()

      addToCart(createCartItem({ ringId: 1, quantity: 2 }))
      addToCart(createCartItem({ ringId: 2, quantity: 3 }))

      expect(cartCount.value).toBe(5)
    })

    it('should update when items are added', () => {
      const { addToCart, cartCount } = useCart()

      expect(cartCount.value).toBe(0)
      addToCart(createCartItem({ quantity: 4 }))
      expect(cartCount.value).toBe(4)
    })
  })

  describe('cartTotal', () => {
    it('should return 0 for an empty cart', () => {
      const { cartTotal } = useCart()
      expect(cartTotal.value).toBe(0)
    })

    it('should return the sum of all item prices', () => {
      const { addToCart, cartTotal } = useCart()

      addToCart(createCartItem({ ringId: 1, price: 100 }))
      addToCart(createCartItem({ ringId: 2, price: 250 }))

      expect(cartTotal.value).toBe(350)
    })

    it('should treat missing price as 0', () => {
      const { addToCart, cartTotal } = useCart()

      addToCart(createCartItem({ ringId: 1, price: 0 }))

      expect(cartTotal.value).toBe(0)
    })
  })

  describe('saveCart (cookie persistence)', () => {
    it('should save cart items to cookie when items exist', () => {
      const { addToCart } = useCart()
      addToCart(createCartItem({ ringId: 1, price: 100 }))

      // Verify cookie is set
      expect(document.cookie).toContain('goldeneye_cart')

      // Reload and verify data survived
      const { loadCart, cartItems } = useCart()
      loadCart()

      expect(cartItems.value).toHaveLength(1)
      expect(cartItems.value[0]!.ringId).toBe(1)
    })

    it('should expire the cookie when cart becomes empty', () => {
      const { addToCart, clearCart } = useCart()
      addToCart(createCartItem())

      expect(document.cookie).toContain('goldeneye_cart')

      clearCart()

      expect(document.cookie).not.toContain('goldeneye_cart')
    })
  })

  describe('loadCart', () => {
    it('should load a previously saved cart', () => {
      const items = [
        createCartItem({ ringId: 1, price: 100 }),
        createCartItem({ ringId: 2, price: 200 }),
      ]
      setRawCookie('goldeneye_cart', JSON.stringify(items))

      const { loadCart, cartItems, cartTotal } = useCart()
      loadCart()

      expect(cartItems.value).toHaveLength(2)
      expect(cartTotal.value).toBe(300)
    })

    it('should set empty cart when cookie is absent', () => {
      const { loadCart, cartItems } = useCart()
      loadCart()

      expect(cartItems.value).toEqual([])
    })
  })
})
