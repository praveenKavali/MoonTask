import { useState } from "react";
import backendCall from "../JsFiles/BaseAxios";
import AuthPage from "../UserLogin/AuthPage";
import { Link, useNavigate } from "react-router-dom";

export default function UpdateUserDetails({setIsLogged}) {
    const[msg, setMsg] = useState("");
    const navigate = useNavigate();
    const handleSubmit = async(e) => {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);
        const data = Object.fromEntries(formData);
        try {
            const res = await backendCall.put('/update', data);
            if(res) {
                setMsg(res.data);
                setTimeout(() => {
                    setMsg('');
                    setIsLogged(true);
                    navigate('/task');
                }, 3000);
            }
        } catch (ex) {
            console.log(`Error: ${ex}`);
        }

    }
    return(
        <div style={{
            textAlign: 'center'
        }}>
            <Link to='/task' style={{
                textDecoration: 'none',
                marginTop: '10px',
                padding: '5px 15px',
                color: 'black',
                backgroundColor: 'white',
                borderRadius: '3px'
            }}>Back</Link>
            {msg ? <div>{msg}</div> : <AuthPage isUpdating={true} isRegister={true} handleSubmit={handleSubmit} />}
        </div>
    )
}