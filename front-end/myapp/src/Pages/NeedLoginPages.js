import { Navigate, Outlet, useLocation } from "react-router-dom";

import { Route, Routes, useNavigate } from 'react-router-dom';
import {useEffect} from "react";


export default function NeedLoginPages() {

    const location = useLocation();

    var isLoggedIn = localStorage.getItem('userLoggedIn');

    const navigate = useNavigate();


    useEffect(() =>
    {
        var isLoggedIn = JSON.parse(localStorage.getItem('userLoggedIn'));

        if(isLoggedIn === null) {
            navigate("/");
        }
    },[]);


    return (<Outlet/>);


}
