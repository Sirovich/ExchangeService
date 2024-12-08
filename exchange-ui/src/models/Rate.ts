import { atom } from "jotai";

export type Rate = {
    baseCurrency: string;
    rates: Map<string,number>;
}

export const ratesAtom = atom<Rate[]>([]);