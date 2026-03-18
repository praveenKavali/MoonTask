
import { useEffect, useState } from "react";
import backendCall from "../JsFiles/BaseAxios";
import styles from './navbar.module.css';
import UserOptions from "../UserLogin/UserOptions";

export function Navbar({ isLogged }) {
    const [showOptions, setShowOptions] = useState(false);
    const [usernameFirstLetter, setUsernameFirstLetter] = useState('');

    useEffect(() => {
        if (isLogged) {
            const fetchUsername = async () => {
                try {
                    const res = await backendCall.get('/username');
                    const username = res.data;
                    console.log(`username: ${username}`);
                    if (username) {
                        const firstLetter = username.charAt(0);
                        setUsernameFirstLetter(firstLetter);
                    } else {
                        setUsernameFirstLetter('U')
                    }
                } catch (e) {
                    console.log(`Error occur during fetching username: ${e}`);
                }
            }
            fetchUsername();
        }
    }, [isLogged]);
    return (
        <div className={styles.nav}>
            <h1>Moon Task</h1>
            {isLogged &&
                <div onClick={() => setShowOptions(true)} className={styles.firstLetter}>
                    {usernameFirstLetter && usernameFirstLetter}
                    <div>
                        {
                            showOptions &&
                            <UserOptions setShowOptions={setShowOptions} />
                        }
                    </div>
                </div>}
        </div>
    )
}