<script lang="ts" setup>
import { Header, Form, Footer } from '@/components/login'
import { useAuth } from '@/api/auth';

const auth = useAuth();

function handleCreateUser(user: { username: string; password: string }) {
  auth.login(user);
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
