import { FC, useEffect, useState } from "react";
import { userAtom } from "../models/User";
import { useAtom } from "jotai";

export const Header: FC = () => {

    const [user] = useAtom(userAtom);

    return (
        <div className="flex justify-between bg-gray-800 text-white p-4 w-auto align-middle">
            <div className="p-4">
                {
                    user.type.toLocaleLowerCase() === 'admin' ?
                        <a href='/rates' className="text-2xl">Rates</a>
                        : <a href='/transaction' className="text-2xl">Currency Exchange</a>
                }
            </div>
            <div className="mr-10 p-4">
                <a href='/user' className="text-white">Profile</a>
            </div>
        </div>
    )
}