import { Link } from "react-router-dom"

export default function UserOptions({ setShowOptions }) {
    const styles = {
        textDecoration: 'none',
        padding: '0.8rem 1.2rem',
        borderRadius: '0.5rem',
        color: 'black',
        backgroundColor: 'white'
    }
    return (
        <div style={{
            position: 'absolute',
            top: '7rem',
            right: '15rem',
            backgroundColor: 'white',
            color: 'black',
            display: 'flex',
            flexDirection: 'column',
            border: '1px solid #333',
            borderRadius: '0.8rem'
        }}>
            <span onClick={(e) => {
                e.stopPropagation();
                setShowOptions(false)
            }} style={{
                alignSelf: 'flex-end',
                cursor: 'pointer',
                color: '#888',
                fontSize: '1.2rem',
                backgroundColor: 'red',
                padding: '0.5rem'
            }}>X</span>
            <Link to="/update" style={styles}>Update UserDetails</Link>
            <Link to="/delete" style={styles}>Delete account</Link>
        </div>
    )
}