export class StockDtoReq{

    constructor(public productName:string,
                public quantity:number,
                public quantityMultiple:number,
                public deliveryDelay:number) {}

}