import { atom } from "jotai";

export type Transaction = {
    id: number;
    userId: number;
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

export const transactionAtom = atom<Transaction | undefined>(undefined);