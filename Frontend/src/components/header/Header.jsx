import styles from "./header.module.css";

export default function Header({ isLogged, name }) {
    const firstLetter = name?.trim().charAt(0).toUpperCase();
    return (
        <header className={styles.headerContainer}>
            <h1><span>Moon</span> Task</h1>
            {isLogged && <div>{firstLetter}</div>}
        </header>
    )
}