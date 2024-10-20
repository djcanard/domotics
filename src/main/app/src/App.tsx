import {useState} from 'react'
import './App.css'
import MetersComponent from './MetersComponent.tsx'
import ServicesComponent from './ServicesComponent.tsx'

export default function App() {
    const [count, setCount] = useState(0)

    const eventSource = new EventSource("/api/events");
    eventSource.onmessage = (event) => {
        console.info('event:', event.data);
    }

    return (
        <>
            <div>
                <MetersComponent/>
            </div>
            <div>
                <ServicesComponent/>
            </div>
            <div>
                <button onClick={() => setCount((count) => count + 1)}>
                    count is {count}
                </button>
            </div>
        </>
    )
}

