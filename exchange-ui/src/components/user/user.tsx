import { FC, useContext, useEffect, useState } from 'react';
import { userAtom } from '../../models/User';
import TransactionList from '../transaction/transactionList';
import { TransactionForm } from '../transaction/transactionForm';
import { useAtom } from 'jotai';
import { AccountsList } from '../account/accountsList';

export const User: FC = () => {
  const [user] = useAtom(userAtom);

  return (
    <div className='px-32'>
      <div className="flex flex-col items-center justify-center bg-gray-100 p-4 mb-3">
        <h1 className="text-2xl font-bold text-center">{user.email}</h1>
      </div>
      <AccountsList />
      <TransactionList />
    </div>
  );
};