<template>
  <section class="page">
    <div class="card">
      <h1>Terminal</h1>
      <p>Interactive container shell</p>

      <TerminalComponent />
    </div>
  </section>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import TerminalComponent from "@/components/Terminal.vue";

export default defineComponent({
  name: "TerminalPage",
  components: {
    TerminalComponent
  }
});
</script>

<style scoped>
.page {
  padding: 30px;
  display: flex;
  justify-content: center;
}

.card {
  width: 100%;
  max-width: 1000px;
  background: #1b1b1b;
  border-radius: 12px;
  padding: 20px;
  border: 1px solid #333;
}

h1 {
  margin-bottom: 5px;
  color: #fff;
}

p {
  margin-bottom: 20px;
  color: #aaa;
}
</style>