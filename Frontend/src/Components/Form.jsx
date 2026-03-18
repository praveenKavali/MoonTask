
import styles from './form.module.css';
/**
 * A reusable input field with a label, used for authentication and text forms.
 * @component
 * @param {string} id - Unique identifier for the input and linking the label.
 * @param {string} [placeholder] - The hint text shown inside the input.
 * @param {string} value - The current state value of the input field.
 * @param {string} [type] - The HTML input type (e.g., 'text', 'email', 'password').
 * @param {(event: React.ChangeEvent<HTMLInputElement>) => void} handleChange - Function called when the user types in the input.
 * @example
 * <Form
 *   id="email"
 *   placeholder="Enter email"
 *   value={email}
 *   type="email"
 *   handleChange={e => setEmail(e.target.value)}
 * />
 * @returns {JSX.Element} The Form component
 */
export function Form({ id, placeholder, value, type, handleChange }) {
  return (
    <div className={styles.formContainer}>
      <label htmlFor={id}>{id}</label>
      <input
        id={id}
        type={type}
        placeholder={placeholder}
        value={value}
        name={id}
        onChange={handleChange}
        required
      />
    </div>
  );
}
