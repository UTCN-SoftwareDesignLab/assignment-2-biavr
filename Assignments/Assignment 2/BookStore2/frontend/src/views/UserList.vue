<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addUser">Add User</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
    ></v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :user="selectedUser"
      @refresh="refreshList"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "UserList",
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", value: "email" },
        { text: "Roles", value: "roles" },
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {},
  async created() {
    this.users = await api.users.allUsers();
  },
  addUser(){
    this.dialogVisible = true;
  }
};
</script>

<style scoped></style>
