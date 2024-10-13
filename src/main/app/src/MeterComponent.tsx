import Meter from './types/Meter.ts'
import Measure from './types/Meter.ts'

export default function MeterComponent({meter}: {meter: Meter}) {

    const measuresList = meter.measures.map((m: Measure) =>
        <li key={m.tagValueRepresentation}>{m.tagValueRepresentation}: {m.value} {meter.unit}</li>
    );

    return (
        <div className="meter">
            <span>[{meter.name} - {meter.device} - {meter.type}]</span>
            <ul>
                {measuresList}
            </ul>
        </div>
    )
}