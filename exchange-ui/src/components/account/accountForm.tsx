import { FC, useEffect, useState } from "react";
import { validateNonNegative } from "../transaction/validation";
import { accountService } from "../../services/accountService";
import { userAtom } from "../../models/User";
import { useAtom } from "jotai";
import { AccountReq } from "../../models/AccountReq";

export const AccountForm: FC = () => {
    const [user] = useAtom(userAtom);
    const [amount, setAmount] = useState<number>(0);
    const [currency, setCurrency] = useState('');
    const [currencies, setCurrencies] = useState<string[]>([]);

    useEffect(() => {
        if (currencies.length <= 0) {
            accountService.getAvailableCurrencies().then((currencies) => {
                if(currencies.length <= 0) {
                    window.location.href = '/user';
                }
                setCurrencies(currencies);
                setCurrency(currencies[0]);
            });
        }
        else {
            setCurrency(currencies[0]);
        }
    }, []);

    const createAccount = (e: React.FormEvent) => {
        e.preventDefault();
        accountService.createAccount({ userId: user.id, balance: amount, currency: currency } as AccountReq);
        window.location.href = '/user';
    }

    const handleAmountChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        console.log(e.target.value);
        const value = parseFloat(e.target.value);
        if (validateNonNegative(value)) {
            setAmount(Number(value));
        }
    };

    return (
        <div className="bg-gray-100 mb-8 p-4">
            <div className="flex flex-col items-center justify-center p-4 mb-3">
                <h1 className="text-2xl font-bold text-center">Create account</h1>
                <form className="flex flex-col gap-4 mt-4">
                    <div className="mb-4 flex flex-row">
                        <input
                            type="number"
                            step="any"
                            id="amount"
                            value={amount}
                            onChange={handleAmountChange}
                            className="mr-2 grow-[3] mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                        />
                        <select
                            id="fromCurrency"
                            value={currency}
                            onChange={(e) => setCurrency(e.target.value)}
                            className="grow-0 w-24 mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                        >
                            {currencies.map((availableCurrency) => (
                                <option key={availableCurrency} value={availableCurrency}>{availableCurrency}</option>
                            ))}
                        </select>
                    </div>
                    <button
                        type="submit"
                        onClick={createAccount}
                        className="w-1/3 px-4 py-2 font-medium text-white bg-indigo-600 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring focus:ring-indigo-200"
                    >
                        Create account
                    </button>
                </form>
            </div>
        </div>
    );
};