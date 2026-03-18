import { useState } from "react"
import backendCall from "../JsFiles/BaseAxios";
import { useNavigate } from "react-router-dom";

export default function DeleteUser() {
    const[msg, setMsg] = useState("");
    const navigate = useNavigate();
    const handleClick = async(e) => {
        e.preventDefault();
        try {
            const data = await backendCall.delete('/delete').then(res => res.data);
            setMsg(data);
            if(data){
                setTimeout(() => {
                    setMsg('');
                    navigate('/');
                }, 3000);
            }
        } catch (ex) {
            console.log(`Error: ${ex}`);
        }
    }
    return(
        <>
            <div style={{
                width: '60%',
                textAlign: 'center',
                margin: '5rem auto'
            }}>
                <button onClick={handleClick} style={{
                    padding: '0.5rem',
                    borderRadius: '0.3rem'
                }}>Delete Account</button>
            </div>
            {msg && <div style={{
                width: '60%',
                textAlign: 'center',
                margin: '5rem auto',
                fontSize: '2rem'
            }}>{msg}</div>}
        </>
    )
}