import { describe, it, expect } from 'vitest'

import { mount } from '@vue/test-utils'
import App from '../App.vue'

describe('App', () => {
  it('mounts renders properly', () => {
    const wrapper = mount(App)
    expect(wrapper.text()).toContain('HomePurchase RingsManage OrdersAbout Us Cart')
  })

  it('toggles menu properly', async () => {
    const wrapper = mount(App)
    const hamburger = wrapper.find('.hamburger-icon')

    // Menu should be closed initially
    expect(wrapper.find('.nav-content').classes()).not.toContain('menu-open')

    // Click hamburger to open menu
    await hamburger.trigger('click')
    expect(wrapper.find('.nav-content').classes()).toContain('menu-open')

    // Click hamburger again to close menu
    await hamburger.trigger('click')
    expect(wrapper.find('.nav-content').classes()).not.toContain('menu-open')
  })

  it('closes menu properly', async () => {
    const wrapper = mount(App)
    const hamburger = wrapper.find('.hamburger-icon')

    // Open the menu first
    await hamburger.trigger('click')
    expect(wrapper.find('.nav-content').classes()).toContain('menu-open')

    // Click a nav link to close the menu
    const homeLink = wrapper.find('.nav-content router-link')
    await homeLink.trigger('click')
    expect(wrapper.find('.nav-content').classes()).not.toContain('menu-open')
  })
})
