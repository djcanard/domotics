import Meter from './types/Meter.ts'

export default function MeterComponent({meter}: {meter: Meter}) {

    return (
        <div className="meter">
            <span>[{meter.name} - {meter.device} - {meter.type}]</span>
            <ul>
                <li>count: 1</li>
                <li>total: 0,487</li>
                <li>max: 0,487</li>
            </ul>
        </div>
    )
}