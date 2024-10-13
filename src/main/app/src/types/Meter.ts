export default interface Measure {
    value: number;
    name: string;
    tagValueRepresentation: string;
}

export default interface Meter {
    name: string;
    device: string;
    unit: string;
    type: string;
    measures: Array<Measure>;
}
