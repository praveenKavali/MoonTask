import UserInput from "./Input/Input";

export default function FormInput({ label, id, type, name, value, onChange, placeholder, minLength }){
    return (
        <div>
            <label htmlFor={id}>{label}</label>
            <UserInput 
                id={id} 
                type={type}
                name={name}
                value={value} 
                onChange={onChange} 
                required
                placeholder={placeholder}
                minLength={minLength}
            />
        </div>
    )
}