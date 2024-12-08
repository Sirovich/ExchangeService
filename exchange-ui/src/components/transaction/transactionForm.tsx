import { FC, useEffect, useState } from "react";
import { accountService } from "../../services/accountService";
import { Account, accountsAtom, currentAccountAtom } from "../../models/Account";
import { Rate, ratesAtom } from "../../models/Rate";
import { exchangeRateService } from "../../services/exchangeRateService";
import { validateNonNegative } from "./validation";
import { useAtom } from "jotai";
import { TransactionReq } from "../../models/TransactionReq";
import { transactionService } from "../../services/transactionService";
import { userAtom } from "../../models/User";

export const TransactionForm: FC = () => {
    const [amountFrom, setAmountFrom] = useState(0);
    const [amountTo, setAmountTo] = useState(0);
    const [currentRate, setCurrentRate] = useState(0);
    const [fromCurrency, setFromCurrency] = useState('');
    const [toCurrency, setToCurrency] = useState('');
    const [exchangeRate, setExchangeRate] = useState(0);
    const [accounts] = useAtom(currentAccountAtom);
    const [rates] = useAtom<Rate[]>(ratesAtom);
    const [user] = useAtom(userAtom);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        
        let transaction: TransactionReq = {
            amountFrom: amountFrom,
            amountTo: amountTo,
            currencyFrom: fromCurrency,
            currencyTo: toCurrency,
            exchangeRate: exchangeRate,
            userId: user.id,
        };
        
        transactionService.createTransaction(transaction);
    };

    useEffect(() => {
        if (accounts.length > 0) {
            let firstAccountCurrency = accounts[0].currency;
            setFromCurrency(firstAccountCurrency);
            let rateObj = rates.find(x => x.baseCurrency === firstAccountCurrency)!;
            const firstCurrency = rateObj.rates.keys().next().value!;
            setToCurrency(firstCurrency);

            const firstRate = rateObj.rates.get(firstCurrency);
            setExchangeRate(firstRate || 0);
            setCurrentRate(firstRate || 0);
        }
    }, [accounts, rates]);


    const handleAmountFromChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = parseFloat(e.target.value);
        if (validateNonNegative(value)) {
            setAmountFrom(value);
            let newAmount = (value * currentRate).toFixed(2);
            setAmountTo(parseFloat(newAmount));
        }
    };

    const handleAmountToChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = parseFloat(e.target.value);
        if (validateNonNegative(value)) {
            setAmountTo(value);
            let newAmount = (value / currentRate).toFixed(2);
            setAmountFrom(parseFloat(newAmount));
        }
    };

    const changeToCurrency = (currency: string) => {
        setToCurrency(currency);
        const targetRates = rates.find(x => x.baseCurrency === fromCurrency)!.rates;
        const rate = targetRates.get(currency);
        setExchangeRate(rate || 0);
        setCurrentRate(rate || 0);
        setAmountTo(parseFloat((amountFrom * (rate || 0)).toFixed(2)));
    };

    const changeFromCurrency = (currency: string) => {
        setFromCurrency(currency);
        const targetRates = rates.find(x => x.baseCurrency === currency)!.rates;
        const newCurrencyTo = targetRates.keys().next().value!;
        setToCurrency(newCurrencyTo);
        const rate = targetRates.get(newCurrencyTo);
        setExchangeRate(rate || 0);
        setCurrentRate(rate || 0);

        setAmountTo(parseFloat((amountFrom * (rate || 0)).toFixed(2)));
    };

    return (
        <div className="flex flex-col justify-center mt-56">
            <div className="mb-6">
                <div className="text-2xl font-bold text-gray-800">1 {fromCurrency} equals</div>
                <div className="text-2xl font-bold text-gray-800">{exchangeRate} {toCurrency}</div>
            </div>
            <form onSubmit={handleSubmit} className="self-center max-w-md w-6/12 p-4 border border-gray-300 rounded-lg bg-white shadow-md">
                <div className="mb-4 flex flex-row">
                    <input
                        type="number"
                        step="any"
                        id="amount"
                        value={amountFrom}
                        onChange={handleAmountFromChange}
                        className="mr-2 grow-[3] mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    />
                    <select
                        id="fromCurrency"
                        value={fromCurrency}
                        onChange={(e) => changeFromCurrency(e.target.value)}
                        className="grow-0 w-24 mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    >
                        {accounts.map((account) => (
                            <option key={account.currency} value={account.currency}>{account.currency}</option>
                        ))}
                    </select>
                </div>
                <div className="mb-8">
                    <div className="mb-4 flex flex-row">
                        <input
                            type="number"
                            id="amount"
                            step="any"
                            value={amountTo}
                            onChange={handleAmountToChange}
                            className="mr-2 grow-[3] mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                        />
                        <select
                            id="toCurrency"
                            value={toCurrency}
                            onChange={(e) => changeToCurrency(e.target.value)}
                            className="grow-0 w-24 mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                        >
                            {rates.filter(x => x.baseCurrency === fromCurrency).map((currency) => (
                                Array.from(currency.rates.keys()).map((rate) => (
                                    <option key={rate} value={rate}>{rate}</option>
                                ))
                            ))}
                        </select>
                    </div>
                </div>
                <button type="submit" className="w-full py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                    Convert
                </button>
            </form>
        </div>
    );
}