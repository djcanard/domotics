import { useState, useEffect } from 'react';

import MeterComponent from './MeterComponent.tsx'
import Meter from './types/Meter.tsx'

export default function MetersComponent() {

    const [meters, setMeters] = useState([]);

    useEffect(() => {
        fetch('/meters')
           .then(response => response.json())
           .then(data => setMeters(data))
           .catch((err) => console.error(err))
    }, []);

    const metersList = meters.map((m: Meter) =>
        <li key={m.name}><MeterComponent meter={m}/></li>
    );

    return (
        <div id="meters">
            <h2>Metrics</h2>

            <div>
              <span>Prometheus: <a href="http://localhost:8080/q/dev-ui/io.quarkus.quarkus-micrometer/prometheus" target="metrics">Micrometer metrics</a></span>
            </div>

            <div>Meters found: {meters.length}</div>

            <div className="meters">
                <ul>
                    {metersList}
                </ul>
            </div>
        </div>
    )
}