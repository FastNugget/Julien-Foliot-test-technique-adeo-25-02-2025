export class YaourtComputeDtoRES {

    constructor(public yaourtNumber:number, public dailyConsumptionList:Array<DailyConsumption>) {}


}

export class DailyConsumption {

    constructor(public date:string, public consumption:number) {}

}