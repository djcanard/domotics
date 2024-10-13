const service = {
    id: "p1meter-0AD04A",
    name: "P1 Meter",
    type: "HWE-P1"
}
export default function ServiceComponent() {

    return (
        <div className="service">
            <span>[{service.id}] {service.name} - {service.type}</span>
        </div>
    )
}