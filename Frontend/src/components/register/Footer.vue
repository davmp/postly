<template>
  <footer class="footer">
    <p>Already have an account? <router-link to="/login">Login</router-link></p>
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
