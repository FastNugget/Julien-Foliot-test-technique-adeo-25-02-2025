export class StockDtoRes{

    constructor(public id:number,
                public productName:string,
                public quantity:number,
                public quantityMultiple:number,
                public deliveryDelay:number) {}

}