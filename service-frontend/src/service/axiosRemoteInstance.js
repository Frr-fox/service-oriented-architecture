import axios from "axios";

const instance = axios.create({
    baseURL: "https://localhost:32471/api/v1",
    headers: {
        "Content-Type": "application/json",
    },
});

export default instance;