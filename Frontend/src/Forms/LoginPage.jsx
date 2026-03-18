
import AuthPage from "../UserLogin/AuthPage";
import { Link } from "react-router-dom";
import Auth from "../UserLogin/AuthResponse";
import { Navbar } from "../Header/Navbar";

export default function LoginPage({setIsLogged}) {
    return(
        <>
            <Auth isRegister={false} setIsLogged={setIsLogged} />
        </>
        
    )
}