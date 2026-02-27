import { BrowserRouter, Route, Routes } from "react-router-dom";
import Homepage from "./features/homePage/HomePage";
import Registration from "./features/auth/Registration";
import Login from "./features/auth/Login";
import "./App.css";
import Task from "./features/task/taskpage";

function App() {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Homepage />}/>
                <Route path="/register" element={<Registration />} />
                <Route path="/login" element={<Login />} />
                <Route path="/task" element={<Task />} />
            </Routes>
        </BrowserRouter>
    )
}

export default App
