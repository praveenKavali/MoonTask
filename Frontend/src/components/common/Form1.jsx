import { useState } from "react";
import Field from "./Field";
import Button from "./Button/Button";


export default function Form({ type, onSubmit, color1, color2 }) {
    const isRegister = type === "register";
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: ""
    });

    function handleChange(e) {
        setFormData({ ...formData, [e.target.id]: e.target.value });
    }
    return (
        <form onSubmit={onSubmit}>
            <h1>{isRegister ? 'Register' : 'Login'}</h1>
            {isRegister &&
                <Field 
                    id="name"
                    label="name:"
                    name="name"
                    value={formData.name}
                    handleChange={handleChange}
                    placeholder="Enter user name"
                />
            }
            <Field 
                id="email"
                label="Email:"
                name="email"
                type="email"
                value={formData.email}
                handleChange={handleChange}
                placeholder="e.g praveen@gmail.com"
            />
            <Field 
                id="password"
                label="Password:"
                name="password"
                type="password"
                value={formData.password}
                handleChange={handleChange}
                placeholder="Enter password(minimum 8 letters)"
                minLength="8"
            />
            <Button 
                type="submit" 
                color1={color1} 
                color2={color2} 
                name={isRegister ? 'Register' : 'Login'}
            />
        </form>
    )
}