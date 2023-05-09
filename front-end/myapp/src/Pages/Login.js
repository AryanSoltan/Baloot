import * as React from "react";
import {toast} from "react-toastify";

import Footer from "./Footer"
import logo from "../assets/images/ballot.png";
import {Link, Routes, Route, useNavigate} from 'react-router-dom';

import {useEffect, useState} from 'react';
import "../Style/register-sign.css"
import "../Style/footer.css"

import axios from 'axios'

export default function Login() {

    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    useEffect(() => {

      localStorage.clear();
      console.log("in local storage clear");

    }, []);
    const handleSubmit = async(e)=> {
        e.preventDefault();
        console.log("username is ");
        console.log(username);
        console.log(password);
        if(!username){
            toast.error('should fill the username part');
            return
        }
        else if(!password)
        {
            toast.error('should fill the password part');
        }
        else
        {
            try{

            const response = await axios.post('/login', {
                username: username,
                password: password
            })

            console.log(response);

                if(response.data.statusCode == 200) {
                    localStorage.setItem('userLoggedIn', true);
                    localStorage.setItem('userId', username);
                    console.log("resp is ");

                     window.location.href = "http://localhost:3000/commodities";
                    //navigate("/commodities");
                }
            }
            catch(e){
                toast.error("password or username is wrong");
                console.log(e.message)
            }
        }
    }


        return (
            <html lang="en">

            <head>
                <meta charSet="UTF-8"/>
                <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <title>Document</title>
            </head>

            <body>
            <div className="logo">

                <div className="font-title"><img src={logo}></img> Baloot</div>
                <form onSubmit={handleSubmit}>

                    <h1>Sign in Form </h1>

                    <p>Username</p>

                    <input type="text" placeholder="Enter Username" name="username" id="username" onChange={e => { setUsername(e.target.value) }}/>
                    <br/>
                    <br/>
                    <p>Password</p>

                    <input type="password" placeholder="Enter Password" name="password" id = "password"  onChange={e => { setPassword(e.target.value) }}/>

                    <br/>

                    <button className="register-button">
                        Sign in
                    </button>
                </form>

                <div>
                    <p>Dont' have an account? <a href="/signup">Create New Account</a>.</p>
                </div>
            </div>


            <Footer/>
            </body>
            </html>
        );

}
