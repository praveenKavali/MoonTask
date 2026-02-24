import { useState } from "react";
import FormInput from "./common/FormInput"

export default function Form({ type, onSubmitAction }) {
    const isRegister = type === "register";
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        password: ''
    });
    function handleChange(e) {
        setFormData({ ...formData, [e.target.id]: e.target.value })
    }

    return (
        <form onSubmit={onSubmitAction}>
            <h2>{isRegister ? 'Create An Account' : 'Login To An Existing Account'}</h2>
            {/*Conditionally render username only for register.*/}
            {
                isRegister && (
                    <FormInput
                        label="name"
                        id="name"
                        type="text"
                        name="name"
                        value={formData.username}
                        onChange={handleChange}
                        placeholder="e.g:praveen"
                    />
                )
            }
            <FormInput
                label="email"
                id="email"
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="e.g:praveen@gmail.com"
            />
            <FormInput
                label="password"
                id="password"
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                minLength="8"
                placeholder="e.g:00000000"
            />
            <button type="submit">{isRegister ? 'Register' : 'Login'}</button>
        </form>
    )
}