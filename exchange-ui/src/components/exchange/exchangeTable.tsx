import { FC, useState } from "react";
import { exchangeRateService } from "../../services/exchangeRateService";
import { userAtom } from "../../models/User";
import { useAtom } from "jotai";

export const ExchangeTable: FC = () => {

    const [fromCurrency, setFromCurrency] = useState<string>('');
    const [toCurrency, setToCurrency] = useState<string>('');
    const [rate, setRate] = useState<number>(0);
    const [user] = useAtom(userAtom);

    function handleSubmit(): void {
        exchangeRateService.addRate(fromCurrency, toCurrency, rate);
        window.location.reload();
    }

    if(user.type.toLocaleLowerCase() !== 'admin') {
        window.location.href = '/user';
    }

    return (
        <div className="container mx-auto px-4 py-8">
          <h1 className="text-2xl font-bold mb-6">Exchange Rates</h1>
          
          <form onSubmit={handleSubmit} className="mb-8">
            <div className="grid grid-cols-1 sm:grid-cols-4 gap-4">
              <input
                type="text"
                value={fromCurrency}
                onChange={(e) => setFromCurrency(e.target.value)}
                placeholder="From Currency (e.g., USD)"
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                maxLength={3}
                required
              />
              <input
                type="text"
                value={toCurrency}
                onChange={(e) => setToCurrency(e.target.value)}
                placeholder="To Currency (e.g., EUR)"
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                maxLength={3}
                required
              />
              <input
                type="number"
                value={rate}
                onChange={(e) => setRate(parseFloat(e.target.value))}
                placeholder="Exchange Rate"
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                step="0.0001"
                min="0"
                required
              />
              <button 
                type="submit" 
                className="w-full bg-blue-500 text-white font-bold py-2 px-4 rounded hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 transition duration-200"
              >
                Add Rate
              </button>
            </div>
          </form>
        </div>
      )
};