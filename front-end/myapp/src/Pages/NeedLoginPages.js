import { Navigate, Outlet, useLocation } from "react-router-dom";

import { Route, Routes, useNavigate } from 'react-router-dom';
import {useEffect} from "react";

import * as React from "react";


import axios from 'axios'



export default function NeedLoginPages() {

    const location = useLocation();

    var isLoggedIn = localStorage.getItem('userLoggedIn');

    const navigate = useNavigate();


//     useEffect(() =>
//     {
// //        var isLoggedIn = JSON.parse(localStorage.getItem('userLoggedIn'));
//
//
//          async function fetchData() {
//              try {
//                 const response = await axios.post("/isLogin", null);
//                 console.log(response);
//
//              } catch (e) {
//                  console.log(e);
//              }
//           }
//         fetchData();
//
//         if(isLoggedIn === null) {
//             navigate("/");
//         }
//     },[]);
    if(!isLoggedIn){
        return <Navigate to="/login" state={{ from: location }} replace />;
    }


    return (<Outlet/>);


}
