

export default function Input({ id, type, name, value, onChange, placeholder, minLength }) {
    return (
        <input
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