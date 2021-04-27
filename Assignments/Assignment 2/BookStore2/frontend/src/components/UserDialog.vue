<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Add user" : "Edit user" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.username" label="Username" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteUser">Delete user</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserDialog",
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.users
          .create({
            username: this.user.username,
            password: this.user.password,
            email: this.user.email,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.users
          .edit({
            username: this.user.username,
            password: this.user.password,
            email: this.user.email,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteUser(){
      api.user
        .delete({
          username: this.user.username,
          password: this.user.password,
          email: this.user.email,
      })
      .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.user.id;
    },
  },
};
</script>

<style scoped></style>
