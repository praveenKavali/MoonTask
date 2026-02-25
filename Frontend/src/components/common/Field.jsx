import Input from "./Input/Input";
import Label from "./Label/Label";

/**
 * Creating a Field component consist of {@link Input} and {@link Button}.
 * @param id - an id for connecting with input with label.
 * @param label - label name.
 * @param type - defines the button type.
 * @param name - name of the button.
 * @param type - determides the type of the input.
 * @param name - name of the input(which will be name in form, for sending backend).
 * @param value - which will be set for specific value(like name or email).
 * @param handleChange - a function to tell what will do if the value is changed.
 * @param placeholder - it set the value in input.
 * @param minLength - In password it need to be 8 letters so I use minLength.
 * @returns a Field componet.
 */
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