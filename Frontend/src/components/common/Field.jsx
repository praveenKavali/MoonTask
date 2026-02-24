import Input from "./Input/Input";
import Label from "./Label/Label";


export default function Field({ id, label, type, name, value, handleChange, placeholder, minLength }){
    return (
        <>
            <Label id={id} label={label} />
            <Input
                id={id}
                type={type}
                name={name}
                value={value}
                onChange={handleChange}
                placeholder={placeholder}
                minLength={minLength}
            />
        </>
    )
}