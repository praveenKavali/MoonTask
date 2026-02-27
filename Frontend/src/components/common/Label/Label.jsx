import "./label.module.css";
/**
 * Creating a label component.
 * @param id - used to connect with input
 * @param label - label name. 
 * @returns a label component.
 */
export default function Label({ id, label }){
    return (
        <label htmlFor={id}>{label}</label>
    )
}