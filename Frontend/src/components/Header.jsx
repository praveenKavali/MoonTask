import { useEffect, useEffectEvent, useState } from "react";
import UserInput from "./common/Input/Input";
import usernameExtraction from "../services/js.files/UsernameExtraction";


export default function Header() {
    const [name, setName] = useState(null);
    const [task, setTask] = useState('');
    function handleChange(e) {
        setTask(e.target.value);
    }

    const username = useEffectEvent((data) => {
        setName(data);
    })

    useEffect(() => {
        const data = usernameExtraction();
        if (data && data !== name) {
            username(data);
        }
    }, [name]);

    return (
        <header>
            <h1>Welcome to Moon Task {name}</h1>
            <UserInput
                type="text"
                name="searchbar"
                value={task}
                onChange={handleChange}
                placeholder="Search for task"
            />
        </header>
    )
}