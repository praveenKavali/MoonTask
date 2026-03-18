import styles from './dropDown.module.css';
/**
 * A reusable drop down, used for selecting a value.
 * @param {string} id - Unique identifier for select.
 * @param {string} value - The current state of the select item.
 * @param {Array<{label: string, value: string}>} options - List of options.
 * @param {Function} handleChange - Change handler.
 * @returns a drop down component
 */
export default function DropDown({ id, value, options, handleChange }) {
    return (
        <div className={styles['dropdown']}>
            <label htmlFor={id}>{id}</label>
            <select name={id} id={id} value={value} onChange={handleChange}>{id}
                {/* We map over the array to create <options> tag dynamically */}
                {
                    options.map((opt, index) => (
                        <option key={index} value={opt.value} className={styles.option} >{opt.label}</option>
                    ))
                }
            </select>
        </div>
    )
}