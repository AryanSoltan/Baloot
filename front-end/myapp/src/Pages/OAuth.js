import * as React from "react";
import {toast} from "react-toastify";

import Footer from "./Footer"
import logo from "../assets/images/ballot.png";
import {Link, Routes, Route, useNavigatel, useSearchParams} from 'react-router-dom';

import {useEffect, useState} from 'react';
import "../Style/register-sign.css"
import "../Style/footer.css"

import axios from 'axios'

export default function OAuth() {

    const [searchParams, setSearchParams] = useSearchParams();

    useEffect(() => {
         async function fetchData() {
            const code = searchParams.get("code");
            const response = await axios.post('/oauth-github', {code: code});
            localStorage.setItem('token', response.data.content);
            const userInfo = await axios.post('/getUserInfo', {token: localStorage.getItem('token')});
//            console.log(userInfo);
            localStorage.setItem('userId', userInfo.data.data.name);
            localStorage.setItem('userEmail', userInfo.data.data.email);
            localStorage.setItem('userLoggedIn', true);
            window.location.href = "http://localhost:3000/commodities";

//        if(response.data.statusCode == 200) {
//            localStorage.setItem('userLoggedIn', true);
//            localStorage.setItem('userEmail', email);
//            localStorage.setItem('token', response.data.content);
//            console.log("token is: ");
//            console.log(localStorage.getItem('token'));
//            console.log("resp is ");
//
//            //navigate("/commodities");
        }
        fetchData();
//        }
    }, []);
}
