import { Rate } from "../models/Rate";
import { Transaction } from "../models/Transaction";

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
    }
}