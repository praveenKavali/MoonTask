import { jwtDecode } from 'jwt-decode';

export default function usernameExtraction() {
    const token = localStorage.getItem('token');
    try {
        if (token) {
            const decode = jwtDecode(token);
            return decode.name;
        }
    } catch {
        console.log('username extraction failed');
        return null;
    }
}
