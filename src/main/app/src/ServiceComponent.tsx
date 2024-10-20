import Service from './types/Service.ts'

export default function ServiceComponent({service}: { service: Service }) {

    return (
        <div className="service">
            <span>[{service.id}] {service.name} - {service.type}</span>
        </div>
    )
}