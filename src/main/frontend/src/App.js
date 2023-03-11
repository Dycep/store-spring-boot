import { Route, Routes } from 'react-router-dom';
import './App.css';
import { Items } from './pages/Items';
import { RegistrationForm } from './pages/RegistrationForm';

function App() {
  return (
    <div className='App'>
      <Routes>
        <Route path='/' element={<Items/>} />
        <Route path='/registration' element={<RegistrationForm/>} />
      </Routes>
    </div>
  );
}

export default App;
