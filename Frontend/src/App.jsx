import { BrowserRouter, Route, Routes } from "react-router-dom";
import Homepage from "./features/HomePage";
import Registration from "./features/auth/Registration";
import Login from "./features/auth/Login";
import Header from "./components/Header";

function App() {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Homepage />}/>
                <Route path="/register" element={<Registration />} />
                <Route path="/login" element={<Login />} />
                <Route path="/task" element={<Header />} />
            </Routes>
        </BrowserRouter>
    )
}

export default App
