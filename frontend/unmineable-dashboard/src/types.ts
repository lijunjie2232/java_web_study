interface workersData {
    uuid: string,
    online: boolean,
    name: string,
    last: number,
    rhr: string,
    chr: string,
    referral: string
}
interface chartData {
    reported: {
        data: string[],
        timestamps: number[]
    },
    "calculated": {
        data: string[],
        timestamps: number[]
    }
}
interface WData {
    workers: workersData,
    chart: chartData
}

export type {workersData, chartData, WData}