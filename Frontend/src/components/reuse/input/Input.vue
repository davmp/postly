<template>
  <div class="input-field">
    <div v-if="!!imgSrc" v-html="imgSrc" :class="imgClass" />
    <input
      :type="type"
      :id="id"
      :value="value"
      @input="$emit('update:value', ($event.target as HTMLInputElement).value)"
      :placeholder="placeholder"
      :required="required"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export interface InputProps {
  id: string
  label?: string
  type?: string
  value?: string | number
  placeholder?: string
  required?: boolean
  imgSrc?: object
  imgClass?: string
}

export default defineComponent({
  name: 'Input',
  props: {
    id: {
      type: String,
      default: '',
    },
    label: {
      type: String,
      default: '',
    },
    type: {
      type: String,
      default: 'text',
    },
    value: {
      type: [String, Number],
      default: '',
    },
    placeholder: {
      type: String,
      default: '',
    },
    required: {
      type: Boolean,
      default: false,
    },
    imgSrc: {
      type: String,
      default: '',
    },
    imgClass: {
      type: String,
      default: 'img',
    },
  },
  emits: ['update:value'],
})
</script>

<style>
.input-field {
  width: 100%;
  max-width: 300px;
  height: 2.3rem;

  display: flex;
  align-items: center;

  background-color: var(--foreground);
  border: 2px solid var(--border);
  border-radius: 0.5rem;
  padding: 0 0.7rem;
  cursor: text;

  &:has(input:focus) {
    border-color: var(--border-dark);
  }

  .img {
    --size: 1.2rem;
    width: var(--size);
    height: var(--size);
    margin-right: 0.5rem;

    & svg {
      width: 100%;
      height: 100%;

      & path {
        stroke-width: 1px;
        stroke: var(--border-dark);
        fill: var(--copy-light) !important;
      }
    }
  }

  & input {
    width: 100%;
    height: 100%;
    border: none;
    background-color: transparent;
    color: var(--copy);

    font-family: var(--font-family);
    font-size: 14px;

    &:focus {
      outline: none;
    }
  }
}
</style>
