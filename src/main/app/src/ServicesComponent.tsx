import ServiceComponent from './ServiceComponent.tsx'
import Service from './types/Service.ts'

const services: Array<Service> = [{
    id: "p1meter-0AD04A",
    name: "P1 Meter",
    type: "HWE-P1"
}];

export default function ServicesComponent() {

    const servicesList = services.map(s =>
        <li key={s.id}><ServiceComponent service={s}/></li>
    );

    return (
        <div id="services">
            <h2>Service Discovery</h2>

            <div>Devices found: {services.length}</div>

            <div className="services">
                <ul>
                    {servicesList}
                </ul>
            </div>
        </div>
    )
}