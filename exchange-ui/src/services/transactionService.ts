import { Transaction } from "../models/Transaction";
import { TransactionReq } from "../models/TransactionReq";

export const transactionService = {
    getTransactions: async (): Promise<Transaction[]> => {
        let token = localStorage.getItem('token');
        let response = await fetch('http://localhost:8080/api/transactions/list', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        return response.json() as Promise<Transaction[]>;
    },

    createTransaction(transaction: TransactionReq): Promise<void> {
        let token = localStorage.getItem('token');
        return fetch('http://localhost:8080/api/transactions/transaction', {
            body: JSON.stringify(transaction),
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            method: 'POST'
        }).then((response) => {
            if (!response.ok) {
                throw new Error('Error creating transaction');
            }
        });
    }
}