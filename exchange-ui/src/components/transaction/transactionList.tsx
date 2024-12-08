import React, { FC, useEffect, useState } from 'react';
import { transactionService } from '../../services/transactionService';
import { Transaction } from '../../models/Transaction';

const TransactionList: FC = () => {
    const [transactionsList, setTransactionsList] = useState<Transaction[]>([]);

    useEffect(() => {
        transactionService.getTransactions().then((transactions) => {
            setTransactionsList(transactions);
        });
    }, []);

    return (
        <div className="p-6 bg-gray-100 flex flex-col justify-center border-2 border-slate-400 rounded-xl">
            <h2 className="text-2xl font-bold mb-4 text-gray-800">Transaction history:</h2>
            <div className="flex flex-row flex-wrap justify-center gap-4">
                {transactionsList.map(transaction => (
                    <div key={transaction.id} className="w-96 bg-white p-4 rounded-lg shadow-md border border-slate-400">
                        <div className='font-bold'>{transaction.checkNumber}</div>
                        <div className='flex flex-row gap-2 justify-center border-b-2 border-slate-400 mb-4 p-2'>
                            <div className="mb-1">{new Date(transaction.createdAt).toLocaleDateString()}</div>
                            <div className="mb-1">{new Date(transaction.createdAt).toLocaleTimeString()}</div>
                        </div>
                        <div className='flex flex-row gap-2 justify-center'>
                            <div className="mb-1">{transaction.currencyFrom}</div>
                            <div>&rarr;</div>
                            <div className="mb-1">{transaction.currencyTo}</div>
                        </div>
                        <div className='flex flex-row gap-2 justify-center'>
                            <div className="mb-1">{transaction.amountFrom.toFixed(2)}</div>
                            <div>&rarr;</div>
                            <div className="mb-1">{transaction.amountTo.toFixed(2)}</div>
                        </div>
                        <div className="mb-1">Rate: {transaction.exchangeRate}</div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default TransactionList;