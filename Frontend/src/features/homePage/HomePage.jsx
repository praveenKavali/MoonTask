import { Link } from "react-router-dom";
import Header from "../../components/header/Header";
import styles from "./homepage.module.css";

export default function Homepage() {
    return (
        <>
            <Header />
            <div className={styles['homepage-container']}>
                <h1 className={styles['main-heading']}>Welcome to Moon Task.</h1>
                <p>A simple task manager application for task management. Your all in one platform to organise work, projects and goal with ease.Set priority and track progress.</p>
                <div className={styles['link-container']}>
                    <Link to="/register">Registration</Link><br />
                    <Link to="/login">Login</Link>
                </div>
            </div>
        </>
    )
}