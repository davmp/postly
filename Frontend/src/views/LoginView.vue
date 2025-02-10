<script lang="ts" setup>
import { Header, Form, Footer } from '@/components/login'
import { useAuth } from '@/api/auth'
import { toast } from 'vue3-toastify'
import router from '@/router'

const auth = useAuth()

async function handleCreateUser(user: { username: string; password: string }) {
  const response = await auth.login(user)

  if (response.status !== 200) {
    toast.error('Login failed')
    console.error(response)
  } else {
    toast.success('Welcome back')
    router.push({ name: 'home' })
  }
}
</script>

<template>
  <main class="login">
    <Header />
    <Form @create-user="handleCreateUser" />
    <Footer />
  </main>
</template>

<style lang="css" scoped>
.login {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}
</style>
