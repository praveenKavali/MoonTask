import { useState } from "react";
import backendCall from "../JsFiles/BaseAxios";
import useSWRMutation from "swr/mutation";
import { useLocation, useNavigate } from "react-router-dom";


export default function UpdatePriority() {
    const location = useLocation();
    const TaskId = location.state?.TaskId;
    const currentPriority = location.state?.priority;
    const PRIORITY_OPTIONS = [
        'LOW', 'MEDIUM', 'HIGH'
    ];
    const navigate = useNavigate();
    const [msg, setMsg] = useState('');
    const update = async (url, { arg }) => {
        const { taskId, newPriority } = arg;
        try {
            const response = await backendCall.patch(`/task/${TaskId}/priority`, null, {
                params: { priority: newPriority }
            }).then(res => res.data);
            setMsg(response);
            return response;
        } catch (e) {
            console.log(`Error during updating priority ${e}`);
        }
    }
    const { trigger, isMutating, error } = useSWRMutation(`/task/${TaskId}/priority`, update, {
        onSuccess: () => {
            setTimeout(() => {
                navigate('/task')
            }, 3000);
        }
    });
    return (
        <>
            {
                PRIORITY_OPTIONS.filter((value) => value !== currentPriority).map((opt, index) => (
                    <div style={{
                        fontSize: '1rem',
                        width: '40%',
                        margin: '0 auto',
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: "center",
                        gap: '1rem',
                        textDecoration: 'none',
                        marginBottom: '0.2rem'
                    }}>
                        <button key={index} onClick={() => trigger({ taskId: TaskId, newPriority: opt })}>{opt}</button>
                    </div>
                ))
            }
            {error && <div>Something went wrong please try again</div>}
            {isMutating ? 'Updating priority...' : `${msg}`}
        </>
    )
}