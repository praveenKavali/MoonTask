import { Link } from "react-router-dom";
import Header from "../../components/header/Header";
import "./homePage.css";

export default function Homepage() {
    return (
        <div className="homepage-container">
            <Header />
            <>
                <h1 className="main-heading">Welcome to Moon Task</h1>
                <p> <span>Moon Task</span> is a task manager application. Where you can store your task.</p>
                <div className="link-container">
                    <Link to="/register">Registration</Link><br />
                    <Link to="/login">Login</Link>
                </div>
            </>
        </div>
    )
}