import axios from 'axios';

const backendCall = axios.create({
    baseURL: 'http://localhost:8080',
});

backendCall.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if(token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
})

export default backendCall;