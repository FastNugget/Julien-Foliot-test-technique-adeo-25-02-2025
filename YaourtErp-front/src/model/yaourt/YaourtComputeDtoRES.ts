export class YaourtComputeDtoRES {

    constructor(public yaourtNumber:string, public dailyConsummationList:Array<DailyConsummation>) {}


}

export class DailyConsummation{

    constructor(public date:string, public consummation:number) {}

}