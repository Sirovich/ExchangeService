import { useAtom } from "jotai";
import { Transaction, transactionAtom } from "../../models/Transaction";
import { useParams } from "react-router-dom";
import { FC, useEffect, useRef } from "react";
import { transactionService } from "../../services/transactionService";

export const TransactionReceipt: FC = () => {
    const handlePrint = () => {
        if (ref.current) {
            const printContents = ref.current.innerHTML;
            const originalContents = document.body.innerHTML;
            document.body.innerHTML = printContents;
            window.print();
            document.body.innerHTML = originalContents;
            window.location.reload(); // To reload the original content
        }
    };

    const { id } = useParams<{ id: string }>();
    const [transaction, setTransaction] = useAtom(transactionAtom);
    const ref = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (transaction && transaction.id.toString() === id) {
            return;
        }

        if (id) {
            transactionService.getTransaction(id).then((result) => {
                setTransaction(result);
            });
        }
    }, [id]);

    if (!transaction) {
        return <div>Loading...</div>;
    }

    return (
        <div className="self-center mt-12 p-4 bg-white rounded-lg shadow border-2 border-slate-300 w-96">
            <div ref={ref} className="p-4">
                <div className="w-full max-w-md mx-auto">
                    <h1 className="text-2xl font-bold mb-4">Transaction Receipt</h1>
                </div>
                <div className="w-full max-w-md mx-auto">
                    <div className="space-y-4">
                        <div className="flex justify-between">
                            <span className="font-semibold">Transaction ID:</span>
                            <span>{transaction.checkNumber}</span>
                        </div>
                        <div className="flex justify-between">
                            <span className="font-semibold">From:</span>
                            <span>{transaction.currencyFrom} {transaction.amountFrom.toFixed(2)}</span>
                        </div>
                        <div className="flex justify-between">
                            <span className="font-semibold">To:</span>
                            <span>{transaction.currencyTo} {transaction.amountTo.toFixed(2)}</span>
                        </div>
                        <div className="flex justify-between">
                            <span className="font-semibold">Exchange Rate:</span>
                            <span>{transaction.exchangeRate.toFixed(4)}</span>
                        </div>
                        <div className="flex justify-between">
                            <span className="font-semibold">Status:</span>
                            <span>{transaction.isRefunded ? 'Refunded' : 'Completed'}</span>
                        </div>
                        <div className="flex justify-between">
                            <span className="font-semibold">Date:</span>
                            <span>{new Date(transaction.createdAt).toLocaleDateString()} {new Date(transaction.createdAt).toLocaleTimeString()}</span>
                        </div>
                    </div>
                </div>
            </div>
            <div className="w-full max-w-md mx-auto mt-8">
                <button
                    onClick={handlePrint}
                    className="w-full px-4 py-2 font-medium text-white bg-indigo-600 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring focus:ring-indigo-200"
                >
                    Print
                </button>
            </div>
        </div>
    );
};