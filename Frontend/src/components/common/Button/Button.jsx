import "./Button.css"

export default function Button({ type, name, color1, color2 }){
    return (
        <button
            type={type} 
            style={
                {color: {color1},
                backgroundColor: {color2}}
            }>{name}</button>
    )
}