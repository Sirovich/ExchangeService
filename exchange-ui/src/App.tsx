import { Login } from './components/user/login';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { User } from './components/user/user';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/login" Component={Login} />
          <Route path="/user" Component={User} />
        </Routes>
      </div>
    </Router>

  );
}

export default App;
