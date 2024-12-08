export type TransactionReq = {
    currencyFrom: string;
    currencyTo: string;
    amountFrom: number;
    amountTo: number;
    exchangeRate: number;
    userId: string;
}