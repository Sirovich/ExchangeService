export type Transaction = {
    id: number;
    currencyFrom: string;
    currencyTo: string;
    amountFrom: number;
    amountTo: number;
    exchangeRate: number;
    isRefunded: boolean;
    createdAt: Date;
    updatedAt: Date;
    checkNumber: string;
}