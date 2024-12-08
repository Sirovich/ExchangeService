import { atom } from "jotai";

export type User = {
    id: string;
    email: string;
    type: 'admin' | 'client';
}

export const userAtom = atom<User>({
    id: '',
    email: '',
    type: 'client'
});