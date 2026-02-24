

export default function Label({ id, label }){
    return (
        <label htmlFor={id}>{label}</label>
    )
}