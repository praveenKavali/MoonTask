import "./Button.module.css"

/**
 * Creating a button component for using it in various places.
 * @param {String} type - defines the button type.
 * @param {String} name - name of the button.
 * @returns a button component.
 */
export default function Button({ type, name }){
    return (
        <button
            type={type} 
        >{name}
        </button>
    )
}