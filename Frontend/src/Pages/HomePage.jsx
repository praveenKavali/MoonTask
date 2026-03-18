import { Link } from "react-router-dom";
import styles from './homePage.module.css';

export default function HomePage() {
    return (
        <div>
            <h2>Welcome to moon task a task manager application. Where you can store your task.</h2>
            <div className={styles.linkContainer}>
                <Link to="/register" 
                    style={{
                        listStyle: "none", color: 'white', backgroundColor: 'black', borderColor: 'black'
                    }}
                >Register</Link>
                <Link to="/login" style={{listStyle: "none"}}>Login</Link>
            </div>
        </div>
    )
}