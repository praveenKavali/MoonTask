import { Link } from "react-router-dom"

export default function Homepage(){
    return (
        <div>
            <h1>Welcome to Moon Task</h1>
            <p> <span>Moon Task</span> is a task manager application. Where you can store your task.</p>
            <Link to="/register">Registration</Link><br />
            <Link to="/login">Login</Link>
        </div>
    )
}