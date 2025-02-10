import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from '@/service/http'

const baseUrl = import.meta.env.VITE_API_BASE_URL

interface User {
  username: string
}

export const useAuth = defineStore('auth', () => {
  const user = ref(localStorage.getItem('user'))
  const isAuth = ref(!!user.value)

  function setUser(user: User | null) {
    if (user && user.username) {
      localStorage.setItem('user', JSON.stringify(user))
      isAuth.value = true
    } else {
      localStorage.removeItem('user')
      isAuth.value = false
    }
  }

  async function login({ username, password }) {
    try {
      const response = await axios.post('/api/auth/login', { username, password })
      setUser(response.data)
      return response
    } catch (error) {
      console.error('Failed to login: ', error)
      throw error
    }
  }

  async function logout({ state }) {
    state.user = null
  }

  return {
    isAuth,
    user,
    setUser,
    login,
    logout,
  }
})
