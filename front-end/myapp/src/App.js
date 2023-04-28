import React, {useState} from "react";
import './App.css';

import Login from "./Pages/Login";

import Home from "./Pages/Home";


import { Route, Link, BrowserRouter, Routes } from "react-router-dom";

import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from "react-toastify";

import Commodities from "./Pages/Commodities";
import Commodity from "./Pages/Commodity";



function App() {
  return (
      <BrowserRouter>
          <ToastContainer />
          <Routes>


              <Route exact path="/" element={<Login />}/>
              <Route path="/commodities" element={<Commodities />}/>
              <Route path="/commodities/:id" element={<Commodity />}/>


          </Routes>
        {/*<Switch>*/}

        {/*  <Route>*/}
        {/*    <Navbar/>*/}
        {/*  </Route>*/}

        {/*</Switch>*/}
        {/*<ToastContainer/>*/}
      </BrowserRouter>
  );
}

export default App;