<template>
  <footer class="footer">
    <p>Don't have an account? <router-link to="/register">Register</router-link></p>
  </footer>
</template>

<style scoped>
.footer {
  text-align: center;
  font-size: 0.8rem;

  & a {
    color: var(--primary);

    &:hover {
      text-decoration: underline;
    }
  }
}
</style>
