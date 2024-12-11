import { FC, useContext, useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { userService } from '../../services/userService';
import { UserContext } from '../../context/UserContext';
import { useAtom } from 'jotai';
import { User, userAtom } from '../../models/User';

export const Registration: FC = () => {
  const [, setUser] = useAtom(userAtom);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [type, setType] = useState('client');
  const [error, setError] = useState(false);

  const submitRegistration = async (e: React.FormEvent) => {
    e.preventDefault();
    let userResponse = await userService.register(email, password, type);
    if (userResponse) {
      localStorage.setItem('token', userResponse.token);
      let val: User = {
        id: userResponse.id,
        email: userResponse.email,
        type: userResponse.type,
      };
      setUser(val)
      setError(false);
      window.location.href = '/transaction';
    }
    else {
      setError(true);
    }

  };

  return (
    <div className="flex flex-row items-center justify-center max-h-screen py-64">
      <div className="w-full max-w-md p-8 space-y-6 bg-white rounded-lg shadow border-2 border-slate-300">
        <h1 className="text-2xl font-bold text-center mb-6">Register</h1>
        <form onSubmit={submitRegistration}>
          <div className='mb-2'>
            <label className="block text-sm font-medium text-gray-700">Email:</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className={"w-full px-3 py-2 mt-1 border rounded-md focus:outline-none focus:ring focus:ring-indigo-200"
                + (error ? ' border-red-500' : '')}
            />
          </div>
          <div className='mb-4'>
            <label className="block text-sm font-medium text-gray-700">Password:</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className={"w-full px-3 py-2 mt-1 border rounded-md focus:outline-none focus:ring focus:ring-indigo-200"
                + (error ? ' border-red-500' : '')}
            />
          </div>
          <div className='mb-4'>
            <label className="block text-sm font-medium text-gray-700">Type:</label>
            <select
              value={type}
              onChange={(e) => setType(e.target.value)}
              required
              className="w-full px-3 py-2 mt-1 border rounded-md focus:outline-none focus:ring focus:ring-indigo-200"
            >
              <option value="client">Client</option>
              <option value="admin">Admin</option>
            </select>
          </div>
          <div className='mb-4'>
            {error && <p className="text-red-500">User already exists</p>}
          </div>
          <button
            type="submit"
            className="w-full px-4 py-2 font-medium text-white bg-indigo-600 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring focus:ring-indigo-200"
          >
            Register
          </button>
        </form>
      </div>
    </div>
  );
}