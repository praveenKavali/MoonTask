
import useSWRMutation from "swr/mutation";
import backendCall from "../JsFiles/BaseAxios";
import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

export default function UpdateStatus() {
    const STATUS_OPTIONS = ['PENDING', 'InPROGRESS', 'COMPLETE'];
    const [msg, setMsg] = useState('');
    const navigate = useNavigate();
    const location = useLocation();
    const TaskId = location.state?.TaskId;
    const status = location.state?.status;

    const updateStatus = async (url, { arg }) => {
        const { taskId, newStatus } = arg;
        try {
            const response = await backendCall.patch(`/task/${taskId}/status`, null, {
                params: { status: newStatus }
            }).then(res => res.data);
            setMsg(response);
            return response;
        } catch (e) {
            console.log(`Error during updating priority ${e}`);
        }
    }

    const { trigger, isMutating, error } = useSWRMutation(`/task/${TaskId}/status`, updateStatus, {
        onSuccess: (() => {
            setTimeout(() => {
                navigate('/task');
            }, 3000);
        })
    });
    return(
        <>
            {
                STATUS_OPTIONS.filter((value) => value !== status).map((opt, index) => (
                <button key={index} onClick={() => trigger({ taskId: TaskId, newStatus: opt })}>{opt}</button>
                ))
            }
            {isMutating ? 'Updating status...': msg}
            {error && <div>something went wrong. please try again</div>}
        </>
    )
}
