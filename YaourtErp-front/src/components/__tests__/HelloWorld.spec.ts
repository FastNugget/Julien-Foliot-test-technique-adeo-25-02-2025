import { describe, it, expect } from 'vitest'

import { mount } from '@vue/test-utils'
import ComputeView from '../../views/ComputeView.vue'

describe('ComputeView', () => {
  it('renders properly', () => {
    const wrapper = mount(ComputeView, { props: { msg: 'Hello Vitest' } })
    expect(wrapper.text()).toContain('Hello Vitest')
  })
})
