<template>
  <NavBar />
  <router-view />
</template>

<script setup lang="ts">
import NavBar from './components/navbar.vue'

//import TerminalComponent from './TerminalComponent.vue' (<TerminalComponent />)
<script lang="ts">
import TerminalComponent from "./components/Terminal.vue";

export default {
  components: {
    TerminalComponent
  }
};
</script>
