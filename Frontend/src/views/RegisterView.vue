<script lang="ts" setup>
import { Header, Form, Footer } from '@/components/register'
import { useAuth } from '@/api/auth'
import { toast } from 'vue3-toastify'
import router from '@/router'

const auth = useAuth()

async function handleCreateUser(user: {
  username: string
  password: string
  picture: File | null
}) {
  const response = await auth.register(user)

  if (response.status !== 200) {
    toast.error('register failed')
    console.error(response)
  } else {
    router.push({ name: 'home' })
    toast.success('Welcome back')
  }
}
</script>

<template>
  <main class="register">
    <Header />
    <Form @create-user="handleCreateUser" />
    <Footer />
  </main>
</template>

<style lang="css" scoped>
.register {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}
</style>
