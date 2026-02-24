import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import api from "./js.files/Fetcher";
import Form from "../components/Form";

export default function AuthHandler({ type }) {
    const isRegister = type === "register";
    const navigate = useNavigate();
    const [msg, setMsg] = useState('');
    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData =Object.fromEntries(new FormData(e.target));
        try {
            const response = await api.post(`/${type}`, formData);
            if (response.status === 200 || response.status === 201) {
                if (isRegister) {
                    setMsg(response.data);
                    setTimeout(() => {
                        setMsg('');
                        navigate('/login');
                    }, 3000);
                } else {
                    setMsg("Welcome to Moon bank a task manager application.");
                    localStorage.setItem('token', response.token);
                    setTimeout(() => {
                        setMsg('');
                        navigate("/task")
                    }, 3000);
                }
            }
        } catch {
            setMsg('Registration failed!. Please try again.')
        }
    }
    return (
        <>
            {msg && <div>{msg}</div>}
            <Form type={type} onSubmitAction={handleSubmit} />
        </>
    )
}