import { Account } from "../models/Account";
import { AccountReq } from "../models/AccountReq";

export const accountService = {
    getAccounts: async (): Promise<Account[]> => {
        let token = localStorage.getItem('token');
        let response = await fetch('http://localhost:8080/api/accounts/list', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        return response.json() as Promise<Account[]>;
    },

    getAvailableCurrencies: async (): Promise<string[]> => {
        let token = localStorage.getItem('token');
        let response = await fetch('http://localhost:8080/api/accounts/currencies', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.json() as Promise<string[]>;
    },

    createAccount: async (account: AccountReq): Promise<void> => {
        let token = localStorage.getItem('token');
        await fetch('http://localhost:8080/api/accounts/account', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(account),
        });
    },

    addFunds: async (account: Account): Promise<Account> => {
        let token = localStorage.getItem('token');
        let result = await fetch(`http://localhost:8080/api/accounts/account/${account.id}`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(account),
        });

        return result.json() as Promise<Account>;
    }
}