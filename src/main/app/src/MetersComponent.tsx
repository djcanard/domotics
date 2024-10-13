import MeterComponent from './MeterComponent.tsx'
import Meter from './types.tsx'

const meters: Array<Meter> = [
    {
        name: "domotics.metrics.homewizard.api.basic.timer",
        device: "p1meter-0AD04A",
        type: "TIMER"
    }
];

export default function MetersComponent() {

    const metersList = meters.map(m =>
        <li key={m.name}><MeterComponent meter={m}/></li>
    );

    return (
        <div id="meters">
            <h2>Metrics</h2>

            <div>
              <span>Prometheus: <a href="http://localhost:8080/q/dev-ui/io.quarkus.quarkus-micrometer/prometheus" target="metrics">Micrometer metrics</a></span>
            </div>

            <div>Meters found: 1</div>

            <div className="meters">
                <ul>
                    {metersList}
                </ul>
            </div>
        </div>
    )
}