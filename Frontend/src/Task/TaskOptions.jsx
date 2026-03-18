import { Link } from "react-router-dom";
export default function TaskFeatures({data, setShowTask}) {
    const styles = {
        textDecoration: 'none',
        padding: '0.4rem 1.2rem',
        borderRadius: '0.5rem',
        color: 'black',
        backgroundColor: 'white'
    }
    return (
        <div style={{
            fontSize: '1rem',
            width: '40%',
            margin: '0 auto',
            display: 'flex',
            flexDirection: 'column',
            alignItems: "center",
            gap: '1rem',
            border: '0.2rem solid white',
            textDecoration: 'none'
        }}>
            <span onClick={(e) => {
                e.stopPropagation();
                setShowTask(true);
            }} style={{
                alignSelf: 'flex-end',
                cursor: 'pointer',
                color: '#888',
                fontSize: '1.2rem',
                backgroundColor: 'red',
                padding: '0 0.5rem'
            }}>X</span>
            <Link to={`/task/complete/${data.id}`} state={{ TaskId: data.id }} style={styles}>Mark as completed</Link>
            <Link to={`/task/update-priority/${data.id}`} 
                state={{TaskId: data.id, priority: data.priority}}
                style={styles}
            >Update priorty</Link>
            <Link to={`/task/update-status/${data.id}`} 
                state={{TaskId: data.id, status: data.status}}
                style={{...styles, marginBottom: '0.8rem'}}
            >Update status</Link>
        </div>
    )
}