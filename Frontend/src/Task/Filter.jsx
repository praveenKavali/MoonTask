import { useState } from "react";
import backendCall from "../JsFiles/BaseAxios";
import DropDown from "../Components/DropDown";

export default function FilterTask({setResult}) {
    const PRIORITY_OPTIONS = [
        { label: 'Low Priority', value: 'LOW' },
        { label: 'Medium Priority', value: 'MEDIUM' },
        { label: 'High Priority', value: 'HIGH' }
    ];
    const STATUS_OPTIONS = [
        { label: 'Pending Task', value: 'DO' },
        { label: 'InProgress Task', value: 'InPROGRESS' },
        { label: 'Completed Task', value: 'COMPLETED' }
    ]
    const [formData, setFormData] = useState({
        priority: '',
        status: ''
    });
    const handleClick = async (e) => {
        const {id, value} = e.target;
        setFormData({...formData, [id]: value})
        try {
            const data = await backendCall.get(`task/${id}`,{
                params: {[id]: value}
            }).then(res => res.data);
            if (data) {
                setResult(data);
            }
        } catch (e) {
            console.log(`Error during mark as completed ${e}`);
        }
    }
    return (
        <div style={{
            textAlign: 'center',
            border: '1px solid white',
            width: '60%',
            margin: '1.5rem auto',
            borderRadius: '0.5rem'
        }}>
            <h2>Filter</h2>
            <DropDown
                id="priority"
                value={formData.priority}
                options={PRIORITY_OPTIONS}
                handleChange={e => handleClick(e)}
            />
            <DropDown
                id="status"
                value={formData.status}
                options={STATUS_OPTIONS}
                handleChange={e => handleClick(e)}
            />
        </div>
    )
}