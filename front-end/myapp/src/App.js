import logo from './logo.svg';
import './App.css';

import Login from "./Pages/Login";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";



function App() {
  return (
      <Router>
        <Switch>

          <Route path = "/login">
            <Login/>
          </Route>

        </Switch>
        <ToastContainer/>
      </Router>
  );
}

export default App;