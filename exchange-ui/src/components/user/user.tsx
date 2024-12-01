import { FC, useContext, useEffect, useState } from 'react';
import { UserContext } from '../../context/UserContext';
import { userService } from '../../services/userService';

export const User: FC = () => {
  const user = useContext(UserContext);

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="w-full max-w-md p-8 space-y-6 bg-white rounded-lg shadow-md">
        <h1 className="text-2xl font-bold text-center">User Profile</h1>
        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">Email:</label>
            <p className="mt-1">{user.email}</p>
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">Account type:</label>
            <p className="mt-1">{user.type}</p>
          </div>
        </div>
      </div>
    </div>
  );
};