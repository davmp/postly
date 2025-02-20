import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import axios from '@/service/http'

interface User {
  username: string
  token: string
  profilePictureUrl: string
}

export const useAuth = defineStore('auth', () => {
  const userRef = ref<User | null>(
    localStorage.getItem('user')
      ? (JSON.parse(localStorage.getItem('user') as string) as User)
      : null,
  )
  const user = computed(() => userRef.value)
  const isAuth = computed(() => !!user.value)

  function setUser(data: User | null) {
    if (data && data.username) {
      localStorage.setItem('user', JSON.stringify(data))
      userRef.value = data
    } else {
      localStorage.removeItem('user')
      userRef.value = null
    }
  }

  async function login({ username, password }) {
    try {
      const response = await axios.post(
        '/api/auth/login',
        {
          username,
          password,
        },
        {
          headers: { 'Content-Type': 'application/json' },
        },
      )
      setUser(response.data)
      return response
    } catch (error) {
      console.error('Failed to login: ', error)
      throw error
    }
  }

  async function register({
    username,
    password,
    picture,
  }: {
    username: string
    password: string
    picture: File | null
  }) {
    try {
      console.warn('register::', picture?.name)
      const response = await axios.post(
        '/api/auth/register',
        {
          username,
          password,
          picture,
        },
        {
          headers: { 'Content-Type': 'multipart/form-data' },
        },
      )
      setUser(response.data)
      return response
    } catch (error) {
      console.error('Failed to register: ', error)
      throw error
    }
  }

  async function logout() {
    setUser(null)
  }

  return {
    isAuth,
    user,
    setUser,
    login,
    register,
    logout,
  }
})
