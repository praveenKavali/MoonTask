import "./header.css";

export default function Header({ isLogged, name }) {
    const firstLetter = name?.trim().charAt(0).toUpperCase();
    return (
        <header className="header-container">
            <h1><span>Moon</span> Task</h1>
            {isLogged && <div>{firstLetter}</div>}
        </header>
    )
}