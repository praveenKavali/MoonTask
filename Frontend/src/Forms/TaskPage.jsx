import { useState } from "react";
import { Form } from "../Components/Form";
import { handleChange } from "../JsFiles/handleChange";
import DropDown from "../Components/DropDown";
import { Link, useNavigate } from "react-router-dom";
import backendCall from "../JsFiles/BaseAxios";

export default function CreateTask() {
    const PRIORITY_OPTIONS = [
        { label: 'Low Priority', value: 'LOW' },
        { label: 'Medium Priority', value: 'MEDIUM' },
        { label: 'High Priority', value: 'HIGH' }
    ];
    const STATUS_OPTIONS = [
        { label: 'Pending Task', value: 'DO' },
        { label: 'InProgress Task', value: 'InPROGRESS' },
        { label: 'Completed Task', value: 'COMPLETED' }
    ];
    const [formData, setFormData] = useState({
        name: '',
        priority: 'LOW',
        status: 'DO',
        description: ''
    });

    const [msg, setMsg] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);
        const data = Object.fromEntries(formData);
        try {
            const res = await backendCall.post('/task/create', data);
            if (res) {
                setMsg(res.data);
                setTimeout(() => {
                    setMsg('');
                    navigate('/task');
                }, 3000);
            }
        } catch (ex) {
            console.log(`Error: ${ex}`);
        }
    }

    return (
        <div>
            <form onSubmit={handleSubmit} style={{
                width: '60%',
                margin: '0 auto',
                backgroundColor: 'white',
                color: 'black',
                textAlign: "right",
                borderRadius: '5px'
            }}>
                <Link to="/task" style={{
                    padding: '0 10px',
                    backgroundColor: 'red',
                    color: 'white',
                    textDecoration: 'none'
                }}>X</Link>
                <Form
                    id="name"
                    placeholder="Name of the task"
                    handleChange={e => handleChange(e, formData, setFormData)}
                    value={formData.name}
                />
                <DropDown
                    id="priority"
                    value={formData.priority}
                    options={PRIORITY_OPTIONS}
                    handleChange={e => handleChange(e, formData, setFormData)}
                />
                <DropDown
                    id="status"
                    value={formData.status}
                    options={STATUS_OPTIONS}
                    handleChange={e => handleChange(e, formData, setFormData)}
                />
                <div style={{
                    width: '60%',
                    margin: '0 auto',
                    display: 'flex',
                    justifyContent: 'space-evenly',
                    alignItems: 'center'
                }}>
                    <label htmlFor="description" style={{fontSize: '1rem'}}>Description</label>
                    <textarea
                        name="description"
                        id="description"
                        rows="3"
                        placeholder="Description about your task"
                        handleChange={e => handleChange(e, formData, setFormData)}
                        style={{
                            border: '1px solid black',
                            borderRadius: '5px'
                        }}
                    />
                </div>
                <div style={{textAlign: 'center'}}>
                    <button type="submit" style={{
                        width: '10%',
                        padding: '5px',
                        margin: '10px 0',
                        backgroundColor: 'black',
                        color: 'white',
                        borderRadius: '5px'
                    }}>submit</button>
                </div>
            </form>
            {msg && <div style={{
                width: '60%',
                margin: '1rem auto',
                backgroundColor: 'white',
                color: 'black',
                textAlign: "center",
                borderRadius: '5px',
                fontSize: '1rem'
            }}>{msg}</div>}
        </div>
    )
}