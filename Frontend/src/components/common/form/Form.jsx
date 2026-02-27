import { useState } from "react";
import Field from "../Field";
import Button from "../Button/Button";
import styles from "./form.module.css";
import Header from "../../header/Header";

/**
 * Creating a Form component using {@link Field} component.
 * @param type - type of the form(register or login)
 * @param onSubmit - action that trigger when user click button
 * @returns a Form component.
 */
export default function Form({ type, onSubmit }) {
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
        <>
            <Header />
            <div className={styles.formContainer}>
                <form onSubmit={onSubmit}>
                    <h1>{isRegister ? 'Register' : 'Login'}</h1>
                    <div className={styles.fieldContainer}>
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
                            placeholder="Enter password"
                            minLength="8"
                        />
                    </div>
                    <Button
                        type="submit"
                        name={isRegister ? 'Register' : 'Login'}
                    />
                </form>
            </div>
        </>
    )
}