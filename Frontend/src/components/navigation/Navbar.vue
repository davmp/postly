<script setup lang="ts">
import { RouterLink } from 'vue-router'
import { Input } from '../reuse/input'
import { Button } from '../reuse/button'
import searchIcon from '@/assets/icons/search.svg?raw'
import { useAuth } from '@/api/auth'
import { storeToRefs } from 'pinia'

const auth = useAuth()
const { isAuth, user } = storeToRefs(auth)
</script>

<template>
  <header class="navbar">
    <div class="wrapper">
      <RouterLink to="/" class="links">
        <img class="logo" src="/postly_logo.png" alt="Logo" />
        <p>Postly</p>
      </RouterLink>

      <!-- <div class="searchbar">
        <div class="img" v-html="searchIcon" />
        <input type="text" placeholder="Search" />
      </div> -->
      <Input type="text" placeholder="Search" :imgSrc="searchIcon" imgClass="img" />
      <div class="action" v-if="!isAuth">
        <Button @click="$router.push('/login')" class="button">Sign In</Button>
      </div>

      <!-- <div class="profile" v-if="isAuth" @click="$router.push('/profile')"> -->
      <div class="profile" v-if="isAuth" @click="auth.logout()">
        <p>{{ user?.username }}</p>
        <img :src="user?.profilePictureUrl" />
      </div>
    </div>
  </header>
</template>

<style scoped>
.navbar {
  position: fixed;
  inset: 0 0 auto 0;

  width: 100%;
  height: 3rem;
  background-color: var(--foreground);

  display: flex;
  justify-content: center;
  gap: 2rem;

  & .wrapper {
    width: 100%;
    max-width: 800px;
    padding-inline: 1rem;

    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  & .links {
    height: 100%;

    display: flex;
    align-items: center;

    & .logo {
      --size: 3rem;
      width: var(--size);
      height: var(--size);
    }

    & p {
      pointer-events: none;
    }
  }

  & .action {
    height: 100%;

    display: flex;
    align-items: center;

    & .button {
      background-color: var(--primary);
      color: var(--primary-content);
    }
  }

  & .profile {
    height: 100%;

    display: flex;
    align-items: center;
    gap: 0.25rem;
    font-size: 15px;

    & img {
      --size: 2rem;
      width: var(--size);
      height: var(--size);

      border-radius: 0.5rem;
      border: 2px solid var(--border);
      cursor: pointer;
    }
  }
}
</style>
