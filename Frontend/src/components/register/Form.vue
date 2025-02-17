<script lang="ts" setup>
import { Input } from '../reuse/input'
import { Button } from '../reuse/button'
import { ref } from 'vue'
import { toast } from 'vue3-toastify'

const username = ref('')
const password = ref('')
const picture = ref<File | null>(null)

const emit = defineEmits(['create-user'])

const handleSubmit = (e: Event) => {
  e.preventDefault()

  if (!username.value || !password.value) {
    toast.warning('Please fill in all fields')
    return
  }

  const user = {
    username: username.value,
    password: password.value,
    picture: picture.value,
  }

  emit('create-user', user)

  // username.value = ''
  // password.value = ''
  // picture.value = null
}
</script>

<template>
  <form class="form" v-on:submit="handleSubmit">
    <Input v-model:value="username" placeholder="Username" class="input" />
    <Input v-model:value="password" placeholder="Password" type="password" class="input" />
    <input :value="picture" type="file" class="input" />
    <Button type="submit"> Register </Button>
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
