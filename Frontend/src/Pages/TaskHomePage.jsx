import { Link } from "react-router-dom";
import TaskCard from "../Components/Task";
import { useAllTask } from "../JsFiles/TaskAPI";
import SearchComponent from "../Task/SearchTask";
import FilterTask from "../Task/Filter";
import { useEffect, useState } from "react";

export default function TaskHomePage() {
    const [result, setResult] = useState([]);
    const { data, isMutation, error } = useAllTask();
    useEffect(() => {
        if (data) {
            setResult(data);
        }
    }, [data]);
    return (
        <>
            <SearchComponent setResult={setResult} />
            <Link to="/task/create" style={{
                fontSize: '1rem',
                width: '0.3rem',
                padding: '0 0.1rem',
                margin: '0 0.1rem',
                border: '0.5rem solid aqua',
                backgroundColor: 'aqua',
                color: 'white',
                borderRadius: '0.2rem',
                textDecoration: 'none'
            }}>Add Task</Link>
            <FilterTask setResult={setResult} />
            {isMutation && <div>Tasks are loading...</div>}
            {error && <div>Something went wrong please try again</div>}
            {result && <TaskCard data={result} />}
        </>
    )
}