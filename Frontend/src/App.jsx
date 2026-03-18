import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./Forms/LoginPage";
import RegisterPage from "./Forms/RegisterForm";
import UpdateUserDetails from "./Forms/UpdateUser";
import DeleteUser from "./Pages/DeleteUserDetails";
import TaskHomePage from "./Pages/TaskHomePage";
import HomePage from "./Pages/HomePage";
import CreateTask from "./Forms/TaskPage";
import MarkAsComplete from "./Task/TaskCompleted";
import UpdatePriority from "./Task/UpdatePriority";
import UpdateStatus from "./Task/UpdateStatus";
import { Navbar } from "./Header/Navbar";
import { useState } from "react";
import TaskFeatures from "./Task/TaskOptions";

function App() {
    const [isLogged, setIsLogged] = useState(false);
    return (
        <BrowserRouter>
            <Navbar isLogged={isLogged} />
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/login" element={<LoginPage setIsLogged= {setIsLogged} />}/>
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/update" element={<UpdateUserDetails setIsLogged= {setIsLogged}/>} />
                <Route path="/delete" element={<DeleteUser />} />
                <Route path="/task" element={<TaskHomePage />} />
                <Route path="/task/create" element={<CreateTask />} />
                <Route path="/task/complete/:id" element={<MarkAsComplete />} />
                <Route path="/task/update-priority/:id" element={<UpdatePriority />} />
                <Route path="/task/update-status/:id" element={<UpdateStatus />} />
            </Routes>
        </BrowserRouter>
    )
}

export default App;