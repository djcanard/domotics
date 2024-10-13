import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Meters from './Meters.tsx'
import Services from './Services.tsx'

export default function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div>
         <Meters />
      </div>
      <div>
         <Services />
      </div>
      <div>
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
      </div>
    </>
  )
}

