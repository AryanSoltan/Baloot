import './App.css';

import Login from "./Pages/Login";
import Home from "./Pages/Home";

import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from "react-toastify";



function App() {
  return (
      <Router>
        <Switch>

          <Route path = "/login">
            <Login/>
          </Route>
          <Route path = "/homepage">
              <Home/>
          </Route>

        </Switch>
        <ToastContainer/>
      </Router>
  );
}

export default App;