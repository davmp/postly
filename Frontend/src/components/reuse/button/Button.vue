<template>
  <button>
    <slot> Button </slot>
  </button>
</template>

<script lang="ts" setup></script>

<style lang="css" scopped>
button {
  min-height: 2rem;
  height: 2rem;

  padding: 0 1rem;
  border-radius: 0.5rem;
  font-weight: 500;
  font-size: 14px;

  background-color: var(--primary);
  color: var(--primary-content);
  font-family: var(--font-family);

  display: flex;
  align-items: center;
  justify-content: center;
  outline: none;
  border: none;
  cursor: pointer;
}
</style>
