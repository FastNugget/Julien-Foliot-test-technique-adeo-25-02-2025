import type {DailyConsumption} from "@/model/yaourt/DailyConsumption.ts";

export class YaourtComputeDtoRES {

    constructor(public yaourtNumber:number, public dailyConsumptionList:Array<DailyConsumption>) {}


}

