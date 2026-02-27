import styles from "./input.module.css"
/**
 * Creating an input component for taking input from user.
 * @param id - an id for connecting with label.
 * @param type - determides the type of the input.
 * @param name - name of the input(which will be name in form, for sending backend).
 * @param value - which will be set for specific value(like name or email).
 * @param onChange - a function to tell what will do if the value is changed.
 * @param placeholder - it set the value in input.
 * @param minLength - In password it need to be 8 letters so I use minLength.
 * @returns an input component used in several places.
 */
export default function Input({ id, type, name, value, onChange, placeholder, minLength }) {
    return (
        <input className={styles.inputField}
            id={id}
            type={type}
            name={name}
            value={value}
            onChange={onChange}
            required
            placeholder={placeholder}
            minLength={minLength}
        />
    )
}