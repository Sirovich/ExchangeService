export const userService = {
    login: async (email: string, password: string): Promise<{
        id: string;
        email: string;
        type: 'admin' | 'client';
        token: string;
    }> => {
        let response = await fetch('http://localhost:8080/api/users/login', {body: JSON.stringify({email, password}), method: 'POST', mode:'no-cors'});
        console.log(response);
        return response.json() as Promise<{id: string, email: string, type: 'admin' | 'client', token: string}>;
    }
}

