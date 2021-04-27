import axios from "axios";

export const BASE_URL = "http://localhost:8089/api";
export const HTTP = axios.create({
  baseURL: BASE_URL,
});

export function encodeParams(p) {
  const queryParamsObject = {};
  for (const key of Object.keys(p)) {
    const filterBook = p[key];
    queryParamsObject[key] = filterBook.toString();
  }
  return queryParamsObject;
}

export default function authHeader() {
  let user = JSON.parse(localStorage.getItem("user"));
  if (user && user.token) {
    return { Authorization: "Bearer " + user.token };
  } else {
    return {};
  }
}
