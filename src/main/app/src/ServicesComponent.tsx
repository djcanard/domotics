import ServiceComponent from './ServiceComponent.tsx'

export default function ServicesComponent() {

    return (
        <div id="services">
            <h2>Service Discovery</h2>

            <div>Devices found: 1</div>

            <div className="services">
                <ul>
                    <li><ServiceComponent /></li>
                </ul>
            </div>
        </div>
    )
}