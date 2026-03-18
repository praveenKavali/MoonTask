import { Link, useLocation, useNavigate } from "react-router-dom";
import useSWRMutation from "swr/mutation";
import backendCall from "../JsFiles/BaseAxios";
import { useState } from "react";

export default function MarkAsComplete() {
    const location = useLocation();
    const TaskId = location.state?.TaskId;
    console.log(`task id in complete task method ${TaskId}`);

    const navigate = useNavigate();
    const [msg, setMsg] = useState('');
    const patchComplete = async(url, {arg}) => {
        try {
            const data = await backendCall.patch(`${url}/${arg}`).then(res => res.data);
            setMsg(data);
            return data;
        } catch(e) {
            console.log(`Error during mark as completed ${e}`);
        }
    }
    const{trigger, isMutating, error} = useSWRMutation('/task/complete', patchComplete,{
        onSuccess: () => {
            setTimeout(() => {
                navigate('/task')
            }, 3000);
        }
    });
    return (
        <div style={{
                         fontSize: '1rem',
                         width: '60%',
                         margin: '0 auto',
                         display: 'flex',
                         flexDirection: 'column',
                         alignItems: "center",
                         gap: '1rem',
                         border: '0.2rem solid white',
                         textDecoration: 'none'
        }}>
            <p>Do you completed the task?</p>
            <button 
                onClick={() => trigger(TaskId)}
                disabled={isMutating}
                style={{
                                textDecoration: 'none',
                                        padding: '0.4rem 1.2rem',
                                        borderRadius: '0.5rem',
                                        color: 'black',
                                        backgroundColor: 'white'
                            }}
            >Yes</button>
            <Link to='/task' style={{
                textDecoration: 'none',
                        padding: '0.2rem 1rem',
                        borderRadius: '0.5rem',
                        color: 'black',
                        backgroundColor: 'white',
                        marginBottom: '0.5rem'
            }}>No</Link>
            {error && 'something went wrong please try again'}
            {isMutating ? 'Completing...' : `${msg}`}
        </div>
    )
}