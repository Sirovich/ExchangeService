import { FC } from "react";

export const Header: FC = () => {
    return (
        <div className="flex justify-between bg-gray-800 text-white p-4 w-auto align-middle">
            <div className="p-4">
                <a href='/transaction' className="text-2xl">Currency Exchange</a>
            </div>
            <div className="mr-10 p-4">
                <a href='/user' className="text-white">Profile</a>
            </div>
        </div>
    )
}