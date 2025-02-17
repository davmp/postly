<script setup lang="ts">
import { RouterView } from 'vue-router'
import Navbar from './components/navigation/Navbar.vue'
import 'vue3-toastify/dist/index.css'
</script>

<template>
  <Navbar />
  <RouterView />
</template>

<style scoped></style>
