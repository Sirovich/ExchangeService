import { useAtom, atom } from "jotai";
import { exchangeRateService } from "../services/exchangeRateService";
import { ratesAtom } from "./Rate";

export type Account = {
  id: number;
  currency: string;
  balance: number;
}

export const accountsAtom = atom<Account[]>([]);
export const availableAccountsAtom = atom<String[]>([]);

export const currentAccountAtom = atom(
  (get) => get(accountsAtom),
  (get, set, update) => {
    const newValue = typeof update === 'function' ? update(get(accountsAtom)) : update;
    let accounts = newValue as Account[];

    const accountCurrencies = accounts.map((account) => account.currency).filter((currency, index, self) => self.indexOf(currency) === index);

    exchangeRateService.getRates(accountCurrencies).then((rateList) => {
      rateList.forEach((rate) => {
        rate.rates = new Map(Object.entries(rate.rates));
      });
      set(ratesAtom, rateList);

      accounts = accounts.filter((account) => rateList.some(x => x.baseCurrency === account.currency));
      set(accountsAtom, accounts);
    });
  },
);