import { FC, useEffect, useState } from "react";
import { Account, availableAccountsAtom } from "../../models/Account";
import { accountService } from "../../services/accountService";
import { useAtom } from "jotai";
import { validateNonNegative } from "../transaction/validation";

export const AccountsList: FC = () => {
    const [accounts, setAccounts] = useState<Account[]>([]);
    const [availableAccounts, setAvailableAccounts] = useAtom(availableAccountsAtom);
    const [amounts, setAmounts] = useState<{ [key: number]: number }>({});

    useEffect(() => {
        accountService.getAccounts().then((accounts) => {
            setAccounts(accounts);
        });

        accountService.getAvailableCurrencies().then((currencies) => {
            setAvailableAccounts(currencies);
        });
    }, []);

    const createAccount = () => {
        window.location.href = '/create-account';
    };

    const handleAmountChange = (accountId: number) => (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.value[0] === '0' && e.target.value.length > 1) {
            e.target.value = e.target.value.slice(1);
        }

        const value = parseFloat(e.target.value);

        if (validateNonNegative(value)) {
            setAmounts((prevAmounts) => ({
                ...prevAmounts,
                [accountId]: Number(value),
            }));
        }
    };

    const submitAmount = (accountId: number) => {
        const amount = amounts[accountId];
        if (amount) {
            let account = accounts.find((acc) => acc.id === accountId);
            account!.balance += amount;
            accountService.addFunds(account!).then((account) => {
                setAccounts((prevAccounts) => {
                    const newAccounts = prevAccounts.map((acc) => {
                        if (acc.id === accountId) {
                            return account;
                        }
                        return acc;
                    });
                    return newAccounts;
                });
            });
        }
    }

    return (
        <div className="bg-gray-100 mb-8 p-4">
            <div className="flex flex-col items-center justify-center p-4 mb-3">
                <h1 className="text-2xl font-bold text-center">Accounts</h1>
                <div className="flex flex-row gap-6 mt-4">
                    {accounts.map((account) => (
                        <div key={account.currency} className="flex flex-col items-center bg-white shadow-md rounded-lg p-4 mb-4 max-w-md">
                            <div className="text-xl text-lg font-semibold">{account.currency}</div>
                            <div className="text-xl text-gray-600 mb-4">{account.balance}</div>
                            <input type="number" step="any" value={amounts[account.id] || ''}
                                onChange={handleAmountChange(account.id)} className="w-auto mb-2 p-2 border rounded" />
                            <button onClick={() => submitAmount(account.id)} className="w-full p-2 bg-blue-500 text-white rounded">Add</button>
                        </div>
                    ))}
                </div>
            </div>
            {availableAccounts.length > 0 && <button
                type="button"
                onClick={createAccount}
                className="w-1/3 px-4 py-2 font-medium text-white bg-indigo-600 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring focus:ring-indigo-200"
            >
                Create account
            </button>}
        </div>
    );
};