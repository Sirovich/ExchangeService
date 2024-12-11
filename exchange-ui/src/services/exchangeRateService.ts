import { Rate } from "../models/Rate";

export const exchangeRateService = {
    getRates: async (currencies: string[]): Promise<Rate[]> => {
        let token = localStorage.getItem('token');

        let response = await fetch('http://localhost:8080/api/rates/list' + '?baseCurrencies=' + currencies.join(','),
        {
            headers: {
                'Authorization': `Bearer ${token}`
            },
        });
        
        return response.json() as Promise<Rate[]>;
    },

    addRate: async (fromCurrency: string, toCurrency: string, rate: number): Promise<void> => {
        let token = localStorage.getItem('token');
        const result = await fetch('http://localhost:8080/api/rates/rate', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({baseCurrency: fromCurrency, targetCurrency: toCurrency, rate}),
        });

        return result.json() as Promise<void>;
    }
}