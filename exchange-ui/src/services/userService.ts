import { User } from "../models/User";

export const userService = {
    login: async (email: string, password: string) => {
        let bodyParams = new URLSearchParams();
        bodyParams.append('email', email);
        bodyParams.append('password', password);

        let response = await fetch('http://localhost:8080/api/users/login', {
            body: bodyParams,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            method: 'POST'});
            console.log(response);
        if (!response.ok) {
            return null as any;
        }
            
        return response.json() as Promise<{id: string, email: string, type: 'admin' | 'client', token: string}>;
    },

    getUser: async (): Promise<User> => {
        let token = localStorage.getItem('token');
        let response = await fetch('http://localhost:8080/api/users/user', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        return response.json() as Promise<User>;
    },

    register: async (email: string, password: string, type: string) => {
        let bodyParams = new URLSearchParams();
        bodyParams.append('email', email);
        bodyParams.append('password', password);
        bodyParams.append('type', type.toUpperCase());

        let response = await fetch('http://localhost:8080/api/users/register', {
            body: bodyParams,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            method: 'POST'
        });

        if (!response.ok) {
            return null as any;
        }

        return response.json() as Promise<{id: string, email: string, type: 'admin' | 'client', token: string}>;
    }
}

