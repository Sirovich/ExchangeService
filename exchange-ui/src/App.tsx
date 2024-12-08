import { Login } from './components/user/login';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { User } from './components/user/user';
import './App.css';
import { UserContext } from './context/UserContext';
import { useEffect, useState } from 'react';
import { userAtom, User as UserModel } from './models/User';
import { useAtom } from 'jotai';
import { currentAccountAtom } from './models/Account';
import { userService } from './services/userService';
import { accountService } from './services/accountService';
import { Header } from './components/header';
import { TransactionForm } from './components/transaction/transactionForm';
import { AccountForm } from './components/account/accountForm';

function App() {
  const [, setUser] = useAtom(userAtom);
  const [, setAccounts] = useAtom(currentAccountAtom);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const user = await userService.getUser();
        setUser(user);

        const accounts = await accountService.getAccounts();
        setAccounts(accounts);
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        console.log('Data fetched');
      }
    };

    fetchData();
  }, [setUser, setAccounts]);

  return (
    <Router>
      <div className='h-screen'>
        <Header />
        <div className="App flex flex-col justify-center">
          <Routes>
            <Route path="/" element={<Navigate to="/login" />} />
            <Route path="/transaction" Component={TransactionForm} />
            <Route path="/create-account" Component={AccountForm} />
            <Route path="/login" Component={Login} />
            <Route path="/user" Component={User} />
          </Routes>
        </div>
      </div>
    </Router>

  );
}

export default App;
