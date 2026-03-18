import { useState } from "react";
import TaskFeatures from "../Task/TaskOptions";
import styles from './task.module.css';

/**
 * A reusable TaskCard component used to show task.
 * @param {Array
 * <{id: number,
 * name: string,
 * priority: string,
 * status: string,
 * description: string,
 * createdTime: time,
 * createdDate: Date,
 * completedTime: time,
 * completedDate: date}
 * >} data - contains the list of task related data(comes from backend).
 * @param {Function} handleClick - change handler(show options(e.g. change status, change prioriry, mark as completed etc) when you click the task) 
 * @returns TaskCard component
 */
export default function TaskCard({ data }) {
    const [showTask, setShowTask] = useState(true);
    const [selectedTask, setSelectedTask] = useState(null);
    const handleClick = (task) => {
        setSelectedTask(task);
        setShowTask(false);
    }
    return (
        <>
            {showTask ? (<div className={styles['task-container']}>
                {Array.isArray(data) ? data.map((task) => (
                    <div id={task.id} key={task.id} onClick={() => handleClick(task)} style={{
                        margin: '10px'
                    }}>
                        <h3>{task.name}</h3>
                        <div className={styles['task-items']}>
                            <li>
                            <span>priority</span>
                            <span>{task.priority}</span>
                        </li>
                        <li>
                            <span>status</span>
                            <span>{task.status}</span>
                        </li>
                        {
                            task.description &&
                            <p>
                                <span>description</span>
                                <span style={{
                                    height: '70px',
                                    overflow: 'auto',
                                    border: '2px solid black',
                                    borderRadius: '5px'
                                }}>{task.description}</span>
                            </p>
                        }
                        <li>
                            <span>created Time</span>
                            <span>{task.createdTime}</span>
                        </li>
                        <li>
                            <span>created Date</span>
                            <span>{task.createdDate}</span>
                        </li>
                        {
                            task.completedTime &&
                            <li>
                                <span>completed Time</span>
                                <span>{task.completedTime}</span>
                            </li>
                        }
                        {
                            task.completedDate &&
                            <li>
                                <span>completed Date</span>
                                <span>{task.completedDate}</span>
                            </li>}
                        </div>
                    </div>
                )) : <div>No tasks available</div>}
            </div>) : <TaskFeatures data={selectedTask} setShowTask={setShowTask} />}
        </>
    )
}