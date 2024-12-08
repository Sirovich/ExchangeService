import { FC, useEffect, useState } from "react";
import { Account, availableAccountsAtom } from "../../models/Account";
import { accountService } from "../../services/accountService";
import { useAtom } from "jotai";

export const AccountsList: FC = () => {
    const [accounts, setAccounts] = useState<Account[]>([]);
    const [availableAccounts, setAvailableAccounts] = useAtom(availableAccountsAtom);

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

    return (
        <div className="bg-gray-100 mb-8 p-4">
            <div className="flex flex-col items-center justify-center p-4 mb-3">
                <h1 className="text-2xl font-bold text-center">Accounts</h1>
                <div className="flex flex-row gap-4 mt-4">
                    {accounts.map((account) => (
                        <div key={account.currency} className="bg-white shadow-md rounded-lg p-4 mb-4 w-32 max-w-md">
                            <div className="text-lg font-semibold">{account.currency}</div>
                            <div className="text-gray-600">{account.balance}</div>
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