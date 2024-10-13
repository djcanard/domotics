import Meter from './Meter.tsx'

export default function Meters() {

    return (
        <div id="meters">
            <h2>Metrics</h2>

            <div>
              <span>Prometheus: <a href="http://localhost:8080/q/dev-ui/io.quarkus.quarkus-micrometer/prometheus" target="metrics">Micrometer metrics</a></span>
            </div>

            <div>Meters found: 1</div>

            <div className="meters">
                <ul>
                    <li><Meter /></li>
                </ul>
            </div>
        </div>
    )
}