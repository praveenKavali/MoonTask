
import backendCall from "../JsFiles/BaseAxios";
import AuthPage from "./AuthPage";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

export default function Auth({ isRegister, setIsLogged }) {
  const url = isRegister ? '/register' : '/login';
  const navigate = useNavigate();
  const [msg, setMsg] = useState('');

  const handleSubmit = async (e) => {
    console.log(`form data: ${e}`)
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const data = Object.fromEntries(formData);
    try {
      const result = await backendCall.post(`${url}`, data);
      if (result) {
        if (isRegister) {
          setMsg(result.data);
          setTimeout(() => {
            setMsg('');
            navigate('/login');
          }, 3000);
        } else {
          setMsg('Welcome back to moon task.');
          localStorage.setItem('token', result.data);
          setTimeout(() => {
            setMsg('');
            setIsLogged(true);
            navigate('/task');
          }, 3000)
        }
      }
    } catch (ex) {
      console.log(`Error: ${ex}`);
    }
  };

  return (
    <>
      {msg && <div style={{
        width: '60%',
        margin: '0 auto',
        backgroundColor: 'white',
        color: 'green',
        textAlign: "center",
        borderRadius: '0.5rem',
        fontSize: '1.5rem'
      }}>{msg}</div >}
      {!msg && <AuthPage handleSubmit={handleSubmit} isRegister={isRegister} />}
    </>
  );
}
