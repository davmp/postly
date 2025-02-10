<script lang="ts" setup>
import { Input } from '../reuse/input'
import { Button } from '../reuse/button'
import { ref } from 'vue'

const username = ref('')
const password = ref('')

const emit = defineEmits(['create-user'])

const handleSubmit = (e: Event) => {
  e.preventDefault()

  if (!username.value || !password.value) {
    alert('Please fill in all fields')
    return
  }

  const user = {
    username: username.value,
    password: password.value,
  }

  emit('create-user', user)

  username.value = ''
  password.value = ''
}
</script>

<template>
  <form class="form" v-on:submit="handleSubmit">
    <Input v-model:value="username" placeholder="Username" class="input" />
    <Input v-model:value="password" placeholder="Password" type="password" class="input" />
    <Button type="submit"> Sign In </Button>
  </form>
</template>

<style lang="css" scoped>
.form {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  width: 100%;
  max-width: 300px;
}
</style>
